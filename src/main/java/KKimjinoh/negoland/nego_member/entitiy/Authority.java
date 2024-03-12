package KKimjinoh.negoland.nego_member.entitiy;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority {

    @Id
    @Column(name = "authority_status")
    @Enumerated(EnumType.STRING)
    private AuthorityEnum authorityStatus;

    public String getAuthorityStatus() {
        return this.authorityStatus.toString();
    }

    @Builder
    public Authority(AuthorityEnum authorityStatus) {
        this.authorityStatus = authorityStatus;
    }
}
