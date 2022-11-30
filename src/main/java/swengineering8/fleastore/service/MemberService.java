package swengineering8.fleastore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swengineering8.fleastore.domain.Member;
import swengineering8.fleastore.domain.Repository.FavoriteRepository;
import swengineering8.fleastore.domain.Repository.MemberRepository;
import swengineering8.fleastore.dto.MemberDto;
import swengineering8.fleastore.dto.Response;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;
    private final Response response;

    public ResponseEntity<?> memberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        MemberDto memberDto = new MemberDto(member.getEmail(), member.getName(), member.getNickname(), member.getPhoneNumber());

        return response.success(memberDto, "유저 정보", HttpStatus.OK);
    }

    public ResponseEntity<?> updateMember(MemberDto memberDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        member.changeInfo(memberDto.getEmail(), memberDto.getNickname(), member.getName(), member.getPhoneNumber());
        memberRepository.save(member);

        return response.success("정보가 성공적으로 변경되었습니다.");
    }

    public ResponseEntity<?> toggleLike(Long memberId, Long marketId) {
        return response.success();
    }
}
