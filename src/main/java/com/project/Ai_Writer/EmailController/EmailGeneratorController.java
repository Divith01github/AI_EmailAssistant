package com.project.Ai_Writer.EmailController;

import com.project.Ai_Writer.ServiceController.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin("*")
//@AllArgsConstructor
public class EmailGeneratorController {

    private final EmailService eamilService;

    public EmailGeneratorController(EmailService eamilService) {
        this.eamilService = eamilService;
    }

//    public EmailGeneratorController(EmailService eamilService) {
//        this.eamilService = eamilService;
//    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateAiReply(@RequestBody EmailRequest email){
        String response=eamilService.getReply(email);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
