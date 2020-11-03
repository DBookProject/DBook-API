package dgsw.dbook.api.Controller;

import dgsw.dbook.api.Domain.EBookData;
import dgsw.dbook.api.Response.ListResponse;
import dgsw.dbook.api.Response.Response;
import dgsw.dbook.api.Service.EBookService;
import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(eBookService.getList(), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Response> upload(@RequestHeader(value = "Token") String token, @ModelAttribute EBookData eBookData) {
        log.info("uploadBook - {}, {}", token, eBookData.getTitle());
        return new ResponseEntity<>(eBookService.uploadBook(token, eBookData), HttpStatus.OK);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Response> editBook(@RequestHeader(value = "Token") String token, @PathVariable long bookId, @ModelAttribute EBookData eBookData) {
        log.info("editBook - {}, {}", token, bookId);
        return new ResponseEntity<>(eBookService.editBook(token, bookId, eBookData), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Response> deleteBook(@RequestHeader(value = "Token") String token, @PathVariable long bookId) {
        log.info("deleteBook - {}, {}", token, bookId);
        return new ResponseEntity<>(eBookService.deleteBook(token, bookId), HttpStatus.OK);
    }

}
