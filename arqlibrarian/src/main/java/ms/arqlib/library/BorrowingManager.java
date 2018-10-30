package ms.arqlib.library;

import ms.arqlib.catalogue.Book;
import ms.arqlib.catalogue.BooksRepository;

public class BorrowingManager {
    private UserDao userDao;
    private BooksRepository booksRepository;
    private BorrowingDao borrowingDao;

    public BorrowingManager(UserDao userDao, BooksRepository booksRepository, BorrowingDao borrowingDao) {
        this.userDao = userDao;
        this.booksRepository = booksRepository;
        this.borrowingDao = borrowingDao;
    }

    public void borrow(long userId, long bookId) {
        User user = userDao.findById(userId);
        Book book = booksRepository.findById(bookId);

        borrowingDao.insert(new Borrowing(user, book));
    }

    public boolean borrowed(long bookId) {
        return borrowingDao.findByBookId(bookId) != null;
    }
}
