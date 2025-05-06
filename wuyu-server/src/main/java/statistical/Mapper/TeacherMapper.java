package statistical.Mapper;

//import com.example.schoolstatistics.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import statistical.Model.Teacher;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherMapper {
    // 查询所有教师信息（可根据实际需求添加更多查询方法等）
    List<Teacher> selectAllTeachers();

    // 根据性别统计教师人数
    int countTeachersByGender(@Param("gender") Integer gender);

    // 按照教师职称分组统计人数
    Map<String, Integer> countTeachersByTitle();
}