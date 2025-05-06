package com.fiveup.core.cultivation.controller;

import com.fiveup.core.cultivation.entity.Goal;
import com.fiveup.core.cultivation.entity.Guidance;
import com.fiveup.core.cultivation.service.GoalService;
import com.fiveup.core.cultivation.service.GuidanceService;
import com.fiveup.core.cultivation.untils.Constants;
import com.fiveup.core.cultivation.untils.FileManager;
import com.fiveup.core.cultivation.entity.vo.Result;
import com.fiveup.core.management.common.CommonResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Harvi
 */
@RestController
@RequestMapping("/api/guidance")
public class GuidanceController {
    private final GuidanceService guidanceService;
    private final GoalService goalService;

    @Autowired
    public GuidanceController(GuidanceService filesService, GoalService goalService) {
        this.guidanceService = filesService;
        this.goalService = goalService;
    }

    @RequestMapping("/paging")
    public Object paging(@RequestBody Guidance o) {
        int pageNo = o.getPageNo() == null ? 1 : o.getPageNo();
        int pageSize = o.getPageSize() == null ? 10 : o.getPageSize();
        PageHelper.startPage(pageNo, pageSize, "create_date desc");
        return new PageInfo<>(guidanceService.getAll(o), pageSize);
    }

    @RequestMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file, Integer tid) {
        Result<String> result = new Result<>();
        Map<String, String> map = FileManager.upload(file);
        if (map == null) {
            result.setCode(500);
            result.setMsg("上传失败");
        } else {
            try {
                String name = map.get("fileName");
                String location = map.get("fileLocation");

                Guidance o = new Guidance();
                o.setTid(tid);
                o.setFileName(name);
                o.setFileLocation(location);
                o.setCreateDate(new Date());
                o.setModifyDate(new Date());
                guidanceService.save(o);

                result.setCode(200);
                result.setMsg("上传成功");
                result.setData(location);
            } catch (Exception e) {
                result.setCode(500);
                result.setMsg("上传失败");
            }
        }
        return result;
    }

    @RequestMapping("/insert")
    public Result<Void> insert(@RequestBody Guidance o) {
        Result<Void> result = new Result<>();
        guidanceService.updateById(o);
        result.setCode(200);
        result.setMsg("操作成功");
        return result;
    }

    @RequestMapping("/list")
    public CommonResponse list(@RequestBody Guidance o) {
        List<Guidance> guidanceList = guidanceService.getAll(o);
        for (int i = 0; i < guidanceList.size(); i++) {
            StringBuilder s = new StringBuilder();
            for (String gid: guidanceList.get(i).getGid().split(Constants.SLASH)) {
                if (Objects.equals(gid, "")) {
                    continue;
                }
                Goal goal = goalService.getById(Integer.parseInt(gid));
                if (goal != null) {
                    s.append(goal.getTitle()).append("/");
                }
            }
            guidanceList.get(i).setGuidanceNames(s.toString());
        }
        return CommonResponse.ok(guidanceList);
    }

    @RequestMapping("/delete")
    public Result<Void> delete(@RequestBody Guidance o) {
        Result<Void> result = new Result<>();
        System.out.println(o.toString());
        Guidance guidance = guidanceService.getById(o.getId());

        if (guidance.getGid().length() > 0) {
            result.setCode(500);
            result.setMsg("删除失败，该大纲正在被引用");
            return result;
        }

        o.setIsDeleted(true);
        guidanceService.updateById(o);

//        if (guidance.getFileLocation() != null) {
//            String fileName = guidance.getFileLocation().replace("http://82.157.231.88:9200/cultivationFile/guidances/", "");
//            if (!FileManager.delete(fileName)) {
//                result.setCode(500);
//                result.setMsg("删除失败，文件不存在");
//                return result;
//            }
//        }

        result.setCode(200);
        result.setMsg("已删除");
        return result;
    }
}
