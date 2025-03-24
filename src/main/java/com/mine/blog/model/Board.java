package com.mine.blog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량 데이터
    private String content; // 썸머노트 라이브러리


    private int views;

    @ManyToOne // Many = Board, User = One
    @JoinColumn(name="userId", nullable = false)
    private User user; // DB 는 오브젝트 저장 X. 자바는 오브젝트 저장 O

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // mappedBy = 연관관계주인 X Not FK / DB에 칼럼 만들지 X
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;
}
