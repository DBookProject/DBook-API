package dbook.api.json;

import dbook.api.Domain.EBookOutput;

import java.util.List;

public class LibraryResponse extends Response {

    List<EBookOutput> books;

    public LibraryResponse(int status, String message) {
        super(status, message);
    }

    public LibraryResponse(int status, String message, List<EBookOutput> books) {
        super(status, message);
        this.books = books;
    }

    public List<EBookOutput> getBooks() {
        return books;
    }

}
