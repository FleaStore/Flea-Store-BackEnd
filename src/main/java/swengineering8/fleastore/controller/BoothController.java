package swengineering8.fleastore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swengineering8.fleastore.dto.BoothDto;
import swengineering8.fleastore.service.BoothService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class BoothController {

    private final BoothService boothService;

    /**
     * 해당 마켓이 가지는 모든 부스 정보 반환
     */
    @GetMapping("/market/{marketId}/booth/list")
    public ResponseEntity<?> getBoothsOfMarket(@PathVariable Long marketId) {

        return boothService.getBoothsOfMarket(marketId);
    }

    /**
     * 특정 부스 정보 가져오기
     */
    @GetMapping("/booth/{boothId}")
    public ResponseEntity<?> getBooth(@PathVariable Long boothId) {

        return boothService.getBooth(boothId);
    }

    /**
     * 마켓 내 부스 생성
     */
    @PostMapping("/market/{marketId}/booth/create")
    public ResponseEntity<?> addBooth(@RequestBody BoothDto boothDto, @PathVariable Long marketId) {

        return boothService.addBooth(boothDto, marketId);
    }

    /**
     * 해당 부스 정보 업데이트
     */
    @PutMapping("/booth/update/{boothId}")
    public ResponseEntity<?> updateBooth(@RequestBody BoothDto boothDto, @PathVariable Long boothId) {

        return boothService.updateBooth(boothDto, boothId);
    }

    /**
     * 부스 삭제
     */
    @DeleteMapping("booth/delete/{boothId}")
    public ResponseEntity<?> deleteMarket(@PathVariable Long boothId) {

        return boothService.deleteBooth(boothId);
    }
}
