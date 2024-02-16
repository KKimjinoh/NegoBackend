package KKimjinoh.negoland.nego_member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder

public class Nego_member_Dto {
    private String name;
    private String email;
    private String password;
    private String phone_number;
    private String nickname;
    private String hometown;
    private String status_message;
    private String member_picture;
    private int income;
    private LocalDateTime create_date;
    private LocalDateTime modified_date;
}