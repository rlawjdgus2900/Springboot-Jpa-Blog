package com.mine.blog.config.auth;

import com.mine.blog.model.User;
import com.mine.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 시큐리티 세션 = Authentication = UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("🔍 loadUserByUsername 호출됨: " + username);

        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));

        System.out.println("🔐 DB에서 불러온 유저: " + userEntity.getUsername());
        System.out.println("🔐 비밀번호: " + userEntity.getPassword());

        return new PrincipalDetail(userEntity); // 여기서 비번이 잘 들어간 상태여야 함
    }


}
