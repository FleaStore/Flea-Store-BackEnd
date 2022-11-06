package swengineering8.fleastore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swengineering8.fleastore.service.MemberService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {

    private final MemberService memberService;
}
