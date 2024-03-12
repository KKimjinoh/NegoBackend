package KKimjinoh.negoland.nego_member.entitiy;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
public class Refresh_token {
    @Id
    @Column(name = "rt_key")
    private String key;

    @Column(name = "rt_value")
    private String value;

    @Builder
    public Refresh_token(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Refresh_token updateValue(String token) {
        this.value = token;
        return this;
    }
}
