package com.study.boardstudy.controller;

import com.study.boardstudy.dto.BoardCreationDto;
import com.study.boardstudy.dto.ListResponse;
import com.study.boardstudy.entity.Board;
import com.study.boardstudy.service.BoardService;
import com.study.boardstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/write")
    public String boardWritePro(@RequestBody BoardCreationDto boardCreationDto, MultipartFile file) throws Exception {

        // 원래 boardStudy.getTitle()을 못 가져오지만 lombok을 이용해 entity 부분에 @Data를 작성해주면 boardStudy.getTitle()을 가져올 수 있다.
//        System.out.println(boardStudy.getTitle());

        System.out.println(boardCreationDto.toString());

        boardService.write(boardCreationDto, file);

        return "message";
    }

    @GetMapping("/list")
    public ListResponse boardList(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "query", defaultValue = "") String searchKeyword) { // Model은 데이터를 담아서 페이지로 보내주기 위해 사용.
        Pageable pageable = PageRequest.of(page - 1, 13, Sort.Direction.DESC, "idx");

        Page<Board> list = null;

        if(searchKeyword == null || searchKeyword.equals("")) {
            list = boardService.boardList(pageable);
        } else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1; // 0부터 시작하기 때문에 1을 더해서 1페이지부터 볼 수 있게 한다.
        int startPage = Math.max(nowPage -4, 1); // 매개변수로 들어온 두 값을 비교해서 더 높은 값을 반환하게 된다.
        int endPage = list.getTotalPages();

        //boardService.boardList()의 리스트가 반환되는데 그 리스트를 "list"라는 이름으로 넘긴다.
//        model.addAttribute("list", list);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);


        // 프론트에 연결
        return ListResponse.builder()
                .list(list)
                .nowPage(nowPage)
                .startPage(startPage)
                .endPage(endPage)
                .build();
    }

    @GetMapping("/view")
    public String boardView(Model model, Integer idx) {

        model.addAttribute("board", boardService.boardView(idx));
        return "boardview";
    }

    @GetMapping("/delete")
    public String boardDelete(Integer idx) {

        boardService.boardDelete(idx);
        return "redirect:/board/list";
    }

    @GetMapping("/modify/{idx}")
    public String boardModify(@PathVariable("idx") Integer idx, Model model) {

        model.addAttribute("board", boardService.boardView(idx));
        return "boardmodify";
    }

    @PostMapping("/update/{idx}")
    public void boardUpdate(@PathVariable("idx") Integer idx, @RequestBody BoardCreationDto boardCreationDto, MultipartFile file) throws Exception {
        boardService.update(idx, boardCreationDto, file);
    }

//    @GetMapping("/")
//    public String test() {
//        return "접근 테스트";
//    }

//    @GetMapping("/count/read/{idx}")
//    public String boardCount() {
//        CountResponseDto countResponseDto = boardService.
//    }

}
