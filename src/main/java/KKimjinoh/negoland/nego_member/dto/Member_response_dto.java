package KKimjinoh.negoland.nego_member.dto;

import KKimjinoh.negoland.nego_member.entitiy.Authority;
import KKimjinoh.negoland.nego_member.entitiy.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;


@Getter
@AllArgsConstructor
@NoArgsConstructor

//회원가입 응답 dto
public class Member_response_dto {
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
    private Set<Authority> authorities;

    public static Member_response_dto of(Member member){
       return new Member_response_dto(member.getEmail(),member.getPassword(),
               member.getName(),member.getPhone_number(),member.getNickname(),
               member.getHometown(),member.getStatus_message(),member.getMember_picture(),
               member.getIncome(),member.getCreate_date(),member.getModified_date(),
               member.getAuthorities());
    }
}
