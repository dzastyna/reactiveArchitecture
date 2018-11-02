package ms.arqlib.issues.ports;

import ms.arqlib.catalogue.Book;
import ms.arqlib.catalogue.BooksRepository;

import java.util.Iterator;

// INFO interface segregation

public interface BooksService {
    String findDescription(long bookId);
}
