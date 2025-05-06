package com.fiveup.core.events.model.response;

import com.fiveup.core.management.model.DTO.TeaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 钟承远
 * @date 2022/3/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherSimpleResp {

    private List<TeaDTO> teacherList;
}
