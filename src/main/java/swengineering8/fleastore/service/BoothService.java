package swengineering8.fleastore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import swengineering8.fleastore.domain.Booth;
import swengineering8.fleastore.domain.BoothImgFile;
import swengineering8.fleastore.domain.Market;
import swengineering8.fleastore.domain.MarketImgFile;
import swengineering8.fleastore.domain.Repository.BoothImgFileRepository;
import swengineering8.fleastore.domain.Repository.BoothRepository;
import swengineering8.fleastore.domain.Repository.MarketRepository;
import swengineering8.fleastore.dto.BoothDto;
import swengineering8.fleastore.dto.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoothService {

    private final BoothRepository boothRepository;
    private final BoothImgFileRepository boothImgFileRepository;
    private final MarketRepository marketRepository;
    private final Response response;

    public ResponseEntity<?> getBoothsOfMarket(Long marketId) throws IOException {

        Market market = marketRepository.findById(marketId).orElse(null);
        List<Booth> booths = boothRepository.findAllByMarket(market);
        List<BoothDto> results = new ArrayList<>();

        for (Booth booth : booths) {
            BoothDto boothDto = new BoothDto(booth.getId(), booth.getName(), booth.getInfo(), booth.getCategory(), getBoothImages(booth));

            results.add(boothDto);
        }


        return response.success(results, "해당 가게의 부스 정보", HttpStatus.OK);
    }

    public ResponseEntity<?> getBooth(Long boothId) throws IOException {
        Booth booth = boothRepository.findById(boothId).orElse(null);

        BoothDto boothDto = new BoothDto(booth.getId(), booth.getName(), booth.getInfo(), booth.getCategory(), getBoothImages(booth));

        return response.success(boothDto, "부스 정보", HttpStatus.OK);
    }

    public ResponseEntity<?> addBooth(BoothDto boothDto, List<MultipartFile> images, Long marketId) throws IOException {

        Booth newBooth = new Booth();
        newBooth.setMarket(marketRepository.findById(marketId).orElse(null));
        boothRepository.save(newBooth);
        boothDto.setBoothId(newBooth.getId());

        return updateBooth(boothDto, images);
    }

    @Transactional
    public ResponseEntity<?> updateBooth(BoothDto boothDto,
                                      List<MultipartFile> images) throws IOException {
        //1. 우선 해당 부스의 기존 이미지 파일을 모두 삭제
        Booth booth = boothRepository.findById(boothDto.getBoothId()).orElse(null);
        //1-1. 이미지 파일을 삭제. 파일 경로 정해야 함
        for (BoothImgFile imgFile : booth.getImgFiles()) {
            File file = new File(imgFile.getImgUrl() + imgFile.getFileName());
            if (file.exists()) {
                file.delete();
                boothImgFileRepository.deleteById(imgFile.getId());
            }
        }
        booth.getImgFiles().clear();

        //2. 이미지를 제외하고 새 부스 정보로 변경
        booth.updateBooth(boothDto.getName(), boothDto.getInfo(), boothDto.getCategory());

        //3. 이미지 새로 저장
        saveBoothImage(booth, images);

        boothRepository.save(booth);


        return response.success("부스정보가 성공적으로 반영되었습니다.");
    }

    public ResponseEntity<?> deleteBooth(Long boothId) {

        Booth booth = boothRepository.findById(boothId).orElse(null);

        for (BoothImgFile imgFile : booth.getImgFiles()) {
            File file = new File(imgFile.getImgUrl() + imgFile.getFileName());
            if (file.exists()) {
                file.delete();
            }
        }
        booth.getImgFiles().clear();

        boothRepository.delete(booth);


        return response.success("부스가 성공적으로 삭제되었습니다.");
    }


    /**
     * 부스의 이미지 정보를 가져옴
     * 다른 로컬에서 동작시킬때마다 fileUrl 확인 필요
     */
    @Transactional
    public void saveBoothImage(Booth booth, List<MultipartFile> images) throws IOException{
        /*1. 아무 이미지가 없으면 바로 반환*/
        if(images == null)
            return;

        /*2. 파일이 저장될 경로를 설정해준다.*/
        String imgUrl = System.getProperty("user.dir") + "/src/main/resources/static/images/";

        /*3. images에 있는 multipartFile 객체 image를 하나씩 확인하면서 저장 폴더에 저장하고 DB에 기록한다.*/
        for(MultipartFile image : images) {
            /*3-1. 클라이언트에서 보낼 당시의 파일 이름*/
            String originalName = image.getOriginalFilename();

            /*3-2. 실제로 저장될 파일이름을 uuid로 설정하여 중복을 방지*/
            String fileName = UUID.randomUUID().toString() +
                    originalName.substring(originalName.lastIndexOf("."));
            File destinationFile = new File(imgUrl + fileName);

            destinationFile.getParentFile().mkdirs();
            image.transferTo(destinationFile); // 파일을 설정한 경로에 저장

            /*3-3. storeImgFile 객체에 저장된 파일에 대한 정보 담음*/
            BoothImgFile boothImgFile = BoothImgFile.builder()
                    .booth(booth)
                    .originalName(originalName)
                    .fileName(fileName)
                    .imgUrl(imgUrl).build();

            booth.addImgFile(boothImgFile); // store의 ImgFile에 추가

            boothImgFileRepository.save(boothImgFile);
        }

        boothRepository.save(booth);
    }

    public List<String> getBoothImages(Booth booth) throws IOException{
        List<BoothImgFile> boothImages = booth.getImgFiles();
        List<String> base64Images = new ArrayList<>();
        String imgUrl = System.getProperty("user.dir") + "/src/main/resources/static/images/";
        String fileName = "default.jpeg";

        if(boothImages.isEmpty()){


            FileInputStream imageStream = new FileInputStream(imgUrl + fileName);
            byte[] bytes = Base64.encodeBase64(imageStream.readAllBytes());
            String result = new String(bytes, "UTF-8");
            imageStream.close();

            base64Images.add(result);
        }
        else {
            for (BoothImgFile imgFile : boothImages) {
                FileInputStream imageStream = new FileInputStream(imgFile.getImgUrl() + imgFile.getFileName());
                byte[] bytes = Base64.encodeBase64(imageStream.readAllBytes());
                String result = new String(bytes, "UTF-8");
                imageStream.close();

                base64Images.add(result);
            }
        }

        return base64Images;
    }

}
