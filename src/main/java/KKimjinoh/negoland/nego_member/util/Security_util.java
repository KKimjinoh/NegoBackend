package KKimjinoh.negoland.nego_member.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class Security_util {
    //로그인 되어있는 유저의 정보가 담긴 Id를 가져오는 클래스
    private Security_util(){}
    public static Long getLoginMember() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw  new RuntimeException("로그인 유저 정보가 없습니다.");
        }

        Long LoginId = Long.parseLong(authentication.getName());
        return LoginId;
    }
}
