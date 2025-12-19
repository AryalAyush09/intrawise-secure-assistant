//package com.intrawise.controller;
//
//import org.springframework.ai.chat.model.ChatModel;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/test")
//public class TestLLMController {
//
//    private final ChatModel chatModel;
//
//    public TestLLMController(ChatModel chatModel) {
//        this.chatModel = chatModel;
//    }
//
//    @GetMapping("/llm")
//    public String test() {
//        return chatModel.call("Say hello in one sentence.");
//    }
//}
