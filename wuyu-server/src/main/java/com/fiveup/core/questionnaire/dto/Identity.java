package com.fiveup.core.questionnaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 史林
 * @date 2022/9/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Identity {
    private int identityId;
    private String identityInfo;
}
