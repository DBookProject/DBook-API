package kr.hs.dgsw.dbook.Service;

import kr.hs.dgsw.dbook.Domain.EmailClass;
import kr.hs.dgsw.dbook.Domain.User;
import kr.hs.dgsw.dbook.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User signUp(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User login(User user) {
        Optional<User> loginUser = this.userRepository.findByUserIdAndPassword(user.getUserId(), user.getPassword());
        return loginUser.orElseGet(null);
    }

    @Override
    public User userModified(User user) {

        return this.userRepository.findByUserId(user.getUserId())
                .map(found -> {
                    found.setName(Optional.ofNullable(user.getName()).orElse(found.getName()));
                    found.setPassword(Optional.ofNullable(user.getPassword()).orElse(found.getPassword()));

                    return this.userRepository.save(found);
                }).orElse(null);
    }

    @Override
    public User getUser(String userId) {
        return this.userRepository.findByUserId(userId).orElseGet(null);
    }

    @Override
    public User sendEmail(EmailClass emailInfo) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(prop, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailInfo.getSendEmail(), emailInfo.getSendEmailPassword());
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailInfo.getSendEmail()));

            //받는사람 메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailInfo.getReceiveEmail()));

            // Subject
            message.setSubject(emailInfo.getTitle()); //메일 제목을 입력

            // Text
            message.setText(emailInfo.getContent());    //메일 내용을 입력

            // send the message
            Transport.send(message); //전송
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }
}