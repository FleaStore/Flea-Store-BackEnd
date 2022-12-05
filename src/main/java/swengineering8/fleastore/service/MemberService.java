package swengineering8.fleastore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swengineering8.fleastore.domain.*;
import swengineering8.fleastore.domain.Repository.FavoriteRepository;
import swengineering8.fleastore.domain.Repository.MarketRepository;
import swengineering8.fleastore.domain.Repository.MemberRepository;
import swengineering8.fleastore.domain.Repository.PermissionRequestRepository;
import swengineering8.fleastore.dto.MemberDto;
import swengineering8.fleastore.dto.PermissionRequestDto;
import swengineering8.fleastore.dto.Response;
import swengineering8.fleastore.dto.SimpleFavoriteDto;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;
    private final MarketRepository marketRepository;
    private final PermissionRequestRepository permissionRequestRepository;
    private final MarketService marketService;
    private final PasswordEncoder passwordEncoder;
    private final Response response;

    public ResponseEntity<?> memberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        MemberDto memberDto = new MemberDto(member.getEmail(), member.getName(), member.getNickname(), member.getPhoneNumber());

        return response.success(memberDto, "유저 정보", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateMember(MemberDto memberDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        log.info("id: {} / email: {} / nickname: {} / name: {} / phoneNumber: {}", member.getId(), member.getEmail(), member.getNickname(), member.getName(), member.getPhoneNumber());
        member.changeInfo(memberDto.getNickname(), memberDto.getName(), memberDto.getPhoneNumber());
        log.info("id: {} / email: {} / nickname: {} / name: {} / phoneNumber: {}", member.getId(), member.getEmail(), member.getNickname(), member.getName(), member.getPhoneNumber());

        memberRepository.save(member);

        return response.success("정보가 성공적으로 변경되었습니다.");
    }

    @Transactional
    public ResponseEntity<?> changePassword(Long memberId, String newPassword){
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member == null) {
            return response.fail("이메일을 다시 입력해주세요.", HttpStatus.BAD_REQUEST);
        }

        member.changePassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);

        return response.success("패스워드가 성공적으로 변경되었습니다.");
    }

    public ResponseEntity<?> toggleLike(Long marketId, Long memberId) {

        Member member = memberRepository.findById(memberId).orElse(null);
        Market market = marketRepository.findById(marketId).orElse(null);

        Favorite favorite;

        if(member.isFavoriteMarket(market)) {
            favorite = favoriteRepository.findFavoriteByMemberAndMarket(member, market).orElseGet(null);

            member.getFavorites().remove(favorite);
            favoriteRepository.delete(favorite);

            return response.success("좋아요 목록에서 성공적으로 제거했습니다.");
        }

        favorite = new Favorite(member, market);

        member.getFavorites().add(favorite);
        market.getFavorites().add(favorite);

        favoriteRepository.save(favorite);

        return response.success("좋아요 목록에 성공적으로 저장했습니다.");
    }

    public ResponseEntity<?> getFavoriteMarkets(Long memberId) throws IOException {
        Member member = memberRepository.findById(memberId).orElse(null);
        List<Favorite> favorites = member.getFavorites();
        List<SimpleFavoriteDto> results = new ArrayList<>();

        for (Favorite favorite : favorites) {
            Market market = favorite.getMarket();

            results.add(new SimpleFavoriteDto(market.getId(), market.getMember().getNickname(), market.getName(), marketService.getMarketImages(market).get(0)));
        }

        return response.success(results, "좋아요 가게 목록", HttpStatus.OK);
    }

    public ResponseEntity<?> changeAuthority(Long memberId, Authority authority){
        Member member = memberRepository.findById(memberId).orElse(null);
        member.setAuthority(authority);
        return response.success("권한을 성공적으로 변경하였습니다.");
    }

    @Transactional
    public ResponseEntity<?> requestPermission(PermissionRequestDto request, Long memberId) {

        Member member = memberRepository.findById(memberId).orElse(null);

        PermissionRequest permissionRequest = request.toEntity();
        permissionRequest.setMember(member);

        permissionRequestRepository.save(permissionRequest);

        return response.success("권한 요청이 전송되었습니다.");
    }
}
