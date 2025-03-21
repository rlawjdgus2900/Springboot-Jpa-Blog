package com.mine.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // IoC 등록
public class SecurityConfig {

    // 🔐 비밀번호 암호화 (BCrypt 사용)
    @Bean
    BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    // 🔐 보안 필터 체인 설정
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) //csrf 비활성화
                // 📌 경로별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**","/js/**","/css/**","/image/**", "/", "/blog").permitAll()      // 로그인, 회원가입만 허용
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근
                        .anyRequest().permitAll()                // 나머지 → 로그인 필요
                )

                // 📌 로그인 폼 설정
                .formLogin(form -> form
                        .loginPage("/auth/loginForm")// 커스텀 로그인 페이지 URL
                        .loginProcessingUrl("/login") // 로그인 처리 URL 명시
                        .defaultSuccessUrl("/")   // 로그인 성공 시 이동할 URL
                );

        return http.build(); // 설정 완료 후 반환
    }
}
