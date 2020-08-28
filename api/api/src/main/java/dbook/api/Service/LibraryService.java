package dbook.api.Service;

import dbook.api.json.LibraryResponse;

public interface LibraryService {

    LibraryResponse getLibrary(String token);

}