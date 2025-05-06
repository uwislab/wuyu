package statistical.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
//import com.example.schoolstatistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import statistical.Service.StatisticsService;

@RestController
public class StatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // 获取男学生人数接口
    @GetMapping("/students/male-count")
    public ResponseEntity<Integer> getMaleStudentCount() {
        int count = statisticsService.getMaleStudentCount();
        return ResponseEntity.ok(count);
    }

    // 获取女学生人数接口
    @GetMapping("/students/female-count")
    public ResponseEntity<Integer> getFemaleStudentCount() {
        int count = statisticsService.getFemaleStudentCount();
        return ResponseEntity.ok(count);
    }

    // 获取男教师人数接口
    @GetMapping("/teachers/male-count")
    public ResponseEntity<Integer> getMaleTeacherCount() {
        int count = statisticsService.getMaleTeacherCount();
        return ResponseEntity.ok(count);
    }

    // 获取女教师人数接口
    @GetMapping("/teachers/female-count")
    public ResponseEntity<Integer> getFemaleTeacherCount() {
        int count = statisticsService.getFemaleTeacherCount();
        return ResponseEntity.ok(count);
    }

    // 获取教师职称分布接口
    @GetMapping("/teachers/title-distribution")
    public ResponseEntity<Map<String, Integer>> getTeacherTitleDistribution() {
        Map<String, Integer> distribution = statisticsService.getTeacherTitleDistribution();
        return ResponseEntity.ok(distribution);
    }
}