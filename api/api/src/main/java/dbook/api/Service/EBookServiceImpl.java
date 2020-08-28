package dbook.api.Service;

import dbook.api.Domain.EBook;
import dbook.api.Repository.EBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EBookServiceImpl implements EBookService {

    @Autowired
    EBookRepository eBookRepository;

    @Override
    public EBook findEBook(Integer eNo) {
        return eBookRepository.findByeNo(eNo).orElseGet(() -> new EBook("Undefined"));
    }

}
