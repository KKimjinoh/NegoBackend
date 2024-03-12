package KKimjinoh.negoland.nego_member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

//회원가입,로그인 요청시 사용되는 dto
public class Token_request_dto {
    private String accessToken;
    private String refreshToken;
}
