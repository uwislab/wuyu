package com.fiveup.core.management.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.fiveup.core.management.mapper.ClazzMapper;
import com.fiveup.core.management.mapper.SchoolMapper;
import com.fiveup.core.management.mapper.StuMapper;
import com.fiveup.core.management.mapper.TeaMapper;
import com.fiveup.core.management.model.DTO.ClassDTO;
import com.fiveup.core.management.model.DTO.StuDTO;
import com.fiveup.core.management.model.DTO.TeaDTO;
import com.fiveup.core.management.model.School;
import com.fiveup.core.management.model.topology.TopologyNode;
import com.fiveup.core.management.service.TopologyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 尔宣赫
 * @date 2022/4/25
 */
@Slf4j
@Service
public class TopologyServiceImpl implements TopologyService {

    @Resource
    private SchoolMapper schoolMapper;

    @Resource
    private ClazzMapper clazzMapper;

    @Resource
    private StuMapper stuMapper;

    @Resource
    private TeaMapper teaMapper;


    /**
     * For-Each循环+数组实现DFS深度优先搜索学生
     * @param schoolId 学校id
     * @return
     */
    @Override
    public TopologyNode getTopology(Long schoolId) {
        TopologyNode topologyNode = new TopologyNode();
        School school = schoolMapper.getSchoolById(schoolId);
        if (school != null && (!StringUtils.isEmpty(school.getSchoolName()))) {
            topologyNode.setName(school.getSchoolName());
            List<String> gradeList = clazzMapper.getGradesBySchoolId(schoolId);
            if (gradeList != null && gradeList.size() != 0) {
                topologyNode.setChildren(new ArrayList<TopologyNode>());
                List<TopologyNode> TwoDepthNodeList = topologyNode.getChildren();
                for (String grade : gradeList) {
                    TopologyNode tmpTopologyNode = new TopologyNode();
                    tmpTopologyNode.setName(grade);
                    TwoDepthNodeList.add(tmpTopologyNode);
                    List<ClassDTO> classDTOList = clazzMapper.getClassListByGradeAndSchoolId(grade, schoolId);
                    if (classDTOList != null && classDTOList.size() != 0) {
                        tmpTopologyNode.setChildren(new ArrayList<TopologyNode>());
                        List<TopologyNode> threeDepthNodeList = tmpTopologyNode.getChildren();
                        for (ClassDTO classDTO : classDTOList) {
                            TopologyNode tmp2TopologyNode = new TopologyNode();
                            Long classId = classDTO.getId();
                            tmp2TopologyNode.setName(classDTO.getClassName());
                            threeDepthNodeList.add(tmp2TopologyNode);
                            List<StuDTO> stuDTOList = stuMapper.getStudentListByCondition(null, null, null, classId, schoolId);
                            if(stuDTOList != null && stuDTOList.size() != 0) {
                                tmp2TopologyNode.setChildren(new ArrayList<TopologyNode>());
                                List<TopologyNode> fourDepthNodeList = tmp2TopologyNode.getChildren();

                                TeaDTO teaDTO = teaMapper.getTeacherById(classDTO.getMonitorId());
                                if(teaDTO != null) {
                                    TopologyNode tmp3TopologyNode = new TopologyNode();
                                    tmp3TopologyNode.setName("班主任："+teaDTO.getTeacherName());
                                    fourDepthNodeList.add(tmp3TopologyNode);
                                }

                                for(StuDTO stuDTO : stuDTOList) {
                                    TopologyNode tmp3TopologyNode = new TopologyNode();
//                                    Long stuId = stuDTO.getStudentId();
                                    tmp3TopologyNode.setName(stuDTO.getStudentName());
                                    fourDepthNodeList.add(tmp3TopologyNode);
                                }
                            }

                        }
                    }
                }
            }
        }
        return topologyNode;

    }
}
