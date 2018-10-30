package bnsit.ms.arq.library;

import java.util.Date;

public class Borrowing {
    private long id;
    private final User user;
    private final Book book;
    private final Date when;

    public Borrowing(User user, Book book) {
        this.user = user;
        this.book = book;
        this.when = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBookId() {
        return book.getId();
    }
}
