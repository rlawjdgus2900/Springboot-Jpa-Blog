package com.mine.blog.test;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 (없으면 GET 요청 시 객체 생성 불가)
@AllArgsConstructor // 모든 필드 포함한 생성자
@ToString // 디버깅용 toString 자동 생성
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;
}
