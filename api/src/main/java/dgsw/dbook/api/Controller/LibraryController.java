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
    public ResponseEntity<Response> addBook(@RequestHeader(value = "Token") String token, EBook eBook) {
        log.info("addLibrary - {}, {}", token, eBook);
        return new ResponseEntity<>(libraryService.addBook(token, eBook), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ObjectResponse> getLibrary(@RequestHeader(value = "Token") String token) {
        log.info("getLibrary - {}", token);
        return new ResponseEntity<>(libraryService.getLibrary(token), HttpStatus.OK);
    }

    @GetMapping("/uploaded")
    public ResponseEntity<ObjectResponse> getUploaded(@RequestHeader(value = "Token") String token) {
        log.info("uploaded - {}", token);
        return new ResponseEntity<>(libraryService.getUploaded(token), HttpStatus.OK);
    }

}
