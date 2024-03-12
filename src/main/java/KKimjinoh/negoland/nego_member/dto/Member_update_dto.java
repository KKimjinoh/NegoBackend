package KKimjinoh.negoland.nego_member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

//내 정보 수정 요청 dto
public class Member_update_dto {
    private String name;
    private String phone_number;
    private String password;
    private String hometown;
    private String status_message;
    private String member_picture;
}
