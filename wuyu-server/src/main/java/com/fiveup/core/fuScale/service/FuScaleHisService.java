package com.fiveup.core.fuScale.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiveup.core.fuScale.model.*;
import com.fiveup.core.management.common.CommonResponse;

import java.util.List;

public interface FuScaleHisService {

    List<ScaleHisInfo> getScaleBySate(Integer stateId);

}
