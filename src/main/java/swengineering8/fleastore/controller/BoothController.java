package swengineering8.fleastore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swengineering8.fleastore.dto.BoothDto;
import swengineering8.fleastore.service.BoothService;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/booth")
public class BoothController {

    private final BoothService boothService;

    /**
     * 해당 마켓이 가지는 모든 부스 정보 반환
     */
    @GetMapping("/list")
    public ResponseEntity<?> getBoothsOfMarket(@RequestParam Long marketId) throws IOException {

        return boothService.getBoothsOfMarket(marketId);
    }

    /**
     * 특정 부스 정보 가져오기
     */
    @GetMapping("")
    public ResponseEntity<?> getBooth(@RequestParam Long boothId) throws IOException {

        return boothService.getBooth(boothId);
    }

    /**
     * 마켓 내 부스 생성
     */
    @PostMapping("")
    public ResponseEntity<?> addBooth(@ModelAttribute BoothDto boothDto, @RequestParam(value = "images", required = false) List<MultipartFile> images,
                                      @RequestParam Long marketId) throws IOException {

        return boothService.addBooth(boothDto, images, marketId);
    }

    /**
     * 해당 부스 정보 업데이트
     */
    @PutMapping("")
    public ResponseEntity<?> updateBooth(@ModelAttribute BoothDto boothDto, @RequestParam(value = "images", required = false) List<MultipartFile> images) throws IOException {

        return boothService.updateBooth(boothDto, images);
    }

    /**
     * 부스 삭제
     */
    @DeleteMapping("")
    public ResponseEntity<?> deleteMarket(@RequestParam Long boothId) {

        return boothService.deleteBooth(boothId);
    }
}
