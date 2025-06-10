package com.fiveup.core.noticeBooklet.utils;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;

import java.util.Arrays;

public  class AIUtil {

    public static String generateComment(String studentName, double... scores)
            throws ApiException, NoApiKeyException, InputRequiredException {
        // 构建系统消息（设置助手角色）
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("你是一位经验丰富的班主任，擅长根据学生的五育成绩生成个性化评语。")
                .build();

        // 构建用户消息（包含五育成绩）
        String prompt = String.format(
                "请根据学生%s的五育成绩生成评语、建议、假期要求：\n" +
                        "- 德育：%.1f分（品德修养、责任感）\n" +
                        "- 智育：%.1f分（学习能力、思维创新）\n" +
                        "- 体育：%.1f分（运动习惯、体育精神）\n" +
                        "- 美育：%.1f分（审美能力、艺术创造力）\n" +
                        "- 劳育：%.1f分（劳动态度、实践能力）\n" +
                        "要求：结合成绩特点，语言亲切，含鼓励与期望，200字左右。",
                studentName, scores[0], scores[1], scores[2], scores[3], scores[4]);

        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(prompt)
                .build();

        // 配置请求参数（从环境变量获取API Key）
        GenerationParam param = GenerationParam.builder()
                .apiKey("sk-55857d789fc247778029c76f2227a4d0")
                .model("qwen-plus") // 使用您验证过的模型名称
                .messages(Arrays.asList(systemMsg, userMsg)) // 添加系统消息
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();

        Generation generation = new Generation();
        GenerationResult result = generation.call(param);
        return result.getOutput().getChoices().get(0).getMessage().getContent();
    }
}