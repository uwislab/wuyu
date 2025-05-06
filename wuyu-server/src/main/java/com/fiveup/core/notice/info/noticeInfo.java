package com.fiveup.core.notice.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class noticeInfo {
    private int id;
    private LocalDate releaseTime;
    private String theme;
    private String content;
}
