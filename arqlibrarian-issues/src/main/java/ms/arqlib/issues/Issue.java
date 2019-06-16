package ms.arqlib.issues;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class Issue {
    private long id;
    private final long userId;
    private final long bookId;
    private final Date issuedOn;
    private String userDescription;
    private String bookDescription;
    private Optional<LocalDate> returnedOn = Optional.empty();

    public Issue(long userId, long bookId, String userDescription, String bookDescription) {

        this.userId = userId;
        this.bookId = bookId;
        this.userDescription = userDescription;
        this.bookDescription = bookDescription;
        this.issuedOn = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getBookId() {
        return this.bookId;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public Date getIssuedOn() {
        return issuedOn;
    }

    public boolean returned() {
        return returnedOn.isPresent();
    }

    public Optional<LocalDate> getReturnedOn() {
        return returnedOn;
    }
}
