package bnsit.ms.arq.library;

public class BorrowingManager {
    private UserDao userDao;
    private BooksDao booksDao;
    private BorrowingDao borrowingDao;

    public BorrowingManager(UserDao userDao, BooksDao booksDao, BorrowingDao borrowingDao) {
        this.userDao = userDao;
        this.booksDao = booksDao;
        this.borrowingDao = borrowingDao;
    }

    public void borrow(long userId, long bookId) {
        User user = userDao.findById(userId);
        Book book = booksDao.findById(bookId);

        borrowingDao.insert(new Borrowing(user, book));
    }

    public boolean borrowed(long bookId) {
        return borrowingDao.findByBookId(bookId) != null;
    }
}
