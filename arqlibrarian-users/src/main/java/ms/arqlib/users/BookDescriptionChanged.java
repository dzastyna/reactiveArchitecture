package ms.arqlib.users;

public class BookDescriptionChanged {
    private long bookId;
    private String newDescription;

    public BookDescriptionChanged() {
    }

    public BookDescriptionChanged(long bookId, String newDescription) {
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
