package KKimjinoh.negoland.nego_member.entitiy;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="nego_member")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Nego_member_Entity {
    @javax.persistence.Id
    @Id
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="phone_number")
    private String phone_number;
    private Long nego_id;

}
