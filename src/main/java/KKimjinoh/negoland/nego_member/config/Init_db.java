package KKimjinoh.negoland.nego_member.config;

import KKimjinoh.negoland.nego_member.entitiy.Authority;
import KKimjinoh.negoland.nego_member.entitiy.AuthorityEnum;
import KKimjinoh.negoland.nego_member.reposittory.Authority_repository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class Init_db {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final Authority_repository authorityRepository;

        public void dbInit() {
            authorityRepository.save(new Authority(AuthorityEnum.ROLE_ADMIN));
            authorityRepository.save(new Authority(AuthorityEnum.ROLE_USER));
        }
    }
}
