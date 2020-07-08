package kr.hs.dgsw.dbook.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailClass {

    private String receiveEmail;

    private String title;

    private String content;

    private String sendEmail;

    private String sendEmailPassword;

    public String getReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(String sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getSendEmailPassword() {
        return sendEmailPassword;
    }

    public void setSendEmailPassword(String sendEmailPassword) {
        this.sendEmailPassword = sendEmailPassword;
    }
}
