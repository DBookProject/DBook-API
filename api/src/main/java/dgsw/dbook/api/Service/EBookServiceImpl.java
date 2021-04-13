package dgsw.dbook.api.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

import dgsw.dbook.api.Config;
import dgsw.dbook.api.Domain.*;
import dgsw.dbook.api.Exception.UserException;
import dgsw.dbook.api.Repository.*;
import dgsw.dbook.api.Response.ListResponse;
import dgsw.dbook.api.Response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;

@Slf4j
@Service
public class EBookServiceImpl implements EBookService {

    @Autowired
    UserRepository userRepository;

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

                    String categoryName = category.getName();

                    for(EBook eBook : eBookRepository.findByCategoryId(category.getId())) {
                        dataMap = new HashMap<>();

                        dataMap.put("id", eBook.getId());
                        dataMap.put("category", categoryName);
                        dataMap.put("title", eBook.getTitle());
                        dataMap.put("author", eBook.getAuthor());
                        dataMap.put("cover_image", "/ebook/image/" + eBook.getCoverImage());
                        dataMap.put("book_file", "/ebook/file/" + eBook.getBookFile());
                        dataMap.put("description", eBook.getDescription());
                        dataMap.put("uploader_id", eBook.getUploader());
                        dataMap.put("publisher", eBook.getPublisher());
                        dataMap.put("published", Config.parseLocalDateTime(eBook.getPublished()));

                        dataArray.add(dataMap);
                    }

                    topMap.put("categoryName", categoryName);
                    topMap.put("categoryId", category.getId());
                    topMap.put("data", dataArray);
                    topList.add(topMap);
                }

                return new ListResponse(200, "Success GetList", topList);
            }
        } catch (UserException e) {
            return new ListResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("getList Error", e);
            return new ListResponse(500, e.getMessage());
        }
    }

    @Override
    public Response uploadBook(String token, EBookData bookData) {
        try {
            String email = tokenRepository.findByToken(token).map(Token::getUserEmail).orElseThrow(
                    () -> new UserException(401, "Unauthorized Token")
            );
            Long uploader = userRepository.findByEmail(email).map(User::getUserId).orElseThrow(
                    () -> new UserException(400, "Undefined UserId")
            );

            String title = bookData.getTitle();
            String author = bookData.getAuthor();
            String category = bookData.getCategory();

            MultipartFile coverImage = bookData.getCoverImage();
            String extendStr = new String(Objects.requireNonNull(coverImage.getOriginalFilename()).toLowerCase().getBytes(StandardCharsets.UTF_8));
            if(!extendStr.endsWith(".jpg") && !extendStr.endsWith(".png"))
                throw new UserException(400, "Unsupported Image Extend Name - " + extendStr);

            MultipartFile bookFile = bookData.getBookFile();
            extendStr = new String(Objects.requireNonNull(bookFile.getOriginalFilename()).toLowerCase().getBytes(StandardCharsets.UTF_8));
            if(!extendStr.endsWith(".epub"))
                throw new UserException(400, "Unsupported File Extend Name - " + extendStr);

            String description = bookData.getDescription();
            String publisher = bookData.getPublisher();
            String published = bookData.getPublished();

            if(title == null)
                throw new UserException(400, "Requires Title");
            if(author == null)
                throw new UserException(400, "Requires Author");
            if(category == null)
                throw new UserException(400, "Requires Category");
            if(publisher == null)
                throw new UserException(400, "Requires Publisher");
            if(published == null)
                throw new UserException(400, "requires Published");

            EBook eBook = new EBook();
            eBook.setTitle(title);
            eBook.setAuthor(author);

            long categoryId = categoryRepository.findByName(category).map(Category::getId).orElse(-1L);
            if(categoryId == -1)
                categoryId = categoryRepository.save(new Category(category)).getId();
            eBook.setCategoryId(categoryId);

            EBookImageFile eBookImageFile = new EBookImageFile(coverImage.getBytes());
            eBookImageFile = imageFileRepository.save(eBookImageFile);
            eBook.setCoverImage(eBookImageFile.getEBookImageId());

            EBookFile eBookFile = new EBookFile(bookFile.getBytes());
            eBookFile = fileRepository.save(eBookFile);
            eBook.setBookFile(eBookFile.getEBookFileId());

            eBook.setDescription(description);
            eBook.setPublisher(publisher);
            eBook.setPublished(Config.parseDateString(published));
            eBook.setUploader(uploader);

            eBookRepository.save(eBook);
            return new Response(200, "Success UploadBook");
        } catch (UserException e) {
            return new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("uploadBook Error", e);
            return new Response(500, e.getMessage());
        }
    }

    @Override
    public Response editBook(String token, long bookId, EBookData bookData) {
        try {
            String email = tokenRepository.findByToken(token).map(Token::getUserEmail).orElseThrow(
                    () -> new UserException(401, "Unauthorized Token")
            );
            Long uploader = userRepository.findByEmail(email).map(User::getUserId).orElseThrow(
                    () -> new UserException(400, "Undefined UserId")
            );
            EBook eBook = eBookRepository.findById(bookId).orElseThrow(
                    () -> new UserException(400, "Undefined EBookId")
            );

            if(!uploader.equals(eBook.getUploader()))
                throw new UserException(400, "Uploader Different");

            String title = bookData.getTitle();
            String author = bookData.getAuthor();
            String category = bookData.getCategory();
            String description = bookData.getDescription();
            String publisher = bookData.getPublisher();
            String published = bookData.getPublished();

            if(title == null)
                throw new UserException(400, "Requires Title");
            if(author == null)
                throw new UserException(400, "Requires Author");
            if(category == null)
                throw new UserException(400, "Requires Category");
            if(description == null)
                throw new UserException(400, "Requires Description");
            if(publisher == null)
                throw new UserException(400, "Requires Publisher");
            if(published == null)
                throw new UserException(400, "Requires Published");

            MultipartFile coverImage = bookData.getCoverImage();
            String extendStr;

            if(coverImage != null) {
                extendStr = new String(Objects.requireNonNull(coverImage.getOriginalFilename()).toLowerCase().getBytes(StandardCharsets.UTF_8));
                if (!extendStr.endsWith(".jpg") && !extendStr.endsWith(".png"))
                    throw new UserException(400, "Unsupported Image Extend Name");

                imageFileRepository.editFile(eBook.getCoverImage(), new SerialBlob(bookData.getCoverImage().getBytes()));
            }

            MultipartFile bookFile = bookData.getBookFile();
            if(bookFile != null) {
                extendStr = new String(Objects.requireNonNull(bookFile.getOriginalFilename()).toLowerCase().getBytes(StandardCharsets.UTF_8));
                if (extendStr.length() < 4)
                    throw new UserException(400, "Too Short File Extend Name");

                extendStr = extendStr.substring(extendStr.length() - 5);
                if (!extendStr.equals(".epub"))
                    throw new UserException(400, "Unsupported File Extend Name");

                fileRepository.editFile(eBook.getBookFile(), new SerialBlob(bookData.getBookFile().getBytes()));
            }

            long categoryId = categoryRepository.findByName(category).map(Category::getId).orElse(-1L);
            if(categoryId == -1)
                categoryId = categoryRepository.save(new Category(category)).getId();

            eBookRepository.editBook(bookId, title, author, categoryId, description, publisher, Config.parseDateString(published));
            return new Response(200, "Success EditBook");
        } catch (UserException e) {
            return new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("editBook Error", e);
            return new Response(500, e.getMessage());
        }
    }

    @Override
    public Response deleteBook(String token, long bookId) {
        try {
            Long uploader = tokenRepository.findByToken(token).map(Token::getId).orElseThrow(
                    () -> new UserException(400, "Unauthorized Token")
            );

            EBook eBook = eBookRepository.findById(bookId).orElseThrow(
                    () -> new UserException(400, "Undefined EBookId")
            );

            if(!uploader.equals(eBook.getUploader()))
                throw new UserException(400, "Uploader Different");

            EBookFile eBookFile = fileRepository.findById(eBook.getBookFile()).orElseThrow(
                    () -> new UserException(400, "NoFileFound Exception")
            );
            EBookImageFile eBookImageFile = imageFileRepository.findById(eBook.getCoverImage()).orElseThrow(
                    () -> new UserException(400, "NoFileFound Exception")
            );

            fileRepository.delete(eBookFile);
            imageFileRepository.delete(eBookImageFile);
            eBookRepository.delete(eBook);
            return new Response(200, "Success deleteBook");
        } catch (UserException e) {
            return new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("deleteBook Error", e);
            return new Response(500, e.getMessage());
        }
    }

    @Override
    public Resource getImage(long imageId) {
        try {
            return new ByteArrayResource(imageFileRepository.findById(imageId).map(EBookImageFile::getEBookImageFile).orElseThrow(
                    () -> new UserException(400, "Undefined ImageId")
            ));
        } catch (UserException e) {
            return null;
        } catch (Exception e) {
            log.error("getEbookImage Error", e);
            return null;
        }
    }

    @Override
    public Object[] getFile(long fileId) {
        try {
            EBook eBook = eBookRepository.findByBookFile(fileId).orElseThrow(
                    () -> new UserException(400, "Undefined FileId(Cannot find eBook)")
            );
            byte[] bytes = fileRepository.findById(fileId).map(EBookFile::getEBookFile).orElseThrow(
                    () -> new UserException(400, "Undefined FileId(Cannot find file)")
            );

            Object[] objects = new Object[2];
            objects[0] = new ByteArrayResource(bytes);
            objects[1] = eBook.getTitle() + " - " + eBook.getAuthor() + ".epub";
            return objects;
        } catch (UserException e) {
            return null;
        } catch (Exception e) {
            log.error("getEbookFile Error", e);
            return null;
        }
    }

}
