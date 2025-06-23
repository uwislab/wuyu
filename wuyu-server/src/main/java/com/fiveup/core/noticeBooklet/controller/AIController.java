package com.fiveup.core.noticeBooklet.controller;

import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.fiveup.core.noticeBooklet.utils.AIUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class AIController {

    /**
     * 测试 AI 评语生成接口
     * @param request 请求参数，包含学生姓名和五育成绩
     * @return 包含评语的响应结果
     */
    @PostMapping("/generate-comment")
    public Map<String, Object> generateComment(@RequestBody CommentRequest request) {
        Map<String, Object> result = new HashMap<>();

        try {
            // 验证输入参数
            if (request.getStudentName() == null || request.getStudentName().isEmpty()) {
                result.put("success", false);
                result.put("message", "学生姓名不能为空");
                return result;
            }

            if (request.getScores() == null || request.getScores().length != 5) {
                result.put("success", false);
                result.put("message", "五育成绩必须包含5个分数");
                return result;
            }

            // 调用工具类生成评语
            String comment = AIUtil.generateComment(
                    request.getStudentName(),
                    request.getScores()[0],
                    request.getScores()[1],
                    request.getScores()[2],
                    request.getScores()[3],
                    request.getScores()[4]
            );

            // 返回成功结果
            result.put("success", true);
            result.put("message", "评语生成成功");
            result.put("comment", comment);

        } catch (ApiException e) {
            result.put("success", false);
            result.put("message", "API调用异常: " + e.getMessage());
        } catch (NoApiKeyException e) {
            result.put("success", false);
            result.put("message", "缺少API Key: " + e.getMessage());
        } catch (InputRequiredException e) {
            result.put("success", false);
            result.put("message", "输入参数缺失: " + e.getMessage());
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "系统异常: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 评语生成请求参数类
     */
    static class CommentRequest {
        private String studentName;
        private double[] scores;

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public double[] getScores() {
            return scores;
        }

        public void setScores(double[] scores) {
            this.scores = scores;
        }
    }
}
