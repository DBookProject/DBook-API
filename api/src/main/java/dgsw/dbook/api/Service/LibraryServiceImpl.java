package dgsw.dbook.api.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dgsw.dbook.api.Domain.EBook;
import dgsw.dbook.api.Domain.Library;
import dgsw.dbook.api.Domain.LibraryPk;
import dgsw.dbook.api.Domain.Token;
import dgsw.dbook.api.Exception.UserException;
import dgsw.dbook.api.Repository.EBookRepository;
import dgsw.dbook.api.Repository.LibraryRepository;
import dgsw.dbook.api.Repository.TokenRepository;
import dgsw.dbook.api.Response.ObjectResponse;
import dgsw.dbook.api.Response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
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
                    () -> new UserException(401, "Undefined Token")
            );

            Long ebookId = eBook.getId();
            if(ebookId == null)
                throw new UserException(412, "Requires Id");

            eBookRepository.findById(ebookId).orElseThrow(
                    () -> new UserException(403, "Undefined Id")
            );

            Library library = new Library();
            library.setPk(new LibraryPk());
            library.getPk().setUserEmail(email);
            library.getPk().setEbookId(ebookId);

            libraryRepository.save(library);
            return new Response(200, "Success addBook");
        } catch (UserException e) {
            return new Response(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("addBook Error", e);
            return new Response(500, e.getMessage());
        }
    }

    @Override
    public ObjectResponse getLibrary(String token) {
        try {
            String email = tokenRepository.findByToken(token).map(Token::getUserEmail).orElseThrow(
                    () -> new UserException(401, "Undefined Token")
            );

            List<Library> list = libraryRepository.findByPk_UserEmail(email);
            List<EBook> bookList = new ArrayList<>();
            EBook eBook;
            for(Object library : list) {
                eBook = eBookRepository.findById(((Library) library).getPk().getEbookId()).orElse(null);

                if(eBook != null)
                    bookList.add(eBook);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("books", bookList);
            return new ObjectResponse(200, "Success GetLibrary", map);
        } catch (UserException e) {
            return new ObjectResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("getLibrary Error", e);
            return new ObjectResponse(500, e.getMessage());
        }
    }

    @Override
    public ObjectResponse getUploaded(String token) {
        try {
            Long uploader = tokenRepository.findByToken(token).map(Token::getId).orElseThrow(
                    () -> new UserException(401, "Undefined Token")
            );

            List<EBook> list = eBookRepository.findByUploader(uploader);

            Map<String, Object> map = new HashMap<>();
            map.put("books", list);
            return new ObjectResponse(200, "Success GetUploaded", map);
        } catch (UserException e) {
            return new ObjectResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("getUploaded Error", e);
            return new ObjectResponse(500, e.getMessage());
        }
    }

}
