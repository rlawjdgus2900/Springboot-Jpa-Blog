package com.mine.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mine.blog.config.auth.PrincipalDetail;
import com.mine.blog.model.KakaoProfile;
import com.mine.blog.model.OAuthToken;
import com.mine.blog.model.User;
import com.mine.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;



@Controller
public class UserController {

    @Value("${mine.key}")
    private String mineKey;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    // 카카오 로그인 후 인가코드(code)를 받아오는 콜백 메서드
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code, HttpServletRequest request) {
        // HTTP 요청을 보낼 RestTemplate 생성
        RestTemplate rt = new RestTemplate();

        // 요청 헤더 설정 (Content-Type을 x-www-form-urlencoded로 설정)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        // 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); // 고정 값
        params.add("client_id", "e7ac4e1a7dc26ee332a489d9fedb98c5"); // 카카오 앱 REST API 키
        params.add("code", code); // 카카오가 리다이렉트하면서 전달한 인가코드
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback"); // 인가코드를 받을 redirect 주소

        // 헤더와 파라미터를 함께 담은 요청 본문 생성
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            ResponseEntity<String> response = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (Exception e) {
            System.out.println("⚠️ 카카오 인가코드가 유효하지 않거나 재사용됨.");
            return "redirect:/auth/loginForm"; // 로그인 실패시 폼으로 이동
        }
        System.out.println("카카오 엑세스토큰 : "+ oauthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .password(mineKey)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();
        User originUser = userService.회원찾기(kakaoUser.getUsername());
        if (originUser.getId() == 0) {
            System.out.println("기존 회원이 아니기에 회원가입을 진행합니다.");
            userService.회원가입(kakaoUser);
            originUser = userService.회원찾기(kakaoUser.getUsername());
        }


        System.out.println("originUser password: " + originUser.getPassword());
        System.out.println("mineKey 평문: " + mineKey);
        System.out.println("비교 결과: " + encoder.matches(mineKey, originUser.getPassword()));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), mineKey)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession();
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());


        return "redirect:/";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
        model.addAttribute("principal", principalDetail);
        return "user/updateForm";
    }


}
