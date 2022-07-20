package com.study.boardstudy.service;

import com.study.boardstudy.dto.LoginRequestDto;
import com.study.boardstudy.entity.Member;
import com.study.boardstudy.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
//@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public String saveUser(LoginRequestDto loginRequestDto) {
        return memberRepository.save(loginRequestDto.toEntity()).getUsername();
    }


//    public Member joinUser(LoginRequestDto loginRequestDto) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        loginRequestDto.setUpwd(passwordEncoder.encode(loginRequestDto.getUpwd()));
//
//        return memberRepository.save(loginRequestDto.toEntity());
//    }

}
