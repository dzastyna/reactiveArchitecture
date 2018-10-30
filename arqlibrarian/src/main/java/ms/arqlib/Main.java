package ms.arqlib;

import ms.arqlib.catalogue.BooksRepository;
import ms.arqlib.catalogue.BooksApplicationService;
import ms.arqlib.catalogue.MemoryBooksRepository;
import ms.arqlib.library.*;

public class Main {

    public static void main(String[] args) {
        Application application = new Application(new ConsoleIn(), new ConsoleOut());
        BooksRepository booksRepository = CreateBooksDao();
        application.setup(new BooksApplicationService(booksRepository));
        application.setup(new BorrowingManager(CreateMemoryUserDao(), booksRepository, CreateMemoryBorrowingDao()));
        application.start();
    }

    private static MemoryBorrowingDao CreateMemoryBorrowingDao()
    {
        MemoryBorrowingDao dao = new MemoryBorrowingDao();
        dao.init();

        return dao;
    }

    private static MemoryUserDao CreateMemoryUserDao()
    {
        MemoryUserDao dao = new MemoryUserDao();
        dao.init();
        return dao;
    }

    private static MemoryBooksRepository CreateBooksDao()
    {
        MemoryBooksRepository dao = new MemoryBooksRepository();
        dao.init();

        return dao;
    }
}
