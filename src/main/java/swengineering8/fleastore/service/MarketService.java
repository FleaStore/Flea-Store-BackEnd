package swengineering8.fleastore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import swengineering8.fleastore.domain.Booth;
import swengineering8.fleastore.domain.BoothImgFile;
import swengineering8.fleastore.domain.Market;
import swengineering8.fleastore.domain.MarketImgFile;
import swengineering8.fleastore.domain.Repository.MarketImgFileRepository;
import swengineering8.fleastore.domain.Repository.MarketRepository;
import swengineering8.fleastore.dto.MarketDto;
import swengineering8.fleastore.dto.Response;
import swengineering8.fleastore.domain.Repository.MemberRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class MarketService {

    private final Response response;
    private final MarketRepository marketRepository;

    private final MarketImgFileRepository marketImgFileRepository;

    private final MemberRepository memberRepository;

    /**
     * 마켓 index로부터 5개 가져오기
     */
    public ResponseEntity<?> getMarkets(int page, int size) throws IOException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Market> marketList = marketRepository.findAll(pageable);

        List<Market> resultMarketList = marketList.getContent();
        ArrayList<MarketDto> responseList = new ArrayList<>();
        for( Market m : resultMarketList){
            MarketDto marketdto = new MarketDto(m.getId(), m.getName(), m.getAddress(), m.getStart_date(), m.getEnd_date(), m.getInfo(), m.getRelated_url(), getMarketImages(m));
            responseList.add(marketdto);
        }

        return response.success(responseList, "마켓 리스트", HttpStatus.OK);


        //response.success(responseList, "유저리스트 반환", HttpStatus.OK);


        //return marketList;
//        Market market = marketRepository.findAll(Pageable.ofSize(index)); // findAll로 인덱스만큼 페이지네이션 처리하기
//        MarketDto marketdto = new MarketDto(market.getId(), market.getName(), market.getAddress(), market.getStart_date(), market.getEnd_date(), market.getInfo(), market.getRelated_url(), getMarketImages(market));
//        return response.success(marketdto, "마켓 정보", HttpStatus.OK);
    }

    /**
     * 마켓 생성정보 가져오기
     */
    public ResponseEntity<?> getDetailInfo(Long marketId) throws IOException {
        Market market = marketRepository.findById(marketId).orElse(null);

        MarketDto marketdto = new MarketDto(market.getId(), market.getName(), market.getAddress(), market.getStart_date(), market.getEnd_date(), market.getInfo(), market.getRelated_url(), getMarketImages(market));


        return response.success(marketdto, "마켓 정보", HttpStatus.OK);
    }

    /**
     * 요청 보낸 유저의 마켓을 추가
     */
    public ResponseEntity<?> addMarket(MarketDto marketDto, List<MultipartFile> images, Long memberId) throws IOException {

        Market newMarket = new Market();
        newMarket.setUser(memberRepository.findById(memberId).orElse(null));
        marketRepository.save(newMarket);
        marketDto.setMarketId(newMarket.getId());

        return updateMarket(marketDto, images);
    }

    /**
     * 마켓 정보 변경
     */
    public ResponseEntity<?> updateMarket(MarketDto marketDto,
                                          List<MultipartFile> images) throws IOException {

        Market market = marketRepository.findById(marketDto.getMarketId()).orElse(null);

        for (MarketImgFile imgFile : market.getImgFiles()) {
            File file = new File(imgFile.getImgUrl() + imgFile.getFileName());
            if (file.exists()) {
                file.delete();
            }
        }
        market.getImgFiles().clear();

        market.updateMarket(marketDto.getName(), marketDto.getInfo(), marketDto.getAddress(), marketDto.getStartDate(), marketDto.getEndDate(), marketDto.getRelatedUrl());
        saveMarketImage(market, images);

        marketRepository.save(market);

        return response.success("마켓정보가 성공적으로 반영되었습니다.");
    }

    /**
     * 마켓 삭제
     */
    public ResponseEntity<?> deleteMarket(Long marketId) {

        Market market = marketRepository.findById(marketId).orElse(null);

        for (MarketImgFile imgFile : market.getImgFiles()) {
            File file = new File(imgFile.getImgUrl() + imgFile.getFileName());
            if (file.exists()) {
                file.delete();
            }
        }
        market.getImgFiles().clear();

        marketRepository.delete(market);

        return response.success("마켓이 성공적으로 삭제되었습니다.");
    }

    @Transactional
    public void saveMarketImage(Market market, List<MultipartFile> images) throws IOException {
        if (images == null) return;

        String imgUrl = System.getProperty("user.dir") + "/src/main/resources/static/images/";

        /*3. images에 있는 multipartFile 객체 image를 하나씩 확인하면서 저장 폴더에 저장하고 DB에 기록한다.*/
        for (MultipartFile image : images) {
            /*3-1. 클라이언트에서 보낼 당시의 파일 이름*/
            String originalName = image.getOriginalFilename();

            /*3-2. 실제로 저장될 파일이름을 uuid로 설정하여 중복을 방지*/
            String fileName = UUID.randomUUID().toString() +
                    originalName.substring(originalName.lastIndexOf("."));
            File destinationFile = new File(imgUrl + fileName);

            destinationFile.getParentFile().mkdirs();
            image.transferTo(destinationFile); // 파일을 설정한 경로에 저장

            /*3-3. storeImgFile 객체에 저장된 파일에 대한 정보 담음*/
            MarketImgFile marketImgFile = MarketImgFile.builder()
                    .market(market)
                    .originalName(originalName)
                    .fileName(fileName)
                    .imgUrl(imgUrl).build();

            market.addImgFile(marketImgFile); // store의 ImgFile에 추가

            marketImgFileRepository.save(marketImgFile);


        }

        marketRepository.save(market);

    }
    public List<String> getMarketImages(Market market) throws IOException{
        List<MarketImgFile> marketImages = market.getImgFiles();
        List<String> base64Images = new ArrayList<>();
        String imgUrl = System.getProperty("user.dir") + "/src/main/resources/static/images/";
        String fileName = "default.jpeg";

        if(marketImages.isEmpty()){
            FileInputStream imageStream = new FileInputStream(imgUrl + fileName);
            byte[] bytes = Base64.encodeBase64(imageStream.readAllBytes());
            String result = new String(bytes, "UTF-8");
            imageStream.close();

            base64Images.add(result);
        }
        else {
            for (MarketImgFile imgFile : marketImages) {
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
