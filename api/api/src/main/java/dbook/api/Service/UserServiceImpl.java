package dbook.api.Service;

import dbook.api.Domain.Token;
import dbook.api.Domain.User;
import dbook.api.Exception.UserException;
import dbook.api.Repository.LibraryRepository;
import dbook.api.Repository.TokenRepository;
import dbook.api.Repository.UserRepository;
import dbook.api.json.LoginResponse;
import dbook.api.json.Response;
import dbook.api.json.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    LibraryRepository libraryRepository;

//    @Override
//    public String sendEmail(String email) {
//        String user = "dgswbook@gmail.com"; // 보내는 email 주소
//        String password = "12dgswbook34"; // 보내는 email password
//        String serverSMTP = "smtp.gmail.com";
//        int serverPORT = 465;
//        StringBuilder code = new StringBuilder();
//
//        //SMTP 정보
//        Properties prop = new Properties();
//        prop.put("mail.smtp.host",serverSMTP);
//        prop.put("mail.smtp.port",serverPORT);
//        prop.put("mail.smtp.auth","true");
//        prop.put("mail.smtp.ssl.enable","true");
//        prop.put("mail.smtp.ssl.trust",serverSMTP);
//
//        Session session = Session.getDefaultInstance(prop, new Authenticator(){
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(user, password);
//            }
//        });
//
//        try{
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(user));
//
//            //받는사람 메일주소
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//
//            for(int i = 0 ; i < 6 ; i++)
//                code.append((int)((Math.random() * 10000) % 10));
//
//            // Subject
//            message.setSubject("[DBOOK] 인증번호 ["+ code +"] 를 입력해주세요."); //메일 제목을 입력
//
//            // Text
//            message.setText("인증번호 ["+ code +"] 를 입력해주세요.");    //메일 내용을 입력
//
//            // send the message
//            Transport.send(message); ////전송
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//        return code.toString();
//    }

    @Override
    public SignUpResponse signup(User user) {
        try {
            String userId = user.getUserId();
            User objUser = findUser(userId);

            if(!objUser.getUserId().equals("ok"))
                throw new UserException("User Already Exists");

            user.setPassword(convertSHA256(user.getPassword()));

            userRepository.save(user);
            return new SignUpResponse(user);
        } catch (UserException e) {
            e.printStackTrace();
            return new SignUpResponse(e.getMessage());
        }
    }

    @Override
    public LoginResponse login(User user) {
        try {
            String userId = user.getUserId();
            Optional<User> objUser = Optional.ofNullable(userRepository.findByUserId(userId).orElseThrow(
                    () -> new UserException("Undefined User")
            ));

            String password = convertSHA256(user.getPassword());
            if(!objUser.get().getPassword().equals(password))
                throw new UserException("Password Different");

            String token = makeToken();
            String ip = findIpAddress();

            Optional<Token> objToken = tokenRepository.findBytOwnerid(userId);

            if (objToken.orElse(null) == null)
                tokenRepository.save(new Token(userId, token, ip));
            else {
                objToken.map(found -> {
                    found.setTKey(Optional.ofNullable(token).orElse(found.getTKey()));
                    found.setTIp(Optional.ofNullable(ip).orElse(found.getTIp()));

                    return tokenRepository.save(found);
                });
            }

            return new LoginResponse(userId, token);
        } catch (UserException e) {
            e.printStackTrace();
            return new LoginResponse(e.getMessage());
        }
    }

    @Override
    public Response shared(User user) {
        String userId = user.getUserId();
        return new Response(libraryRepository.findByUserId(userId));
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