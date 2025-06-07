package com.fiveup.core.notice.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {



    // 注意参数中的model就是使用的模型，这里用了Ollama，也可以选择OpenAIChatModel
//    @Bean
//    public ChatClient chatClient(OllamaChatModel model) {
//        return ChatClient.builder(model) // 创建ChatClient工厂
//                .build(); // 构建ChatClient实例
//
//    }

}
