package KKimjinoh.negoland.nego_member.config;

import KKimjinoh.negoland.nego_member.jwt.Jwt_access_denied_handler;
import KKimjinoh.negoland.nego_member.jwt.Jwt_authentication_entry_point;
import KKimjinoh.negoland.nego_member.jwt.Jwt_token_provider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//기본적인 web 보안을 활성화 하겠다는 어노테이션
@EnableWebSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class Security_config extends WebSecurityConfigurerAdapter {
    private final Jwt_token_provider tokenProvider;
    private final Jwt_authentication_entry_point jwtAuthenticationEntryPoint;
    private final Jwt_access_denied_handler jwtAccessDeniedHandler;
    private final StringRedisTemplate redisTemplate;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .apply(new Jwt_security_config(tokenProvider, redisTemplate))

                .and()
                .authorizeRequests()
                //HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정
                .antMatchers("/members/sign_up").permitAll()
                .antMatchers("/members/login").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/media/**").permitAll()

                //위 api는 인증 없이 접근 허용
                .and()
                .csrf()
                .ignoringAntMatchers("/members/sign_up")
                .ignoringAntMatchers("/members/login");
                //csrf 무시
    }
}