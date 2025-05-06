package statistical.Mapper;

//import com.example.schoolstatistics.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import statistical.Model.Student;

import java.util.List;

@Mapper
public interface StudentMapper {
    // 查询所有学生信息（可根据实际需求添加更多查询方法等）
    List<Student> selectAllStudents();

    // 根据性别统计学生人数
    int countStudentsByGender(@Param("gender") Integer gender);
}