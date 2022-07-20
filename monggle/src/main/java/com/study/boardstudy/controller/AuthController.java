package com.study.boardstudy.controller;

import com.study.boardstudy.dto.LoginRequestDto;
import com.study.boardstudy.entity.Member;
import com.study.boardstudy.exception.MemberNotFoundException;
import com.study.boardstudy.exception.PasswordMismatchException;
import com.study.boardstudy.repository.MemberRepository;
import com.study.boardstudy.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @PostMapping("/login")
    public void login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info("HTTP call {}", System.currentTimeMillis());
        log.info(loginRequestDto.toString());

        Member member = memberRepository.findByUsername(loginRequestDto.getUsername())
                        .orElseThrow(MemberNotFoundException::new);

        if(!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword()))
            throw new PasswordMismatchException();

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(member, null, new ArrayList<>()));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void signUp(@RequestBody LoginRequestDto loginRequestDto) {
        memberService.saveUser(loginRequestDto);
    }


//    @GetMapping("/register")
//    public String signUp() {
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String signUp() {
//
//        memberService.joinUser
//        return "redirect:/login";
//    }
}
