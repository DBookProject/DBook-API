package dgsw.dbook.api.Service;

import dgsw.dbook.api.Domain.Token;
import dgsw.dbook.api.Domain.User;
import dgsw.dbook.api.Exception.UserException;
import dgsw.dbook.api.Repository.TokenRepository;
import dgsw.dbook.api.Repository.UserRepository;
import dgsw.dbook.api.Response.LoginResponse;
import dgsw.dbook.api.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public LoginResponse signUp(User user) {
        try {
            String email = user.getEmail();
            String password = user.getPassword();
            String name = user.getName();

            if(email == null)
                throw new UserException(412, "Requires Email");
            if(password == null)
                throw new UserException(412, "Requires Password");
            if(name == null)
                throw new UserException(412, "Requires Name");

            //TODO: delete later(after client complete the process)
//            user.setPassword(this.convertSHA256(user.getPassword()));

            userRepository.save(user);

            LoginResponse response = this.login(user);
            if(response.getStatus() == 200)
                response.setMessage("Success SignUp/Login");

            return response;
        } catch (UserException e) {
            return new LoginResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse(500, e.getMessage());
        }
    }

    @Override
    public LoginResponse login(User user) {
        try {
            String email = user.getEmail();
            String password = user.getPassword();

            if(email == null)
                throw new UserException(412, "Requires Email");
            if(password == null)
                throw new UserException(412, "Requires Password");

            User findUser = this.findUser(email);

            if(findUser.getEmail().equals("Undefined Email"))
                throw new UserException(403, findUser.getEmail());
            if(!findUser.getPassword().equals(password))
                throw new UserException(403, "Password Different");

            String tok = this.generateToken();
            String ip = this.findIpAddress();

            Token token = tokenRepository.findByUserEmail(email).orElse(null);

            if(token != null)
                tokenRepository.delete(token);

            token = new Token();
            token.setUserEmail(email);
            token.setToken(tok);
            token.setIp(ip);
            tokenRepository.save(token);

            Map<String, Object> object = new HashMap<>();
            object.put("user", user);
            return new LoginResponse(200, "Success Login", tok, object);
        } catch (UserException e) {
            return new LoginResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse(500, e.getMessage());
        }
    }

    @Override
    public User findUser(String email) {
        return userRepository.findByEmail(email).orElse(new User("Undefined Email"));
    }

    public String generateToken() {
        StringBuilder token = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 16; i++)
            token.append((char) ((int) (random.nextInt(26)) + 97));

        return token.toString();
    }

    public String findIpAddress() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String convertSHA256(String string) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }

}