package com.yangyang.whatcanieat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("你是一个菜谱分析助手,你叫米粒。你会为用户提供专业的菜谱情况分析。你每次回复最后都要加上‘米粒米粒喵~’")
                .build();
    }
}
