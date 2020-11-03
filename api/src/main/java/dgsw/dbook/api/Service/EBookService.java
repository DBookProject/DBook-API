package dgsw.dbook.api.Service;

import dgsw.dbook.api.Domain.EBookData;
import dgsw.dbook.api.Response.ListResponse;
import dgsw.dbook.api.Response.Response;

public interface EBookService {

    ListResponse getList();

    Response uploadBook(String token, EBookData bookData);

    Response editBook(String token, long bookId, EBookData bookData);

    Response deleteBook(String token, long bookId);

}