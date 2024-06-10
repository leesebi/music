package com.prac.music.domain.mail.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailRequestDto {
    private String email;

    @Builder
    public MailRequestDto(String email) {
        this.email = email;
    }
}
