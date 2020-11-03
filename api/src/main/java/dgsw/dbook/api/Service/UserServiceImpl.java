package dgsw.dbook.api.Service;

import dgsw.dbook.api.Domain.Token;
import dgsw.dbook.api.Domain.User;
import dgsw.dbook.api.Domain.UserData;
import dgsw.dbook.api.Domain.UserImageFile;
import dgsw.dbook.api.Exception.UserException;
import dgsw.dbook.api.Repository.TokenRepository;
import dgsw.dbook.api.Repository.UserImageFileRepository;
import dgsw.dbook.api.Repository.UserRepository;
import dgsw.dbook.api.Response.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserImageFileRepository imageFileRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public LoginResponse signUp(UserData userData) {
        MultipartFile profileImage = userData.getFile();

        try {
            String email = userData.getEmail();
            String password = userData.getPassword();
            String name = userData.getName();

            if(email == null)
                throw new UserException(412, "Requires Email");
            if(password == null)
                throw new UserException(412, "Requires Password");
            if(name == null)
                throw new UserException(412, "Requires Name");

            if(!this.findUser(email).getEmail().equals("Undefined Email"))
                throw new UserException(403, "Already Existing Email");

            User user = new User(email);

            if(profileImage != null) {
                String extendStr = StringUtils.cleanPath(Objects.requireNonNull(profileImage.getOriginalFilename()));
                if(extendStr.length() < 4)
                    throw new UserException(403, "Too Short Image Extend Name");

                extendStr = extendStr.substring(extendStr.length() - 4);
                if(!extendStr.equals(".jpg") && !extendStr.equals(".png"))
                    throw new UserException(403, "Unsupported Image Extend Name");

                UserImageFile imageFile = new UserImageFile(profileImage.getBytes());
                imageFile = imageFileRepository.save(imageFile);
                user.setProfileImage(imageFile.getUserImageId());
            }

            user.setName(name);
            user.setPassword(password);
            userRepository.save(user);

            LoginResponse response = this.login(user);
            if(response.getStatus() == 200)
                response.setMessage("Success SignUp/Login");
            return response;
        } catch (UserException e) {
            return new LoginResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("signUp Error", e);
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
                throw new UserException(401, "Password Different");

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
            Map<String, Object> userObject = new HashMap<>();
            userObject.put("userId", user.getUserId());
            userObject.put("email", user.getEmail());
            userObject.put("name", user.getName());
            userObject.put("password", user.getPassword());
            userObject.put("profileImage", "/user/image/" + user.getProfileImage());

            object.put("user", userObject);
            return new LoginResponse(200, "Success Login", tok, object);
        } catch (UserException e) {
            return new LoginResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("login Error", e);
            return new LoginResponse(500, e.getMessage());
        }
    }

    @Override
    public Resource getImage(long imageId) {
        try {
            Blob imageBlob = imageFileRepository.findById(imageId).map(UserImageFile::getUserProfileImage).orElseThrow(
                    () -> new UserException(403, "Undefined ImageId")
            );
            byte[] bytes = imageBlob.getBytes(1L, (int)imageBlob.length());

            return new ByteArrayResource(bytes);
        } catch (UserException e) {
            return null;
        } catch (Exception e) {
            log.error("getUserImage Error", e);
            return null;
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
            token.append((char) (random.nextInt(26) + 97));

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

}