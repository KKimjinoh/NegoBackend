package KKimjinoh.negoland.nego_member.entitiy;

import KKimjinoh.negoland.nego_member.dto.Member_update_dto;
import KKimjinoh.negoland.nego_member.entitiy.Authority;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="nego_member")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone_number;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String hometown;

    private String status_message;
    private String member_picture;
    private int income;
    private LocalDateTime create_date;
    private LocalDateTime modified_date;

    @ManyToMany
    @JoinTable
            //Member 객체와 권한 객체를 다대다 관계로 설정함
            (
                    name = "member_authority",
                    joinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"),
                    inverseJoinColumns = @JoinColumn(name = "authority_status", referencedColumnName = "authority_status"))
    private Set<Authority> authorities = new HashSet<>();

    @Builder
    
    public Member(String email, String name, String password, String phone_number, String nickname, String hometown,
                  String status_message, String member_picture, int income, LocalDateTime create_date, LocalDateTime modified_date, Set<Authority> authorities) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phone_number = phone_number;
        this.nickname = nickname;
        this.hometown = hometown;
        this.status_message = status_message;
        this.member_picture = member_picture;
        this.income = income;
        this.create_date = create_date;
        this.modified_date = modified_date;
        this.authorities = authorities;
    }

    // 회원 정보 수정
    public void updateMember(Member_update_dto dto, PasswordEncoder passwordEncoder) {
        if(dto.getPassword() != null) this.password = passwordEncoder.encode(dto.getPassword());
        if(dto.getName() != null) this.name = dto.getName();
        if (dto.getPhone_number() != null) this.phone_number = dto.getPhone_number();
        if (dto.getHometown() != null) this.hometown = dto.getHometown();
        if (dto.getStatus_message() != null) this.status_message = dto.getStatus_message();
        if (dto.getMember_picture() != null) this.member_picture = dto.getMember_picture();
    }
}
