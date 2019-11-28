package ms.arqlib.catalogue;

public class BookDescriptionChangedEvent implements DomainEvent {

    private long bookId;
    private String newDescription;

    public BookDescriptionChangedEvent(){}
    public BookDescriptionChangedEvent(long bookId, String newDescription) {
        this.bookId = bookId;
        this.newDescription = newDescription;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }
}
