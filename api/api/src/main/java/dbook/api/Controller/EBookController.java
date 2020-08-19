package dbook.api.Controller;

import dbook.api.Domain.EBook;
import dbook.api.Service.EBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EBookController {

    @Autowired
    private EBookService eBookService;

    @GetMapping("/ebook/path/{no}")
    public ResponseEntity<String> getPath(@PathVariable Integer no) {
        EBook ebook = eBookService.findEBook(no);

        if(ebook.getName().equals("ok"))
            return new ResponseEntity<>(ebook.getPath(), HttpStatus.OK);
        return new ResponseEntity<>(ebook.getPath(), HttpStatus.ACCEPTED);
    }

}
