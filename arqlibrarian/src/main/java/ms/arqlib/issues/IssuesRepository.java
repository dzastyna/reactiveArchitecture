package ms.arqlib.library;

public interface BorrowingDao {
    void insert(Borrowing borrowing);

    Borrowing findByBookId(long bookId);
}
