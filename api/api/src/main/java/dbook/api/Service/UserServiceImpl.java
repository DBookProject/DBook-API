package dbook.api.Service;

import dbook.api.Domain.Token;
import dbook.api.Domain.User;
import dbook.api.Exception.UserException;
import dbook.api.Repository.TokenRepository;
import dbook.api.Repository.UserRepository;
import dbook.api.json.LoginResponse;
import dbook.api.json.Response;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public Response sendEmail(User user) {
        String sendEmail = "dgswbook@gmail.com"; // 보내는 email 주소
        String password = "12qwerdbookqwer34!"; // 보내는 email password
        String serverSMTP = "smtp.gmail.com";
        int serverPORT = 465;

        //SMTP 정보
        Properties prop = new Properties();
        prop.put("mail.smtp.host", serverSMTP);
        prop.put("mail.smtp.port", serverPORT);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", serverSMTP);

        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sendEmail, password);
            }
        });

        try {
            String email = user.getEmail();
            String userPassword = user.getPassword();

            if (email == null)
                throw new UserException("Requires Email");
            if(userPassword == null)
                throw new UserException("Requires Password");
            if (!findUser(email).getEmail().equals("Undefined"))
                throw new UserException("User Already Exists");

            Pattern pattern = Pattern.compile("(^(?=.*[0-9])(?=.*[a-zA-Z]).*$)");
            Matcher matcher = pattern.matcher(userPassword);

            if(userPassword.length() < 8)
                return new Response(401, "Password Must Be Longer Than 8");
            if(!matcher.find())
                return new Response(401, "Password Must Contain Number And English");

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendEmail));

            //받는사람 메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // Subject
            message.setSubject("[DBOOK] 인증"); //메일 제목을 입력

            // Text
            StringBuilder code = new StringBuilder();
            for(int i = 0; i < 4; i++)
                code.append((int)(Math.random() * 10));
            user.setCertifyCode(Integer.parseInt(code.toString()));
            user.setIsCertified(false);

            StringBuilder link = new StringBuilder("http://10.80.162.210:8080/users/certify?code=");
            link.append(email);
            link.append(user.getCertifyCode());
            message.setText("이메일 인증을 완료해주세요!\n" + link);    //메일 내용을 입력

            // send the message
            Transport.send(message); ////전송

            user.setPassword(convertSHA256(user.getPassword()));
            userRepository.save(user);

            return new Response(200, "Success sendEmail");
        } catch (MessagingException e) {
            e.printStackTrace();
            return new Response(400, e.getMessage());
        }
    }

    @Override
    public LoginResponse login(User user) {
        try {
            String email = user.getEmail();
            if(email == null)
                return new LoginResponse(400, "Requires Email");

            User objUser = findUser(email);;

            if(objUser.getEmail().equals("Undefined"))
                return new LoginResponse(400, "Undefined User");
            if(user.getPassword() == null)
                return new LoginResponse(400, "Requires Password");
            if(!objUser.getIsCertified())
                return new LoginResponse(400, "UnCertified Email");

            String password = objUser.getPassword();
            if(!user.getPassword().equals(password))
                throw new UserException("Different Password");

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

            return new LoginResponse(200, "Success logins", token, objUser);
        } catch (UserException e) {
            e.printStackTrace();
            return new LoginResponse(401, e.getMessage());
        }
    }

    @Override
    public User findUser(String email) {
        return userRepository.findByuEmail(email).orElseGet(() -> new User("Undefined"));
    }

    @Override
    public Response certify(String code) {
        String email = code.substring(0, code.length() - 4);
        String certifyCode = code.substring(code.length() - 4, code.length());

        User user = findUser(email);

        if(user.getEmail().equals("Undefined"))
            return new Response(400, "Undefined User");
        if(user.getIsCertified())
            return new Response(401, "Already Certified");
        if(!user.getCertifyCode().toString().equals(certifyCode))
            return new Response(400, "Unknown Certify Code");

        try {
            userRepository.delete(user);

            user.setIsCertified(true);
            user.setCertifyCode(null);
            userRepository.save(user);
        } catch(Exception e) {
            return new Response(400, e.getMessage());
        }

        return new Response(200, "Certified Email");
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