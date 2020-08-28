package dbook.api.Controller;

import dbook.api.Domain.EBook;
import dbook.api.Service.EBookService;
import dbook.api.json.EBookResponse;
import dbook.api.json.Response;
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
    public ResponseEntity<EBookResponse> getPath(@PathVariable Integer no) {
        EBook ebook = eBookService.findEBook(no);

        if(ebook.getName().equals("Undefined"))
            return new ResponseEntity<>(new EBookResponse(400, "Undefined Book", null), HttpStatus.ACCEPTED);
        return new ResponseEntity<>(new EBookResponse(200, "Success getPath", ebook.getPath()), HttpStatus.OK);
    }

}
