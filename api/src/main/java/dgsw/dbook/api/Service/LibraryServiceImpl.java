package dgsw.dbook.api.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dgsw.dbook.api.Domain.EBook;
import dgsw.dbook.api.Domain.Library;
import dgsw.dbook.api.Domain.Token;
import dgsw.dbook.api.Exception.UserException;
import dgsw.dbook.api.Repository.EBookRepository;
import dgsw.dbook.api.Repository.LibraryRepository;
import dgsw.dbook.api.Repository.TokenRepository;
import dgsw.dbook.api.Response.ObjectResponse;
import dgsw.dbook.api.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    EBookRepository eBookRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Override
    public Response addBook(String token, EBook eBook) {
        try {
            String email = tokenRepository.findByToken(token).map(Token::getUserEmail).orElseThrow(
                    () -> new UserException(403, "Undefined Token")
            );

            Long ebookId = eBook.getId();
            if(ebookId == null)
                throw new UserException(412, "Requires Id");

            eBookRepository.findById(ebookId).orElseThrow(
                    () -> new UserException(403, "Undefined Id")
            );

            Library library = new Library();
            library.getPk().setUserEmail(email);
            library.getPk().setEbookId(ebookId);

            libraryRepository.save(library);
            return new Response(200, "Success addBook");
        } catch (UserException e) {
            return new Response(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(500, e.getMessage());
        }
    }

    @Override
    public ObjectResponse getLibrary(String token) {
        try {
            String email = tokenRepository.findByToken(token).map(Token::getUserEmail).orElseThrow(
                    () -> new UserException(403, "Undefined Token")
            );

            List<Library> list = libraryRepository.findByPk_UserEmail(email);
            List<EBook> bookList = new ArrayList<>();
            for(Object library : list)
                eBookRepository.findById(((Library) library).getPk().getEbookId());

            Map<String, Object> map = new HashMap<>();
            map.put("books", bookList);
            return new ObjectResponse(200, "Success GetLibrary", map);
        } catch (UserException e) {
            return new ObjectResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ObjectResponse(500, e.getMessage());
        }
    }

    @Override
    public ObjectResponse getUploaded(String token) {
        try {
            String email = tokenRepository.findByToken(token).map(Token::getUserEmail).orElseThrow(
                    () -> new UserException(403, "Undefined Token")
            );

            List<EBook> list = eBookRepository.findByUploader(email);

            Map<String, Object> map = new HashMap<>();
            map.put("books", list);
            return new ObjectResponse(200, "Success GetUploaded", map);
        } catch (UserException e) {
            return new ObjectResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ObjectResponse(500, e.getMessage());
        }
    }

}
