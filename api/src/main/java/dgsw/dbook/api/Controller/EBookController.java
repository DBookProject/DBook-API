package dgsw.dbook.api.Controller;

import dgsw.dbook.api.Response.ObjectResponse;
import dgsw.dbook.api.Service.EBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ebook")
public class EBookController {

    @Autowired
    private EBookService eBookService;

    @GetMapping("/list")
    public ResponseEntity<ObjectResponse> getList() {
        System.out.println("getEBookList");
        return new ResponseEntity<>(eBookService.getList(), HttpStatus.OK);
    }

}
