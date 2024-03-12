package KKimjinoh.negoland.nego_member.reposittory;

import KKimjinoh.negoland.nego_member.entitiy.Authority;
import KKimjinoh.negoland.nego_member.entitiy.AuthorityEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Authority_repository extends JpaRepository<Authority, AuthorityEnum> {
    Optional<Authority> findByAuthorityStatus(AuthorityEnum authorityStatus);
}
