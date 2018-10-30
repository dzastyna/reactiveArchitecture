package ms.arqlib.library;

import ms.arqlib.catalogue.Book;
import ms.arqlib.catalogue.BooksRepository;
import ms.arqlib.users.User;
import ms.arqlib.users.UsersRepository;

public class BorrowingManager {
    private UsersRepository usersRepository;
    private BooksRepository booksRepository;
    private BorrowingDao borrowingDao;

    public BorrowingManager(UsersRepository usersRepository, BooksRepository booksRepository, BorrowingDao borrowingDao) {
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
        this.borrowingDao = borrowingDao;
    }

    public void borrow(long userId, long bookId) {
        User user = usersRepository.findById(userId);
        Book book = booksRepository.findById(bookId);

        borrowingDao.insert(new Borrowing(user, book));
    }

    public boolean borrowed(long bookId) {
        return borrowingDao.findByBookId(bookId) != null;
    }
}
