package KKimjinoh.negoland.nego_member.controller;

import KKimjinoh.negoland.nego_member.dto.Member_request_dto;
import KKimjinoh.negoland.nego_member.dto.Member_response_dto;
import KKimjinoh.negoland.nego_member.dto.Token_info;
import KKimjinoh.negoland.nego_member.dto.Token_request_dto;
import KKimjinoh.negoland.nego_member.reposittory.Authority_repository;
import KKimjinoh.negoland.nego_member.service.Auth_service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class auth_controller implements ErrorController {
    private final Auth_service authService;
    private final Authority_repository authorityRepository;

    // model 안됨 그냥 getmapping controller 하나 클래스 새로 파서
    // @Controller 어노테이션 달고 index.html return 해주기 그게 편할듯

    @PostMapping("/members/sign_up")
    public ResponseEntity<Member_response_dto> sign_up (@RequestBody Member_request_dto memberRequestDto){
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/members/login")
    public ResponseEntity<Token_info> login(@RequestBody Member_request_dto memberRequestDto){
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/members/reissue")
    public ResponseEntity<Token_info> reissue(@RequestBody Token_request_dto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
