package com.project.Ai_Writer.EmailController;

//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;


public class EmailRequest {
    private String emailContent;

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    private String tone;


}
