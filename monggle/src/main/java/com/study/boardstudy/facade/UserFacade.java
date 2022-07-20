package com.study.boardstudy.facade;

import com.study.boardstudy.entity.Member;
import com.study.boardstudy.exception.MemberNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public Member queryCurrentMember() {
        try {
            return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception ex) {
            throw new MemberNotFoundException();
        }
    }
}
