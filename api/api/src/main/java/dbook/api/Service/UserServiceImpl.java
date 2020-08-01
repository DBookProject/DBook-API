package dbook.api.Service;

import dbook.api.Domain.Auth;
import dbook.api.Domain.Token;
import dbook.api.Domain.User;
import dbook.api.Exception.UserException;
import dbook.api.Repository.AuthRepository;
import dbook.api.Repository.TokenRepository;
import dbook.api.Repository.UserRepository;
import dbook.api.json.LoginResponse;
import dbook.api.json.Response;
import dbook.api.json.SignupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    AuthRepository authRepository;

    @Override
    public Response sendEmail(User user) {
        String email = user.getEmail();

        String sendEmail = "dgswbook@gmail.com"; // 보내는 email 주소
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
                return new PasswordAuthentication(sendEmail, password);
            }
        });

        try{
            Auth check = findAuth(email);
            if(!check.getAuth().equals("ok"))
                authRepository.delete(check);

            if(!findUser(email).getEmail().equals("ok"))
                throw new UserException("User Already Exists");

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendEmail));

            //받는사람 메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            check = null;
            do {
                for (int i = 0; i < 6; i++)
                    code.append((int) ((Math.random() * 10000) % 10));

                check = authRepository.findByaAuth(code.toString()).orElseGet(() -> new Auth("-1"));
            } while(!check.getAuth().equals("-1"));

            // Subject
            message.setSubject("[DBOOK] 인증번호"); //메일 제목을 입력

            // Text
            message.setText("인증번호 ["+ code +"] 를 입력해주세요.");    //메일 내용을 입력

            // send the message
            Transport.send(message); ////전송

            check.setEmail(email);
            check.setAuth(code.toString());
            authRepository.save(check);

            return new Response("Complete Sending Email");
        } catch (MessagingException e) {
            e.printStackTrace();
            return new Response(e.getMessage());
        }
    }

    @Override
    public SignupResponse signup(Auth auth) {
        try {
            String authCode = auth.getAuth();
            Auth data = Optional.ofNullable(authRepository.findByaAuth(authCode).orElseThrow(
                    () -> new UserException("Undefined Auth Code")
            )).get();

            User user = new User(data.getEmail());
            user.setPassword(convertSHA256(auth.getPassword()));

            userRepository.save(user);
            authRepository.delete(data);

            return new SignupResponse("Complete Signup");
        } catch (UserException e) {
            e.printStackTrace();
            return new SignupResponse(e.getMessage());
        }
    }

    @Override
    public LoginResponse login(User user) {
        try {
            String email = user.getEmail();
            Optional<User> objUser = Optional.ofNullable(userRepository.findByuEmail(email).orElseThrow(
                    () -> new UserException("Undefined User")
            ));

            String password = convertSHA256(user.getPassword());
            if(!objUser.get().getPassword().equals(password))
                throw new UserException("Password Different");

            String token = makeToken();
            String ip = findIpAddress();

            Optional<Token> objToken = tokenRepository.findBytOwnerid(email);

            if (objToken.orElse(null) == null)
                tokenRepository.save(new Token(email, token, ip));
            else {
                objToken.map(found -> {
                    found.setTKey(Optional.ofNullable(token).orElse(found.getTKey()));
                    found.setTIp(Optional.ofNullable(ip).orElse(found.getTIp()));

                    return tokenRepository.save(found);
                });
            }

            return new LoginResponse(email, token);
        } catch (UserException e) {
            e.printStackTrace();
            return new LoginResponse(e.getMessage());
        }
    }

    @Override
    public Response shared(User user) {
        return null;
    }

    @Override
    public User findUser(String email) {
        return userRepository.findByuEmail(email).orElseGet(() -> new User("ok"));
    }

    @Override
    public Auth findAuth(String email) { return authRepository.findByuEmail(email).orElseGet(() -> new Auth("ok")); }

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

    private String convertSHA256(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }


}