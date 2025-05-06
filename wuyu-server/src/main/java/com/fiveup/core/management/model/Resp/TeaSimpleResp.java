package com.fiveup.core.management.model.Resp;

import com.fiveup.core.management.model.DTO.TeaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/3/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeaSimpleResp {

    private List<TeaDTO> monitorList;
}
