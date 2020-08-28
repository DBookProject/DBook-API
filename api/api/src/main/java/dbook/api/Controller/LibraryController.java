package dbook.api.Controller;

import dbook.api.Service.LibraryService;
import dbook.api.json.LibraryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @GetMapping("/library")
    public ResponseEntity<LibraryResponse> getLibrary(@RequestHeader(value = "Token") String token) {
        LibraryResponse libraryResponse = libraryService.getLibrary(token);
        System.out.println("library - " + token);
        return new ResponseEntity<>(libraryResponse, HttpStatus.OK);
    }

}
