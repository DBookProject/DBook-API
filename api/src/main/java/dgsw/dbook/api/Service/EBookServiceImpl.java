package dgsw.dbook.api.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dgsw.dbook.api.Domain.EBook;
import dgsw.dbook.api.Exception.UserException;
import dgsw.dbook.api.Repository.EBookRepository;
import dgsw.dbook.api.Repository.TokenRepository;
import dgsw.dbook.api.Response.ObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EBookServiceImpl implements EBookService {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    EBookRepository eBookRepository;

    @Override
    public ObjectResponse getList() {
        try {
            List<EBook> list = eBookRepository.findAll();

            if(list.size() == 0)
                throw new UserException(204, "No Content");

            else {
                Map<String, Object> map = new HashMap<>();
                map.put("books", list);
                return new ObjectResponse(200, "Success GetList", map);
            }
        } catch (UserException e) {
            return new ObjectResponse(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new ObjectResponse(500, e.getMessage());
        }
    }

}
