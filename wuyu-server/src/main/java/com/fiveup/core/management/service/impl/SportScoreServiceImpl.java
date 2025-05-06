package com.fiveup.core.management.service.impl;

import com.fiveup.core.management.mapper.SportScoreMapper;
import com.fiveup.core.management.pojo.SportScore;
import com.fiveup.core.management.pojo.SportSearchVO;
import com.fiveup.core.management.service.SportScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class SportScoreServiceImpl implements SportScoreService {

    @Resource
    private SportScoreMapper dao;
    @Override
    public List<SportScore> getSportScore() {
        return dao.findAllSportScore();
    }

    @Override
    public List<SportScore> findSportScoreForSearch(SportSearchVO sportSearchVO) {
        return dao.findSportScoreForSearch(sportSearchVO);
    }

    @Override
    public List<String> editBmiSportScore(SportScore score) {
        List<String> flag=new ArrayList<>();
        try{
            if (score.getFvcscore()!=null){
                if (dao.findFvcSportScoreByStudentNum(score.getStudentNum())>0){
                    if(dao.editFvcSportScore(score)<=0){
                        flag.add("肺活量数据修改失败");
                    }
                }else {
                    if(dao.addFvcSportScore(score)<=0){
                        flag.add("肺活量数据修改失败");
                    }
                }
            }
            if (score.getRsscore()!=null){
                if (dao.findRsSportScoreByStudentNum(score.getStudentNum())>0){
                    if(dao.editRsSportScore(score)<=0){
                        flag.add("跳远数据修改失败");
                    }
                }else {
                    if(dao.addRsSportScore(score)<=0){
                        flag.add("跳远数据修改失败");
                    }
                }
            }
            if (score.getSarscore()!=null){
                if (dao.findSarSportScoreByStudentNum(score.getStudentNum())>0){
                    if(dao.editSarSportScore(score)<=0){
                        flag.add("仰卧起坐数据修改失败");
                    }
                }else {
                    if(dao.addSarSportScore(score)<=0){
                        flag.add("仰卧起坐数据修改失败");
                    }
                }
            }
            if (score.getOabscore()!=null){
                if (dao.findOabSportScoreByStudentNum(score.getStudentNum())>0){
                    if(dao.editOabSportScore(score)<=0){
                        flag.add("50米往返跑数据修改失败");
                    }
                }else {
                    if(dao.addOabSportScore(score)<=0){
                        flag.add("50米往返跑数据修改失败");
                    }
                }
            }
            if (score.getDashscore()!=null){
                if (dao.findDashSportScoreByStudentNum(score.getStudentNum())>0){
                    if(dao.editDashSportScore(score)<=0){
                        flag.add("50米短跑数据修改失败");
                    }
                }else {
                    if(dao.addDashSportScore(score)<=0){
                        flag.add("50米短跑数据修改失败");
                    }
                }
            }
            if (score.getSubscore()!=null){
                if (dao.findSubSportScoreByStudentNum(score.getStudentNum())>0){
                    if(dao.editSubSportScore(score)<=0){
                        flag.add("坐位前屈数据修改失败");
                    }
                }else {
                    if(dao.addSubSportScore(score)<=0){
                        flag.add("坐位前屈数据修改失败");
                    }
                }
            }
            if (score.getBmiscore()!=null){
                if (dao.findBmiSportScoreByStudentNum(score.getStudentNum())>0){
                    if(dao.editBmiSportScore(score)<=0){
                        flag.add("体重指数数据修改失败");
                    }
                }else {
                    if(dao.addBmiSportScore(score)<=0){
                        flag.add("体重指数数据修改失败");
                    }
               }
            }
        }catch (Exception e){
            log.error("修改数据失败");
            flag.add("修改数据失败");
        }
        return flag;
    }

    @Override
    public List<String> removeSportScore(SportScore score) {
        List<String> flag=new ArrayList<>();
        try{
            if (score.getBmiscore() != null && dao.deleteBmiSportScore(score.getStudentNum()) <= 0) {
                flag.add("体重指数数据删除失败");
            }
            if (score.getFvcscore() != null && dao.deleteFvcSportScore(score.getStudentNum()) <= 0) {
                flag.add("肺活量数据删除失败");
            }
            if (score.getRsscore() != null && dao.deleteRsSportScore(score.getStudentNum()) <= 0) {
                flag.add("跳远数据删除失败");
            }
            if (score.getSarscore() != null && dao.deleteSarSportScore(score.getStudentNum()) <= 0) {
                flag.add("仰卧起坐数据删除失败");
            }
            if (score.getOabscore() != null && dao.deleteOabSportScore(score.getStudentNum()) <= 0) {
                flag.add("50米往返跑数据删除失败");
            }
            if (score.getDashscore() != null && dao.deleteDashSportScore(score.getStudentNum()) <= 0) {
                flag.add("50米短跑数据删除失败");
            }
            if (score.getSubscore() != null && dao.deleteSubSportScore(score.getStudentNum()) <= 0) {
                flag.add("坐位前屈数据删除失败");
            }
        }catch (Exception e){
            log.error("删除数据失败");
            flag.add("删除数据失败");
        }
        return flag;
    }
    @Override
    public List<String> addSportScore(SportScore score) {
        List<String> flag=new ArrayList<>();
        int classId = dao.findClassIdByStudentNum(score.getStudentNum());
        if(classId==0){
            flag.add("学生信息不存在");
            return flag;
        }
        if(dao.findFvcSportScoreByStudentNum(score.getStudentNum())>0||
           dao.findRsSportScoreByStudentNum(score.getStudentNum())>0||
           dao.findSarSportScoreByStudentNum(score.getStudentNum())>0||
           dao.findOabSportScoreByStudentNum(score.getStudentNum())>0||
           dao.findDashSportScoreByStudentNum(score.getStudentNum())>0||
           dao.findSubSportScoreByStudentNum(score.getStudentNum())>0||
           dao.findBmiSportScoreByStudentNum(score.getStudentNum())>0
        ){
            flag.add("学生数据已存在");
            return flag;
        }
        score.setClassNum(classId);
        try{
            if (score.getFvcscore()!=null){
                if (dao.findFvcSportScoreByStudentNum(score.getStudentNum())>0){
                    flag.add("肺活量数据已存在");
                }else {
                    if(dao.addFvcSportScore(score)<=0){
                        flag.add("肺活量数据添加失败");
                    }
                }
            }
            if (score.getRsscore()!=null){
                if (dao.findRsSportScoreByStudentNum(score.getStudentNum())>0){
                    flag.add("跳绳数据已存在");
                }else {
                    if(dao.addRsSportScore(score)<=0){
                        flag.add("跳绳数据添加失败");
                    }
                }
            }
            if (score.getSarscore()!=null){
                if (dao.findSarSportScoreByStudentNum(score.getStudentNum())>0){
                    flag.add("仰卧起坐数据已存在");
                }else {
                    if(dao.addSarSportScore(score)<=0){
                        flag.add("仰卧起坐数据添加失败");
                    }
                }
            }
            if (score.getOabscore()!=null){
                if (dao.findOabSportScoreByStudentNum(score.getStudentNum())>0){
                    flag.add("50米往返跑数据已存在");
                }else {
                    if(dao.addOabSportScore(score)<=0){
                        flag.add("50米往返跑数据添加失败");
                    }
                }
            }
            if (score.getDashscore()!=null){
                if (dao.findDashSportScoreByStudentNum(score.getStudentNum())>0){
                    flag.add("50米短跑数据已存在");
                }else {
                    if(dao.addDashSportScore(score)<=0){
                        flag.add("50米短跑数据添加失败");
                    }
                }
            }
            if (score.getSubscore()!=null){
                if (dao.findSubSportScoreByStudentNum(score.getStudentNum())>0){
                    flag.add("坐位前屈数据已存在");
                }else {
                    if(dao.addSubSportScore(score)<=0){
                        flag.add("坐位前屈数据添加失败");
                    }
                }
            }
            if (score.getBmiscore()!=null){
                if (dao.findBmiSportScoreByStudentNum(score.getStudentNum())>0){
                    flag.add("体重指数数据已存在");
                }else {
                    if(dao.addBmiSportScore(score)<=0){
                        flag.add("体重指数数据添加失败");
                    }
                }
            }
        }catch (Exception e){
            log.error("添加数据失败");
            flag.add("添加数据失败");
        }
        return flag;
    }

    /**
     * 批量删除体育成绩
     * @param ids 学号集
     * @return 删除失败原因
     */
    @Override
    public List<String> deleteBatchSportScore(List<String> ids) {
        List<String> flag=new ArrayList<>();
        try{
            dao.deleteBatchFvc(ids);
            dao.deleteBatchRs(ids);
            dao.deleteBatchSar(ids);
            dao.deleteBatchOab(ids);
            dao.deleteBatchDash(ids);
            dao.deleteBatchSub(ids);
            dao.deleteBatchBmi(ids);
        }catch (Exception e){
            log.error("批量删除数据失败");
            flag.add("批量删除数据失败");
        }
        return flag;
    }
}
