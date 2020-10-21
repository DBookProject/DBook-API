package dgsw.dbook.api.Controller;

import dgsw.dbook.api.Domain.EBook;
import dgsw.dbook.api.Response.ObjectResponse;
import dgsw.dbook.api.Response.Response;
import dgsw.dbook.api.Service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @PostMapping("/add")
    public ResponseEntity<Response> addBook(@RequestHeader(value = "Token") String token, EBook eBook) {
        System.out.println("addLibrary - " + token + ", " + eBook.getId());
        return new ResponseEntity<>(libraryService.addBook(token, eBook), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ObjectResponse> getLibrary(@RequestHeader(value = "Token") String token) {
        System.out.println("getLibrary - " + token);
        return new ResponseEntity<>(libraryService.getLibrary(token), HttpStatus.OK);
    }

    @GetMapping("/uploaded")
    public ResponseEntity<ObjectResponse> getUploaded(@RequestHeader(value = "Token") String token) {
        System.out.println("uploaded - " + token);
        return new ResponseEntity<>(libraryService.getUploaded(token), HttpStatus.OK);
    }

}
