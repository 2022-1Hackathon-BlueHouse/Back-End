package com.study.boardstudy.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder @Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private String title;

    private String content;

    private String filename;

    private String filepath;

    private String wdate;

    @Column(columnDefinition = "Integer default 0", nullable = false)
    private Integer count;
}
