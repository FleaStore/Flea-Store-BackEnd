package swengineering8.fleastore.controller;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swengineering8.fleastore.dto.MarketUpdateDto;
import swengineering8.fleastore.service.MarketService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
@Tag(name = "Market", description = "마켓 조회, 추가, 삭제, 수정 API")
public class MarketController {

    private final MarketService marketService;

    /**
     * 마켓 index 로부터 5개 가져오기
     */
    @ApiOperation(value = "Market 리스트 5개씩 받아오기", notes = "메인페이지에 보여줄 Market 리스트를 5개씩 뿌려주는 API", tags = "Market")
    @ApiResponse(code = 200, message = "마켓 리스트 불러오기 성공")
    @ApiImplicitParam(name = "page", value = "페이지 번호", required = true, dataTypeClass = Integer.class, example = "1")
    @GetMapping("/list")
    public ResponseEntity<?> getMarkets(@RequestParam int page) throws IOException {

        //Page<Market> marketList = marketService.getMarkets(page,5);
        return marketService.getMarkets(page, 5);

    }

    /**
     * 마켓 생성정보 가져오기
     */
    @ApiOperation(value = "Market 상세정보", notes = "마켓상세페이지에 보여줄 마켓 정보 API", tags = "Market")
    @ApiResponse(code = 200, message = "마켓 상세정보 불러오기 성공")
    @ApiImplicitParam(name = "marketId", value = "마켓 고유 Id", required = true, dataTypeClass = Integer.class, example = "1")
    @GetMapping("")
    public ResponseEntity<?> getDetailInfo(@RequestParam("marketId") Long marketId, Principal principal) throws IOException {

        return marketService.getDetailInfo(marketId);
    }

    /**
     * 요청 보낸 유저의 마켓을 추가
     */
    @ApiOperation(value = "Market 추가", notes = "유저의 Market 추가 API", tags = "Market")
    @ApiResponse(code = 200, message = "마켓 추가 성공")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "마켓 주소", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "info", value = "마켓 설명", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "name", value = "마켓 이름", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "startDate", value = "시작날짜", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "endDate", value = "마감날짜", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "relatedUrl", value = "SNS 주소", required = true, dataTypeClass = String.class)
    })
    @PostMapping("")
    public ResponseEntity<?> addMarket(@ModelAttribute MarketUpdateDto marketDto, @RequestParam(value = "images", required = false) List<MultipartFile> images,
                                       Principal principal) throws IOException {

        long memberId = Long.parseLong(principal.getName());

        return marketService.addMarket(marketDto, images, memberId);
    }

    /**
     * 마켓 정보 변경
     */
    @ApiOperation(value = "Market 수정", notes = "유저의 Market 수정 API", tags = "Market")
    @ApiResponse(code = 200, message = "마켓 리스트 삭제 성공")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "marketId", value = "마켓 고유 Id", required = true, dataTypeClass = Integer.class, example = "1"),
            @ApiImplicitParam(name = "address", value = "마켓 주소", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "info", value = "마켓 설명", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "name", value = "마켓 이름", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "startDate", value = "시작날짜", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "endDate", value = "마감날짜", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "relatedUrl", value = "SNS 주소", required = true, dataTypeClass = String.class)
    })
    @PutMapping("")
    public ResponseEntity<?> updateMarket(@RequestParam("marketId") Long marketId, List<MultipartFile> images, @ModelAttribute MarketUpdateDto marketDto, Principal principal) throws IOException {
        long memberId = Long.parseLong(principal.getName());
        return marketService.updateMarket(marketDto, images, memberId, marketId);
    }

    /**
     * 마켓 삭제
     */

    @ApiOperation(value = "Market 삭제", notes = "유저의 Market 삭제 API", tags = "Market")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "마켓 리스트 삭제 성공"),
            @ApiResponse(code = 400, message = "유저의 마켓이 아닌경우 삭제 실패"),
    })
    @ApiImplicitParam(name = "marketId", value = "마켓 고유 Id", required = true, dataTypeClass = Integer.class, example = "1")
    @DeleteMapping("")
    public ResponseEntity<?> deleteMarket(@RequestParam Long marketId, Principal principal, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());
        long memberId = Long.parseLong(principal.getName());
        return marketService.deleteMarket(marketId, memberId);
    }

    @GetMapping("/monthly")
    public ResponseEntity<?> getMarketByMonth(@RequestParam int year, @RequestParam int month) throws IOException {

        return marketService.getMarketByMonth(year, month);
    }
}
