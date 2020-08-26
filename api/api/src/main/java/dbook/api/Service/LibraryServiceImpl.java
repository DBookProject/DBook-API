package dbook.api.Service;

import dbook.api.Domain.EBook;
import dbook.api.Domain.EBookOutput;
import dbook.api.Domain.Library;
import dbook.api.Repository.EBookRepository;
import dbook.api.Repository.LibraryRepository;
import dbook.api.json.LibraryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    EBookRepository eBookRepository;

    @Autowired
    LibraryRepository libraryRepository;

    @Override
    public LibraryResponse getLibrary(Integer no) {
        List<EBookOutput> books = new ArrayList<>();

        List<EBook> bookList = eBookRepository.findByeUploader(no);
        for(EBook eBook : bookList)
            books.add(new EBookOutput(eBook));

        EBookOutput eBook;
        List<Library> libraryList = libraryRepository.findByPk_uNo(no);
        for(Library library : libraryList) {
            try {
                eBook = new EBookOutput(eBookRepository.findByeNo(library.getPk().geteNo()).get());
            } catch(NoSuchElementException e) {
                return new LibraryResponse(400, e.getMessage());
            }

            books.add(eBook);
        }

        return new LibraryResponse(200, "Success getLibrary", books);
    }

}
