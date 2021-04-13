package dgsw.dbook.api.Controller;

import dgsw.dbook.api.Domain.EBook;
import dgsw.dbook.api.Response.ObjectResponse;
import dgsw.dbook.api.Response.Response;
import dgsw.dbook.api.Service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @PostMapping("/add")
    public ResponseEntity<Response> addBook(@RequestHeader(value = "Authentication") String token, @RequestBody EBook eBook) {
        log.info("addLibrary - {}, {}", token, eBook);
        Response response = libraryService.addBook(token, eBook);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("")
    public ResponseEntity<ObjectResponse> getLibrary(@RequestHeader(value = "Authentication") String token) {
        log.info("getLibrary - {}", token);
        ObjectResponse response = libraryService.getLibrary(token);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @GetMapping("/uploaded")
    public ResponseEntity<ObjectResponse> getUploaded(@RequestHeader(value = "Authentication") String token) {
        log.info("uploaded - {}", token);
        ObjectResponse response = libraryService.getUploaded(token);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

}
