package KKimjinoh.negoland.nego_member.service;

import KKimjinoh.negoland.nego_member.dto.Member_response_dto;
import KKimjinoh.negoland.nego_member.dto.Member_update_dto;
import KKimjinoh.negoland.nego_member.entitiy.Member;
import KKimjinoh.negoland.nego_member.entitiy.Refresh_token;
import KKimjinoh.negoland.nego_member.reposittory.Member_repository;
import KKimjinoh.negoland.nego_member.reposittory.Refresh_token_repository;
import KKimjinoh.negoland.nego_member.util.Security_util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class Member_service {

    private final Member_repository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final Refresh_token_repository refreshTokenRepository;
    private final StringRedisTemplate redisTemplate;

    /**
     내 정보 조회
    SecurityUtil.getLoginMemberId()를 통해 현재 로그인 되어있는 유저 정보의 ID를 가져옴
    Member_repository에서 Id로 유저 정보 가져와 dto로 리턴
     **/
    @Transactional(readOnly = true)
    public Member_response_dto getMyInfo(){
        return memberRepository.findById(Security_util.getLoginMember())
                .map(Member_response_dto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    /**
     현재 로그인 되어 있는 유저의 Id로 Member_repository에서 member 객체 가져옴
     member.updateMember 메소드를 호출해서 update(더티체킹)
     **/
    @Transactional
    public void updateMyInfo(Member_update_dto dto) {
        Member member = memberRepository
                    .findById(Security_util.getLoginMember())
                    .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));

        member.updateMember(dto,passwordEncoder);
    }

    /**
     Request 헤더에서 accessToken 정보 가져옴
     redis 메소드를 통해 블랙리스트에 accesstoken을 등록시킴
     그 후 Refresh_token_repository에서 로그인 되어 있는 ID key값과 매핑해서 삭제
     **/
    @Transactional
    public void logout(HttpServletRequest request) {

        // accessToken redisTemplate 블랙리스트 추가
        String jwt = request.getHeader("Authorization").substring(7);
        ValueOperations<String, String> logoutValueOperations = redisTemplate.opsForValue();
        logoutValueOperations.set(jwt, jwt); // redis set 명령어

        // refreshToken 삭제
        refreshTokenRepository.deleteByKey(String.valueOf(Security_util.getLoginMember()))
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    /**
     로그인 되어 있는 유저 Id를 Member_repository에서 삭제함
     */
    @Transactional
    public void deleteMember() {
        Long loginMemberId = Security_util.getLoginMember();
        if (loginMemberId == null) {
            throw new RuntimeException("로그인 유저 정보가 없습니다.");
        }
        memberRepository.deleteById(loginMemberId);
    }
}
