package dgsw.dbook.api.Service;

import java.util.*;

import dgsw.dbook.api.Domain.*;
import dgsw.dbook.api.Exception.UserException;
import dgsw.dbook.api.Repository.*;
import dgsw.dbook.api.Response.ListResponse;
import dgsw.dbook.api.Response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;

@Slf4j
@Service
public class EBookServiceImpl implements EBookService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EBookRepository eBookRepository;

    @Autowired
    EBookImageFileRepository imageFileRepository;

    @Autowired
    EBookFileRepository fileRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public ListResponse getList() {
        try {
            List<EBook> list = eBookRepository.findAll();

            if(list.size() == 0)
                throw new UserException(204, "No Content");

            else {
                ArrayList<Object> topList = new ArrayList<>();
                Map<String, Object> topMap;
                ArrayList<Map<String, Object>> dataArray;
                Map<String, Object> dataMap;

                for(Category category : categoryRepository.findAll()) {
                    topMap = new HashMap<>();
                    dataArray = new ArrayList<>();

                    for(EBook eBook : eBookRepository.findByCategory(category.getId())) {
                        dataMap = new HashMap<>();

                        dataMap.put("id", eBook.getId());
                        dataMap.put("category", eBook.getCategory());
                        dataMap.put("title", eBook.getTitle());
                        dataMap.put("author", eBook.getAuthor());
                        dataMap.put("book_file", eBook.getBookFile());
                        dataMap.put("cover_image", eBook.getCoverImage());
                        dataMap.put("description", eBook.getDescription());
                        dataMap.put("uploader_id", eBook.getUploader());
                        dataMap.put("publisher", eBook.getPublisher());

                        dataArray.add(dataMap);
                    }

                    topMap.put("categoryName", category.getName());
                    topMap.put("categoryId", category.getId());
                    topMap.put("data", dataArray);
                    topList.add(topMap);
                }

                return new ListResponse(200, "Success GetList", topList);
            }
        } catch (UserException e) {
            return new ListResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("getList Error", e);
            return new ListResponse(500, e.getMessage());
        }
    }

    @Override
    public Response uploadBook(String token, EBookData bookData) {
        try {
            Long uploader = tokenRepository.findByToken(token).map(Token::getId).orElseThrow(
                    () -> new UserException(401, "Unauthorized Token")
            );

            String title = bookData.getTitle();
            String author = bookData.getAuthor();
            String category = bookData.getCategory();

            MultipartFile coverImage = bookData.getCoverImage();
            String extendStr = StringUtils.cleanPath(Objects.requireNonNull(coverImage.getOriginalFilename()));
            if(extendStr.length() < 4)
                throw new UserException(403, "Too Short Image Extend Name");

            extendStr = extendStr.substring(extendStr.length() - 4);
            if(!extendStr.equals(".jpg") && !extendStr.equals(".png"))
                throw new UserException(403, "Unsupported Image Extend Name");

            MultipartFile bookFile = bookData.getBookFile();
            extendStr = StringUtils.cleanPath(Objects.requireNonNull(bookFile.getOriginalFilename()));
            if(extendStr.length() < 5)
                throw new UserException(403, "Too Short File Extend Name");

            extendStr = extendStr.substring(extendStr.length() - 5);
            if(!extendStr.equals(".epub"))
                throw new UserException(403, "Unsupported File Extend Name");

            String description = bookData.getDescription();
            String publisher = bookData.getPublisher();

            if(title == null)
                throw new UserException(412, "Requires Title");
            if(author == null)
                throw new UserException(412, "Requires Author");
            if(category == null)
                throw new UserException(412, "Requires Category");
            if(publisher == null)
                throw new UserException(412, "Requires Publisher");

            EBook eBook = new EBook();
            eBook.setTitle(title);
            eBook.setAuthor(author);
            eBook.setCategory(category);

            EBookImageFile eBookImageFile = new EBookImageFile(coverImage.getBytes());
            eBookImageFile = imageFileRepository.save(eBookImageFile);
            eBook.setCoverImage(eBookImageFile.getEBookImageId());

            EBookFile eBookFile = new EBookFile(bookFile.getBytes());
            eBookFile = fileRepository.save(eBookFile);
            eBook.setBookFile(eBookFile.getEBookFileId());

            eBook.setDescription(description);
            eBook.setPublisher(publisher);
            eBook.setUploader(uploader);

            if(!categoryRepository.findByName(category).isPresent())
                categoryRepository.save(new Category(category));

            eBookRepository.save(eBook);
            return new Response(200, "Success UploadBook");
        } catch (UserException e) {
            return new Response(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("uploadBook Error", e);
            return new Response(500, e.getMessage());
        }
    }

    @Override
    public Response editBook(String token, long bookId, EBookData bookData) {
        try {
            Long uploader = tokenRepository.findByToken(token).map(Token::getId).orElseThrow(
                    () -> new UserException(401, "Unauthorized Token")
            );

            EBook eBook = eBookRepository.findById(bookId).orElseThrow(
                    () -> new UserException(403, "Undefined EBookId")
            );

            if(!uploader.equals(eBook.getUploader()))
                throw new UserException(401, "Uploader Different");

            eBookRepository.delete(eBook);

            String title = bookData.getTitle();
            String author = bookData.getAuthor();
            String category = bookData.getCategory();
            String description = bookData.getDescription();
            String publisher = bookData.getPublisher();

            if(title == null)
                throw new UserException(412, "Requires Title");
            if(author == null)
                throw new UserException(412, "Requires Author");
            if(category == null)
                throw new UserException(412, "Requires Category");
            if(description == null)
                throw new UserException(412, "Requires Description");
            if(publisher == null)
                throw new UserException(412, "Requires Publisher");

            MultipartFile coverImage = bookData.getCoverImage();
            String extendStr;

            if(coverImage != null) {
                extendStr = StringUtils.cleanPath(Objects.requireNonNull(coverImage.getOriginalFilename()));
                if (extendStr.length() < 4)
                    throw new UserException(403, "Too Short Image Extend Name");

                extendStr = extendStr.substring(extendStr.length() - 4);
                if (!extendStr.equals(".jpg") && !extendStr.equals(".png"))
                    throw new UserException(403, "Unsupported Image Extend Name");

                imageFileRepository.editFile(eBook.getCoverImage(), new SerialBlob(bookData.getCoverImage().getBytes()));
            }

            MultipartFile bookFile = bookData.getBookFile();
            if(bookFile != null) {
                extendStr = StringUtils.cleanPath(Objects.requireNonNull(bookFile.getOriginalFilename()));
                if (extendStr.length() < 4)
                    throw new UserException(403, "Too Short File Extend Name");

                extendStr = extendStr.substring(extendStr.length() - 5);
                if (!extendStr.equals(".epub"))
                    throw new UserException(403, "Unsupported File Extend Name");

                fileRepository.editFile(eBook.getBookFile(), new SerialBlob(bookData.getBookFile().getBytes()));
            }

            eBookRepository.editBook(bookId, title, author, category, description, publisher);
            return new Response(200, "Success EditBook");
        } catch (UserException e) {
            return new Response(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("editBook Error", e);
            return new Response(500, e.getMessage());
        }
    }

    @Override
    public Response deleteBook(String token, long bookId) {
        try {
            Long uploader = tokenRepository.findByToken(token).map(Token::getId).orElseThrow(
                    () -> new UserException(401, "Unauthorized Token")
            );

            EBook eBook = eBookRepository.findById(bookId).orElseThrow(
                    () -> new UserException(403, "Undefined EBookId")
            );

            if(!uploader.equals(eBook.getUploader()))
                throw new UserException(403, "Uploader Different");

            EBookFile eBookFile = fileRepository.findById(eBook.getBookFile()).orElseThrow(
                    () -> new UserException(412, "NoFileFound Exception")
            );
            EBookImageFile eBookImageFile = imageFileRepository.findById(eBook.getCoverImage()).orElseThrow(
                    () -> new UserException(412, "NoFileFound Exception")
            );

            fileRepository.delete(eBookFile);
            imageFileRepository.delete(eBookImageFile);
            eBookRepository.delete(eBook);
            return new Response(200, "Success deleteBook");
        } catch (UserException e) {
            return new Response(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("deleteBook Error", e);
            return new Response(500, e.getMessage());
        }
    }

}
