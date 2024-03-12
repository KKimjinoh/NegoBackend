package KKimjinoh.negoland.nego_member.controller;

import KKimjinoh.negoland.nego_member.dto.Member_request_dto;
import KKimjinoh.negoland.nego_member.dto.Member_response_dto;
import KKimjinoh.negoland.nego_member.dto.Token_info;
import KKimjinoh.negoland.nego_member.dto.Token_request_dto;
import KKimjinoh.negoland.nego_member.reposittory.Authority_repository;
import KKimjinoh.negoland.nego_member.service.Auth_service;
import KKimjinoh.negoland.nego_member.service.Member_service;
import antlr.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class member_controller {
    private final Auth_service authService;
    private final Authority_repository authorityRepository;

    @PostMapping("/sign-up")
    public ResponseEntity<Member_response_dto> sign_up (@RequestBody Member_request_dto memberRequestDto){
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }
    @PostMapping("/login")
    public ResponseEntity<Token_info> login(@RequestBody Member_request_dto memberRequestDto){
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<Token_info> reissue(@RequestBody Token_request_dto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    //@PostMapping("/mypage")
    //public ResponseEntity<Token_info> getmyinfo
}
