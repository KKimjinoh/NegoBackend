package KKimjinoh.negoland.nego_member.reposittory;

import KKimjinoh.negoland.nego_member.entitiy.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Member_repository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
}
