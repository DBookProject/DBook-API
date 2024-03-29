package dgsw.dbook.api.Controller;

import dgsw.dbook.api.Domain.EBookData;
import dgsw.dbook.api.Response.ListResponse;
import dgsw.dbook.api.Response.Response;
import dgsw.dbook.api.Service.EBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ebook")
public class EBookController {

    @Autowired
    private EBookService eBookService;

    @GetMapping("/list")
    public ResponseEntity<ListResponse> getList() {
        log.info("getEBookList");
        ListResponse response = eBookService.getList();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PostMapping("/upload")
    public ResponseEntity<Response> upload(@RequestHeader(value = "Authentication") String token, @ModelAttribute EBookData eBookData) {
        log.info("uploadBook - {}, {}", token, eBookData);
        Response response = eBookService.uploadBook(token, eBookData);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Response> editBook(@RequestHeader(value = "Authentication") String token, @PathVariable long bookId, @ModelAttribute EBookData eBookData) {
        log.info("editBook - {}, {}, {}", token, bookId, eBookData);
        Response response = eBookService.editBook(token, bookId, eBookData);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Response> deleteBook(@RequestHeader(value = "Authentication") String token, @PathVariable long bookId) {
        log.info("deleteBook - {}, {}", token, bookId);
        Response response = eBookService.deleteBook(token, bookId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable long imageId) {
        log.info("getEbookImage - {}", imageId);

        Resource resource = eBookService.getImage(imageId);
        if(resource == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource);
        } catch (Exception e) {
            log.error("getImage Error", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable long fileId) {
        log.info("getEbookFile - {}", fileId);

        Object[] objects = eBookService.getFile(fileId);

        if(objects == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Resource resource = (Resource) objects[0];
        String fileName = (String) objects[1];

        try {
            return ResponseEntity.ok().contentType(MediaType.MULTIPART_MIXED)
                    .header("Content-Disposition", "attachment;filename=" + fileName)
                    .body(resource);
        } catch (Exception e) {
            log.error("getFile Error", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
