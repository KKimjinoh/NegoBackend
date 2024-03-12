package KKimjinoh.negoland.nego_member.dto;

import KKimjinoh.negoland.nego_member.entitiy.Authority;
import KKimjinoh.negoland.nego_member.entitiy.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member_request_dto {
    private String email;
    private String password;
    private String name;
    private String phone_number;
    private String nickname;
    private String hometown;
    private String status_message;
    private String member_picture;
    private int income;
    private LocalDateTime create_date;
    private LocalDateTime modified_date;

    public Member toMember(PasswordEncoder passwordEncoder, Set<Authority> authorities){
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .phone_number(phone_number)
                .hometown(hometown)
                .status_message(status_message)
                .income(income)
                .create_date(create_date)
                .modified_date(modified_date)
                .authorities(authorities)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
