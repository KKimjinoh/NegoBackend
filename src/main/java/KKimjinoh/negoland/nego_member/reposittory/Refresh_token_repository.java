package KKimjinoh.negoland.nego_member.reposittory;

import KKimjinoh.negoland.nego_member.entitiy.Refresh_token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Refresh_token_repository extends JpaRepository<Refresh_token,String> {
    Optional<Refresh_token> findByKey(String key);
    Optional<Refresh_token> deleteByKey(String key);
}
