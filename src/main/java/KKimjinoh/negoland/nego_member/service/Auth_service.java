package KKimjinoh.negoland.nego_member.service;

import KKimjinoh.negoland.nego_member.dto.Member_request_dto;
import KKimjinoh.negoland.nego_member.dto.Member_response_dto;
import KKimjinoh.negoland.nego_member.dto.Token_info;
import KKimjinoh.negoland.nego_member.dto.Token_request_dto;
import KKimjinoh.negoland.nego_member.entitiy.Authority;
import KKimjinoh.negoland.nego_member.entitiy.AuthorityEnum;
import KKimjinoh.negoland.nego_member.entitiy.Member;
import KKimjinoh.negoland.nego_member.entitiy.Refresh_token;
import KKimjinoh.negoland.nego_member.jwt.Jwt_token_provider;
import KKimjinoh.negoland.nego_member.reposittory.Authority_repository;
import KKimjinoh.negoland.nego_member.reposittory.Member_repository;
import KKimjinoh.negoland.nego_member.reposittory.Refresh_token_repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class Auth_service {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final Authority_repository authorityRepository;
    private final Member_repository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final Jwt_token_provider tokenProvider;
    private final Refresh_token_repository refreshTokenRepository;


    //회원가입
    @Transactional
    public Member_response_dto signup(Member_request_dto memberRequestDto) {
        // 이메일 중복 회원 검증
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 이메일입니다.");
        }

        Authority authority = authorityRepository
                .findByAuthorityStatus(AuthorityEnum.ROLE_USER).orElseThrow(()->new RuntimeException("권한 정보가 없습니다."));
        Set<Authority> set = new HashSet<>();
        set.add(authority);

        Member member = memberRequestDto.toMember(passwordEncoder, set);
        return Member_response_dto.of(memberRepository.save(member));
    }
    
    
    //로그인
    @Transactional
    public Token_info login(Member_request_dto memberRequestDto) {
        // Dto의 email, password를 받고 UsernamePasswordAuthenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
        log.info("1");
        log.info(authenticationToken.toString());

        // authenticate 메서드가 실행이 될 때 Member_details_service 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("2");
        // JWT 토큰 생성
        Token_info tokenInfo = tokenProvider.generateTokenDto(authentication);
        log.info("3");

        // refreshToken 저장
        Refresh_token refreshToken = Refresh_token.builder()
                .key(authentication.getName())
                .value(tokenInfo.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);
        log.info("4");

        return tokenInfo;
    }

    //토큰 재발급
    @Transactional
    public Token_info reissue(Token_request_dto tokenRequestDto) {
        // refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // access Token에서 Authentication객체 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // DB에서 member_id를 기반으로 Refresh Token 값 가져옴
        Refresh_token refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // refresh Token이 다르면
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 새로운 토큰 생성
        Token_info tokenInfo = tokenProvider.generateTokenDto(authentication);

        // refreshToken 업데이트
        Refresh_token newRefreshToken = refreshToken.updateValue(tokenInfo.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenInfo;
    }
}