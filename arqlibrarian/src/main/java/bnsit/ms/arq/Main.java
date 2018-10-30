package bnsit.ms.arq;

import bnsit.ms.arq.library.*;

public class Main {

    public static void main(String[] args) {
        Application application = new Application(new ConsoleIn(), new ConsoleOut());
        BooksDao booksDao = CreateBooksDao();
        application.setup(new BooksManager(booksDao));
        application.setup(new BorrowingManager(CreateMemoryUserDao(), booksDao, CreateMemoryBorrowingDao()));
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

    private static MemoryBooksDao CreateBooksDao()
    {
        MemoryBooksDao dao = new MemoryBooksDao();
        dao.init();

        return dao;
    }
}
