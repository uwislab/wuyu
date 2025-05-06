package com.fiveup.core.cultivation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveup.core.cultivation.entity.AspectActivity;

import java.util.List;

/**
 * @author Harvi
 */
public interface AspectActivityService extends IService<AspectActivity> {
    /**
     * TODO
     * @param activity TODO
     * @return TODO
     */
    List<AspectActivity> getListByAid(AspectActivity activity);

    /**
     * TODO
     * @param gid TODO
     */
    void deleteByGid(Integer gid);

    /**
     * TODO
     * @param aid TODO
     */
    void deleteById(Integer id);

    /**
     * 删除
     *
     * @param aid
     */
    void deleteByAid(Integer aid);
}
