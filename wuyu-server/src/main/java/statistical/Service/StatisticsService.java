package statistical.Service;

import java.util.Map;

public interface StatisticsService {
    int getMaleStudentCount();
    int getFemaleStudentCount();
    int getMaleTeacherCount();
    int getFemaleTeacherCount();
    Map<String, Integer> getTeacherTitleDistribution();
}