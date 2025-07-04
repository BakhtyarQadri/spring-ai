package com.example.ollama;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class OllamaApplication {

    private final OllamaChatModel chatModel;
    private final ChatClient chatClient;

    public OllamaApplication(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
        chatClient = ChatClient.create(chatModel);
    }

    public static void main(String[] args) {
        SpringApplication.run(OllamaApplication.class, args);
    }

    @GetMapping("/ollama/chat-model/{question}")
    public String getAnswerViaChatModel(@PathVariable String question) {
        try {
            String response = chatModel.call(question);
            System.out.println(response);
            return response;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/ollama/chat-client/{question}")
    public String getAnswerViaChatClient(@PathVariable String question) {
        try {
            String response = chatClient.prompt(question).call().content();
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
