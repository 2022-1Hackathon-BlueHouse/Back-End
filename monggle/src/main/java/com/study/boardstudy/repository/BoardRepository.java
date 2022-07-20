package com.study.boardstudy.repository;

import com.study.boardstudy.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    // JpaRepository<BoardStudy, Integer>부분에서 앞에는 entity, 뒤에는 entity의 PK의 타입이 오면 된다.

    // 조회수
//    @Modifying
//    @Query("update Board b set b.view = b.view + 1 where b.idx = :idx")
//    int updateView(Long idx);

    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);


}
