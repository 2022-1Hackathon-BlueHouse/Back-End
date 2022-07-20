package com.study.boardstudy.service;

import com.study.boardstudy.dto.BoardCreationDto;
import com.study.boardstudy.entity.Board;
import com.study.boardstudy.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 글 작성 처리
    // MultipartFile은 글 작성할 때 파일을 불러오기 위해 사용하는 인터페이스.
    public void write(BoardCreationDto boardCreationDto, MultipartFile file) throws Exception {

        // System.getProperty("user.dir")을 하면 프로젝트 경로를 projectPath에 담아주게 된다.
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        // UUID는 식별자. UUID.randomUUID()를 사용하면 랜덤으로 이름을 만들어 줄 수 있다.
        UUID uuid = UUID.randomUUID();

        Board board = Board.builder()
                .title(boardCreationDto.getTitle())
                .content(boardCreationDto.getContent())
                .wdate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .count(0)
                .build();

        if(file != null && !file.isEmpty()) {
            // uuid로 인해 랜덤으로 이름이 붙은 다음에 원래 파일이름이 붙게 된다.
            String fileName = uuid + "_" + file.getOriginalFilename();

            File svaeFile = new File(projectPath, fileName); // 이름은 fileName으로 담긴다.

            file.transferTo(svaeFile);

            board.setFilename(fileName);
            board.setFilepath("/files/" + fileName);
        }

        boardRepository.save(board);
    }

    @Transactional
    public void update(int idx, BoardCreationDto boardCreationDto, MultipartFile file) {
        Board board = boardRepository.findById(idx).orElse(null);
        board.setTitle(boardCreationDto.getTitle());
        board.setContent(boardCreationDto.getContent());
    }

    // 게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable) { // List<> 는 매개변수가 없을 때 사용가능.

        return boardRepository.findAll(pageable);
    }

    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer idx) {

        return boardRepository.findById(idx).get();
        // return boardStudyRepository.findById(idx); 는 public Optional<Board> boardView(Integer idx) { } 로 작성하면 사용 가능.
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer idx) {

        boardRepository.deleteById(idx);
    }

    // 조회수
//    @Transactional
//    public int updateView(Long idx) {
//        return boardRepository.updateView(idx);
//    }

}
