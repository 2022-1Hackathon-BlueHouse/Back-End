package com.study.boardstudy.dto;

import com.study.boardstudy.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor @NoArgsConstructor
@Getter @ToString
public class LoginRequestDto {
    private String username;
    private String password;

    public Member toEntity() {
        Member build = Member.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .build();
        return build;
    }


}
