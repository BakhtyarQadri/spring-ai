package com.example.ollama;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class OllamaApplication {

    private final ChatModel chatModel;
    private final ChatClient chatClient;

    public OllamaApplication(OllamaChatModel ollamaChatModel) {
        this.chatModel = ollamaChatModel;
        this.chatClient = ChatClient.create(ollamaChatModel);
    }

    public static void main(String[] args) {
        SpringApplication.run(OllamaApplication.class, args);
    }

    @GetMapping("/ollama/chat-model/{prompt}")
    public String getAnswerViaChatModel(@PathVariable String prompt) {
        String response = chatModel.call(prompt);
        System.out.println(response);
        return response;
    }

    @GetMapping("/ollama/chat-client/{prompt}")
    public String getAnswerViaChatClient(@PathVariable String prompt) {
        try {
            String response = chatClient.prompt(prompt).call().content();
//            ChatResponse chatResponse = chatClient.prompt(question).call().chatResponse();
//            System.out.println(chatResponse.getMetadata().getModel());
//            response = chatResponse.getResult().getOutput().getText();
            System.out.println(response);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
