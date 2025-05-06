package com.fiveup.core.diagnose.service;


import com.fiveup.core.diagnose.bean.student_sportsScore;
import com.fiveup.core.diagnose.mapper.SportsScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SportsScoreService {

    @Autowired
    SportsScoreMapper sportsScoreMapper;
    student_sportsScore sss = new student_sportsScore();
    public  student_sportsScore getSportsScoreByKey(String name, int id){
        return sportsScoreMapper.getSportsScoreByKey(name,id);
    }

    public Map<String, Object> StudnettiyuScoreDetail(String name, int id) {
        sss=getSportsScoreByKey(name,id);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("studentId", sss.getStudentId());
        resultMap.put("studentName", sss.getStudentName());
        resultMap.put("sportsMorality", sss.getSportsMorality());
        resultMap.put("healthKnowledge", sss.getHealthKnowledge());
        resultMap.put("mentalHealth", sss.getMentalHealth());
        resultMap.put("sportsSkills", sss.getSportsSkills());
        resultMap.put("sportsActivity", sss.getSportsActivity());

        return resultMap;
    }

    /*分析体育在各方面的发展*/

    public String StudnettiyuScoreDiagnose(String name, int id){
        String diagnose="";
        sss=getSportsScoreByKey(name,id);
        if(sss.getSportsMorality()<=2&&sss.getHealthKnowledge()<=2){
            diagnose=diagnose+"希望能够早日融入班级，积极参与活动,游戏，与同学团结合作，和睦相处，这样能交到更多朋友;" +
                    "关于体育与健康的知识不是一朝得来的，是需要日积月累的，多关注平常生活的健康常识。";
        }
        else{
            diagnose=diagnose+"能够与同学积极游戏，团结合作，热情开朗的你也了解了许多健康常识，继续保持，这样你的朋友就会" +
                    "越来越多了。";
        }
        if(sss.getMentalHealth()<=35){
            diagnose=diagnose+"身体是一切的基础，是革命的本钱，提醒你要早睡早起，作息规律，饮食健康，不要挑食，肉类与" +
                    "蔬菜搭配才能茁壮成长哦，保护好眼睛，不要过度用眼哦，遇到烦心事与困难要积极与老师，朋友或家长沟通交流，" +
                    "这样就能很快解决哦。";
        }
        else{
            diagnose=diagnose+"身体各方面都很健康，但是防微杜渐，对于健康一点也不能马虎，需要一直善待自己的身体，饮食均衡，" +
                    "作息规律，遇到困难积极与老师，家长和朋友沟通交流。";
        }
        if(sss.getSportsActivity()<15){
            diagnose=diagnose+"最近的体育锻炼活动参与次数有点少哦，需要积极参与学校的活动，与同学的玩耍，这样身体才能得到锻炼。";
        }
        else{
            diagnose=diagnose+"锻炼充足，需要继续保持！";
        }
        if(sss.getSportsSkills()<15){
            diagnose=diagnose+"基本运动技能与专业运动技能还需要加强，不过不要灰心，这些技能积极参与就能很好得到加强，尤其是" +
                    "各种球类运动哦";
        }
        else{
            diagnose=diagnose+"体能充沛，基本运动熟练，各种球类运动游刃有余，不要骄傲自大哦，在体育上有很高天赋的你希望能积极参与" +
                    "活动，等级测评。";

        }
        return diagnose;
    }
}
