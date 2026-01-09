package com.yangyang.whatcanieat.controller;

//import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {
//
//    private final ChatClient chatClient;
//
//    public AiController(ChatClient.Builder builder) {
//        this.chatClient = builder.build();
//    }
//
//    @GetMapping("/chat")
//    public String chat(@RequestParam String prompt) {
//        return chatClient.prompt()
//                .user(prompt)
//                .call()
//                .content();
//    }
//
//    @GetMapping("/chat2")
//    public String chat2(@RequestParam String prompt) {
//        return chatClient.prompt()
//                .system("你是一个资深 Java 后端架构师")
//                .user(prompt)
//                .call()
//                .content();
//    }

//    @GetMapping("/chat3")
//    public ChatResponse chat3(@RequestParam String prompt) {
//        return chatClient.prompt()
//                .user(prompt)
//                .call();
//    }
}
