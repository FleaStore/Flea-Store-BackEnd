package swengineering8.fleastore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swengineering8.fleastore.domain.Market;
import swengineering8.fleastore.dto.MarketDto;
import swengineering8.fleastore.dto.Response;
import swengineering8.fleastore.service.MarketService;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {

    private final MarketService marketService;
    private final Response response;

    /**
     * 마켓 index 로부터 5개 가져오기
     */
    @GetMapping("/list")
    public ResponseEntity<?> getMarkets(@RequestParam int page) throws IOException {

        //Page<Market> marketList = marketService.getMarkets(page,5);
        return marketService.getMarkets(page,5);

    }

    /**
     * 마켓 생성정보 가져오기
     */
    @GetMapping("")
    public ResponseEntity<?> getDetailInfo(@RequestParam("marketId") Long marketId) throws IOException {

        return marketService.getDetailInfo(marketId);
    }

    /**
     * 요청 보낸 유저의 마켓을 추가
     */
    @PostMapping("")
    public ResponseEntity<?> addMarket(@ModelAttribute MarketDto marketDto, @RequestParam(value = "images", required = false) List<MultipartFile> images,
                                       Principal principal) throws IOException {

        long memberId = Long.parseLong(principal.getName());

        return marketService.addMarket(marketDto, images, memberId);
            //return response.success("마켓 추가에 성공했습니다.");
    }

    /**
     * 마켓 정보 변경
     */
    @PutMapping("")
    public ResponseEntity<?> updateMarket(List<MultipartFile> images, @ModelAttribute MarketDto marketDto) throws IOException {

        return marketService.updateMarket(marketDto, images);
    }

    /**
     * 마켓 삭제
    */
    @DeleteMapping("")
    public ResponseEntity<?> deleteMarket(@RequestParam Long marketId) {

        return marketService.deleteMarket(marketId);
    }
}
