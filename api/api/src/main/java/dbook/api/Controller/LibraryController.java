package dbook.api.Controller;

import dbook.api.Service.LibraryService;
import dbook.api.json.LibraryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @GetMapping("/library/{no}")
    public ResponseEntity<LibraryResponse> getLibrary(@PathVariable Integer no) {
        LibraryResponse libraryResponse = libraryService.getLibrary(no);
        System.out.println("library - " + no);
        return new ResponseEntity<>(libraryResponse, HttpStatus.OK);
    }

}
