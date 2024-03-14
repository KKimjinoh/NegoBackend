package KKimjinoh.negoland.nego_member.controller;

import KKimjinoh.negoland.nego_member.dto.Member_response_dto;
import KKimjinoh.negoland.nego_member.service.Member_service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Slf4j
//로그인 상태에서만 사용 가능하도록 하는 어노테이션
public class member_controller {
    private final Member_service memberService;

    @GetMapping("/members/mypage")
    public ResponseEntity<Member_response_dto> getMyInfo(){
        log.info("마이페이지");
        return ResponseEntity.ok(memberService.getMyInfo());
    }
}
