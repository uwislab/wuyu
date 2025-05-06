package com.fiveup.core.questionnaire.mapper;


import com.fiveup.core.questionnaire.po.Options;
import com.fiveup.core.questionnaire.vo.OptionsVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OptionsMapper {

    int addOption(OptionsVO optionsVO);

    Options findOption(OptionsVO optionsVO);

    void updateOption(OptionsVO optionsVO);

    void deleteByQuestionId(Integer questionId);

    List<Options> selectByQuestionId(int questionId);
}
