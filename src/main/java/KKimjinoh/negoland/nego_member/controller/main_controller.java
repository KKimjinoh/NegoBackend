package KKimjinoh.negoland.nego_member.controller;

import KKimjinoh.negoland.nego_member.service.Auth_service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class main_controller {

    private final Auth_service authService;

    @GetMapping("/")
    public String mainPage(){
        log.info("1");
        return "index";
    }

    @GetMapping("/members/sign_up")
    public String signup(){
        return "index";
    }

    @GetMapping("/members/login")
    public String login(){
        return "index";
    }

}
