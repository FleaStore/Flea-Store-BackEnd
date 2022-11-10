package swengineering8.fleastore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swengineering8.fleastore.dto.MarketDto;
import swengineering8.fleastore.dto.Response;
import swengineering8.fleastore.service.MarketService;
import swengineering8.fleastore.service.MemberService;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {

    private final MarketService marketService;
    private final Response response;

    /**
     * 마켓 index로부터 5개 가져오기
     */
    @GetMapping("/list/{index}")
    public ResponseEntity<?> getMarkets(@PathVariable int index) {

        return marketService.getMarkets(index);
    }

    /**
     * 마켓 생성정보 가져오기
     */
    @GetMapping("/market/view/{marketId}")
    public ResponseEntity<?> getDetailInfo(@PathVariable Long marketId) {

        return marketService.getDetailInfo(marketId);
    }

    /**
     * 요청 보낸 유저의 마켓을 추가
     */
    @PostMapping("/market/upload")
    public ResponseEntity<?> addMarket(@RequestBody MarketDto marketDto, Principal principal) {

        long memberId = Long.parseLong(principal.getName());

        return marketService.addMarket(marketDto, memberId);
    }

    /**
     * 마켓 정보 변경
     */
    @PutMapping("/market/modify/{marketId}")
    public ResponseEntity<?> updateMarket(@PathVariable Long marketId, @RequestBody MarketDto marketDto) {

        return marketService.updateMarket(marketDto, marketId);
    }

    /**
     * 마켓 삭제
    */
    @DeleteMapping("/market/delete/{marketId}")
    public ResponseEntity<?> deleteMarket(@PathVariable Long marketId) {

        return marketService.deleteMarket(marketId);
    }
}
