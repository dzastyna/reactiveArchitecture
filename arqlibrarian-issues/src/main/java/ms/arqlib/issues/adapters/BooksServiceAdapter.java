package ms.arqlib.issues.adapters;

import ms.arqlib.catalogue.BooksApplicationService;
import ms.arqlib.issues.ports.BooksService;

public class BooksServiceAdapter implements BooksService {
    private BooksApplicationService booksApplicationService;

    public BooksServiceAdapter(BooksApplicationService booksApplicationService) {
        this.booksApplicationService = booksApplicationService;
    }

    @Override
    public String findDescription(long bookId) {
        return this.booksApplicationService.findDescription(bookId);
    }
}
