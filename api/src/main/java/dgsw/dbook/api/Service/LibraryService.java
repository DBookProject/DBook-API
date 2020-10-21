package dgsw.dbook.api.Service;

import dgsw.dbook.api.Domain.EBook;
import dgsw.dbook.api.Response.ObjectResponse;
import dgsw.dbook.api.Response.Response;

public interface LibraryService {

    Response addBook(String token, EBook eBook);

    ObjectResponse getLibrary(String token);

    ObjectResponse getUploaded(String token);

}