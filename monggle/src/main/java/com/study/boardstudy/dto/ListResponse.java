package com.study.boardstudy.dto;

import com.study.boardstudy.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter @AllArgsConstructor
@Builder
public class ListResponse {

    private Page<Board> list;

    private int nowPage;

    private int startPage;

    private int endPage;

}
