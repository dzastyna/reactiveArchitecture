package bnsit.ms.arq.library;

import java.util.ArrayList;
import java.util.List;

public class MemoryBorrowingDao implements BorrowingDao {
    private static List<Borrowing> borrowings = new ArrayList<>();

    public void clear() {
        borrowings.clear();
    }

    public void init() {
        // no borrowings at start yet
    }

    public Borrowing FindByBookId(long id)
    {
        for (Borrowing borrowing : borrowings)
        {
            if (borrowing.getBookId() == id)
            {
                return borrowing;
            }
        }

        return null;
    }
    @Override
    public void insert(Borrowing borrowing) {
        borrowing.setId(Generated.borrowingId());
        borrowings.add(borrowing);
    }

    @Override
    public Borrowing findByBookId(long bookId) {
        for (Borrowing borrowing : borrowings)
        {
            if (borrowing.getBookId() == bookId)
            {
                return borrowing;
            }
        }

        return null;
    }
}
