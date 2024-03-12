package KKimjinoh.negoland.nego_member.service;

import KKimjinoh.negoland.nego_member.entitiy.Member;
import KKimjinoh.negoland.nego_member.reposittory.Member_repository;
import KKimjinoh.negoland.nego_member.entitiy.Authority;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

//Member_repository를 주입받아 로그인 할 때 DB에서 유저 정보를 가져오는 클래스

public class Member_details_service implements UserDetailsService {

    private final Member_repository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        log.info("loadUserByUsername 실행됨");
        return memberRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다"));
    }
    //이때 가져온 정보를 userdetails 객체로 만들어서 return
    private UserDetails createUserDetails(Member member) {
        List<SimpleGrantedAuthority> authList = member.getAuthorities()
                .stream()
                .map(Authority::getAuthorityStatus)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        authList .forEach(o-> log.debug("authList -> {}",o.getAuthority()));

        return new User(
                String.valueOf(member.getId()),
                member.getPassword(),
                authList
        );
    }
}