package dbook.api.Service;

import dbook.api.Domain.EBook;
import dbook.api.Domain.EBookOutput;
import dbook.api.json.LibraryResponse;

import java.util.List;

public interface EBookService {

    EBook findEBook(Integer eNo);

    LibraryResponse getShared(String key, String category);

}