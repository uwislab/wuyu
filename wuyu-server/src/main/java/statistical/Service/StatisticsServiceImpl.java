package statistical.Service;

import org.ehcache.core.spi.service.StatisticsService;
import org.springframework.stereotype.Service;
import java.util.Map;
//import com.example.schoolstatistics.mapper.StudentMapper;
//import com.example.schoolstatistics.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import statistical.Mapper.StudentMapper;
import statistical.Mapper.TeacherMapper;

@Service
public abstract class StatisticsServiceImpl implements StatisticsService {

    private final StudentMapper studentMapper;
    private final TeacherMapper teacherMapper;

    @Autowired
    public StatisticsServiceImpl(StudentMapper studentMapper, TeacherMapper teacherMapper) {
        this.studentMapper = studentMapper;
        this.teacherMapper = teacherMapper;
    }

    //@Override
    public int getMaleStudentCount() {
        return studentMapper.countStudentsByGender(1);
    }

    //@Override
    public int getFemaleStudentCount() {
        return studentMapper.countStudentsByGender(0);
    }

    //@Override
    public int getMaleTeacherCount() {
        return teacherMapper.countTeachersByGender(1);
    }

    //@Override
    public int getFemaleTeacherCount() {
        return teacherMapper.countTeachersByGender(0);
    }

    //@Override
    public Map<String, Integer> getTeacherTitleDistribution() {
        return teacherMapper.countTeachersByTitle();
    }
}