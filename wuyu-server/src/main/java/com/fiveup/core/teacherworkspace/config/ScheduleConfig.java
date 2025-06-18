package com.fiveup.core.teacherworkspace.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class ScheduleConfig {
    @Value("${schedule.auto-copy.enabled:true}")
    private boolean autoCopyEnabled;

}
