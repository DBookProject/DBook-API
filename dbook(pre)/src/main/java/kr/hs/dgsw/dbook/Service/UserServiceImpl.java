package kr.hs.dgsw.dbook.Service;

import kr.hs.dgsw.dbook.Domain.Token;
import kr.hs.dgsw.dbook.Domain.User;
import kr.hs.dgsw.dbook.Exception.UserException;
import kr.hs.dgsw.dbook.Repository.TokenRepository;
import kr.hs.dgsw.dbook.Repository.UserRepository;
import kr.hs.dgsw.dbook.json.LoginResponse;
import kr.hs.dgsw.dbook.json.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public String sendEmail(String email) {
        String user = "dgswbook@gmail.com"; // 보내는 email 주소
        String password = "12dgswbook34"; // 보내는 email password
        String serverSMTP = "smtp.gmail.com";
        int serverPORT = 465;
        StringBuilder code = new StringBuilder();

        //SMTP 정보
        Properties prop = new Properties();
        prop.put("mail.smtp.host",serverSMTP);
        prop.put("mail.smtp.port",serverPORT);
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.trust",serverSMTP);

        Session session = Session.getDefaultInstance(prop, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));

            //받는사람 메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            for(int i = 0 ; i < 6 ; i++)
                code.append((int)((Math.random() * 10000) % 10));

            // Subject
            message.setSubject("[DBOOK] 인증번호 ["+ code +"] 를 입력해주세요."); //메일 제목을 입력

            // Text
            message.setText("인증번호 ["+ code +"] 를 입력해주세요.");    //메일 내용을 입력

            // send the message
            Transport.send(message); ////전송
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return code.toString();
    }

    @Override
    public SignUpResponse signup(User user) {
        return null;
    }

    @Override
    public LoginResponse login(User user) {
        try {
            String userid = user.getUserId();
            Optional<User> objUser = Optional.ofNullable(userRepository.findByUserId(userid).orElseThrow(
                    () -> new UserException("존재하지 않는 회원입니다.")
            ));
            String password = objUser.get().getPassword();
            String token = makeToken();
            String ip = findIpAddress();

            Optional<Token> objToken = tokenRepository.findBytOwnerid(userid);

            if (objToken.orElse(null) == null)
                tokenRepository.save(new Token(userid, token, ip));
            else {
                objToken.map(found -> {
                    found.setTKey(Optional.ofNullable(token).orElse(found.getTKey()));
                    found.setTIp(Optional.ofNullable(ip).orElse(found.getTIp()));

                    return tokenRepository.save(found);
                });
            }

            return new LoginResponse(userid, token);
        } catch (UserException e) {
            e.printStackTrace();
            return new LoginResponse(e.getMessage());
        }
    }

    @Override
    public User findUser(String userid) {
        return userRepository.findByUserId(userid).orElseGet(() -> new User("ok"));
    }

    public String makeToken(){
        StringBuilder token = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 16; i++) {
            token.append((char) ((int) (random.nextInt(26)) + 97));
        }

        return token.toString();
    }

    public String findIpAddress() {
        String ipaddr = null;

        try {
            ipaddr = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return ipaddr;
    }

}