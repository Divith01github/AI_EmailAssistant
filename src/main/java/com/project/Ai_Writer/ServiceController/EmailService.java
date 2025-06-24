package com.project.Ai_Writer.ServiceController;

import com.project.Ai_Writer.EmailController.EmailRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class EmailService {

    private final WebClient webClient;
    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public EmailService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String getReply(EmailRequest email){
        String prompt= buildPrompt(email);
        //function that will return the prompt for the mail
        //next creating a request for the reply
        //create a key,value request
        Map<String,Object> requestBody=Map.of
                ("contents", new Object[]{
                        Map.of("parts",new Object[]{
                                Map.of("text",prompt)
                        })
                });
        //now make a request and get the response
        String response=webClient.post()
                .uri(geminiApiUrl+geminiApiKey)
                .header("Content-Type","application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return returnExtractedText(response);
    }

    private String returnExtractedText(String response) {
        try{
            ObjectMapper mapper=new ObjectMapper();
            JsonNode rootNode= mapper.readTree(response);
            return rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        }catch(Exception e){
                return "Error processing the request"+e.getMessage();
        }
    }

    private String buildPrompt(EmailRequest email) {
        StringBuilder prompt= new StringBuilder();
        prompt.append("Generate a professional reply for the following email content.Don't need a subject line");
        if(email.getTone() !=null && !email.getTone().isEmpty()){
            prompt.append("use a").append(email.getTone()).append("tone");
        }
        prompt.append("\n Original Content is:\n").append(email.getEmailContent());
        return prompt.toString();
    }
}
