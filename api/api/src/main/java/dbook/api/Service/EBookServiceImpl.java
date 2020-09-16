package dbook.api.Service;

import dbook.api.Domain.EBook;
import dbook.api.Domain.EBookOutput;
import dbook.api.Domain.Token;
import dbook.api.Domain.User;
import dbook.api.Exception.UserException;
import dbook.api.Repository.EBookRepository;
import dbook.api.Repository.TokenRepository;
import dbook.api.Repository.UserRepository;
import dbook.api.json.LibraryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EBookServiceImpl implements EBookService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    EBookRepository eBookRepository;

    @Override
    public EBook findEBook(Integer eNo) {
        return eBookRepository.findByeNo(eNo).orElseGet(() -> new EBook("Undefined"));
    }

    @Override
    public LibraryResponse getShared(String key, String category) {
        try {
            Token token = tokenRepository.findBytKey(key).orElseThrow(
                    () -> new UserException("Undefined Token Key")
            );

            User user = userRepository.findByuEmail(token.getTOwnerid()).orElseThrow(
                    () -> new UserException("Unreachable Code")
            );

            List<EBookOutput> books = new ArrayList<>();
            for (EBook ebook : eBookRepository.findByeCategory(category)) {
                if (ebook.getIsSharable() && ebook.getUploader() != user.getId())
                    books.add(new EBookOutput(ebook));
            }

            return new LibraryResponse(200, "Success getShared", books);
        } catch (UserException e) {
            return new LibraryResponse(400, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new LibraryResponse(500, e.getMessage());
        }
    }

    //공유된 책인지, 업로더가 내가 아닌지, 카테고리가 일치하는지

}
