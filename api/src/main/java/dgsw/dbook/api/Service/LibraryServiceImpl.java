package dgsw.dbook.api.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dgsw.dbook.api.Config;
import dgsw.dbook.api.Domain.*;
import dgsw.dbook.api.Exception.UserException;
import dgsw.dbook.api.Repository.CategoryRepository;
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

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Response addBook(String token, EBook eBook) {
        try {
            String email = tokenRepository.findByToken(token).map(Token::getUserEmail).orElseThrow(
                    () -> new UserException(401, "Undefined Token")
            );

            Long ebookId = eBook.getId();
            if(ebookId == null)
                throw new UserException(400, "Requires Id");

            eBookRepository.findById(ebookId).orElseThrow(
                    () -> new UserException(400, "Unauthorized Id")
            );

            Library library = new Library();
            library.setPk(new LibraryPk());
            library.getPk().setUserEmail(email);
            library.getPk().setEbookId(ebookId);

            libraryRepository.save(library);
            return new Response(200, "Success addBook");
        } catch (UserException e) {
            return new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("addBook Error", e);
            return new Response(500, e.getMessage());
        }
    }

    @Override
    public ObjectResponse getLibrary(String token) {
        try {
            String email = tokenRepository.findByToken(token).map(Token::getUserEmail).orElseThrow(
                    () -> new UserException(401, "Unauthorized Token")
            );

            List<Library> list = libraryRepository.findByPk_UserEmail(email);
            List<Map<String, Object>> bookList = new ArrayList<>();
            Map<String, Object> map;
            EBook eBook;
            for(Object library : list) {
                eBook = eBookRepository.findById(((Library) library).getPk().getEbookId()).orElse(null);

                if(eBook != null) {
                    map = new HashMap<>();
                    map.put("id", eBook.getId());
                    map.put("category", categoryRepository.findById(eBook.getCategoryId()).map(Category::getName).orElseThrow(
                            () -> new UserException(500, "EBook data error")
                    ));
                    map.put("title", eBook.getTitle());
                    map.put("author", eBook.getAuthor());
                    map.put("bookFile", "/ebook/file/" + eBook.getBookFile());
                    map.put("coverImage", "/ebook/image/" + eBook.getCoverImage());
                    map.put("description", eBook.getDescription());
                    map.put("uploader", eBook.getUploader());
                    map.put("publisher", eBook.getPublished());
                    map.put("published", Config.parseLocalDateTime(eBook.getPublished()));

                    bookList.add(map);
                }
            }

            map = new HashMap<>();
            map.put("books", bookList);
            return new ObjectResponse(200, "Success GetLibrary", map);
        } catch (UserException e) {
            return new ObjectResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("getLibrary Error", e);
            return new ObjectResponse(500, e.getMessage());
        }
    }

    @Override
    public ObjectResponse getUploaded(String token) {
        try {
            Long uploader = tokenRepository.findByToken(token).map(Token::getId).orElseThrow(
                    () -> new UserException(401, "Unauthorized Token")
            );

            List<EBook> list = eBookRepository.findByUploader(uploader);
            List<Map<String, Object>> bookList = new ArrayList<>();
            Map<String, Object> map;
            for(EBook eBook : list) {
                map = new HashMap<>();
                map.put("id", eBook.getId());
                map.put("category", categoryRepository.findById(eBook.getCategoryId()).map(Category::getName).orElseThrow(
                        () -> new UserException(500, "EBook data error")
                ));
                map.put("title", eBook.getTitle());
                map.put("author", eBook.getAuthor());
                map.put("bookFile", "/ebook/file/" + eBook.getBookFile());
                map.put("coverImage", "/ebook/image/" + eBook.getCoverImage());
                map.put("description", eBook.getDescription());
                map.put("uploader", eBook.getUploader());
                map.put("publisher", eBook.getPublished());
                map.put("published", Config.parseLocalDateTime(eBook.getPublished()));

                bookList.add(map);
            }

            map = new HashMap<>();
            map.put("books", bookList);
            return new ObjectResponse(200, "Success GetUploaded", map);
        } catch (UserException e) {
            return new ObjectResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("getUploaded Error", e);
            return new ObjectResponse(500, e.getMessage());
        }
    }

}
