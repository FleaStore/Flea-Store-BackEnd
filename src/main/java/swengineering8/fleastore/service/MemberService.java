package swengineering8.fleastore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swengineering8.fleastore.domain.Repository.MemberRepository;
import swengineering8.fleastore.dto.MemberDto;
import swengineering8.fleastore.dto.Response;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final Response response;

    public ResponseEntity<?> memberInfo(Long memberId) {
        return response.success();
    }

    public ResponseEntity<?> updateMember(MemberDto memberDto, Long memberId) {

        return response.success();
    }
}
