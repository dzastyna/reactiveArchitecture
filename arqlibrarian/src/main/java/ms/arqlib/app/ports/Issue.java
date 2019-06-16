package ms.arqlib.app.ports;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class Issue {
    private long id;
    private long userId;
    private long bookId;
    private Date issuedOn;
    private String userDescription;
    private String bookDescription;
    private Optional<LocalDate> returnedOn = Optional.empty();

    public Issue() { }

    public Issue(long userId, long bookId, String userDescription, String bookDescription) {
        this.userId = userId;
        this.bookId = bookId;
        this.userDescription = userDescription;
        this.bookDescription = bookDescription;
        this.issuedOn = new Date();
    }

    public Issue(long id, long userId, long bookId, String userDescription, String bookDescription, Date issuedOn, Optional<LocalDate> returnedOn) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.userDescription = userDescription;
        this.bookDescription = bookDescription;
        this.issuedOn = issuedOn;
        this.returnedOn = returnedOn;
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

    public Optional<LocalDate> getReturnedOn() {
        return returnedOn;
    }

    public boolean returned() {
        return returnedOn.isPresent();
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setIssuedOn(Date issuedOn) {
        this.issuedOn = issuedOn;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public void setReturnedOn(Optional<LocalDate> returnedOn) {
        this.returnedOn = returnedOn;
    }
}

