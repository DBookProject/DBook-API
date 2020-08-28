package dbook.api.Service;

import dbook.api.Domain.*;
import dbook.api.Repository.EBookRepository;
import dbook.api.Repository.LibraryRepository;
import dbook.api.Repository.TokenRepository;
import dbook.api.Repository.UserRepository;
import dbook.api.Domain.EBookOutput;
import dbook.api.json.LibraryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    EBookRepository eBookRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Override
    public LibraryResponse getLibrary(String token) {
        Long no = 0l;

        try {
            Token t = tokenRepository.findBytKey(token).get();
            String email = t.getTOwnerid();
            User user = userRepository.findByuEmail(email).get();
            no = user.getId();
        } catch(NoSuchElementException e) {
            e.printStackTrace();
            return new LibraryResponse(400, "Undefined Token");
        }

        List<EBookOutput> books = new ArrayList<>();

        List<EBook> bookList = eBookRepository.findByeUploader(no);
        for(EBook eBook : bookList)
            books.add(new EBookOutput(eBook));

        EBookOutput eBook;
        List<Library> libraryList = libraryRepository.findByPk_uNo(no);
        for(Library library : libraryList) {
            eBook = new EBookOutput(eBookRepository.findByeNo(library.getPk().geteNo()).get());
            books.add(eBook);
        }

        return new LibraryResponse(200, "Success getLibrary", books);
    }

}
