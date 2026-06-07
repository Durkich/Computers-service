package com.Kalabekov.Computersservice.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String question = request.get("message");

        System.out.println("========================================");
        System.out.println("CHAT REQUEST: " + question);

        SearchRequest searchRequest = SearchRequest.builder()
                .query(question)
                .topK(10)
                .build();

        List<Document> relevantDocs = vectorStore.similaritySearch(searchRequest);

        System.out.println("Found " + relevantDocs.size() + " relevant documents:");
        for (Document doc : relevantDocs) {
            System.out.println("  - " + doc.getText());
        }

        if (relevantDocs.isEmpty()) {
            return ResponseEntity.ok(Map.of("reply",
                    "Не могу найти информацию по запросу \"" + question + "\". Попробуйте перефразировать."));
        }

        String context = relevantDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        String prompt = String.format("""
            Ты — ИТ-ассистент. Отвечай ТОЛЬКО на основе информации ниже.
            Если в информации нет ответа — скажи "Не знаю".
            Если информация есть — дай чёткий, краткий ответ на русском языке.
            Не придумывай ничего от себя.
            
            Информация:
            %s
            
            Вопрос пользователя: %s
            
            Ответ:
            """, context, question);

        System.out.println("Prompt length: " + prompt.length());

        ChatClient chatClient = chatClientBuilder.build();

        String answer = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        System.out.println("ANSWER: " + answer);
        System.out.println("========================================");

        return ResponseEntity.ok(Map.of("reply", answer));
    }
}