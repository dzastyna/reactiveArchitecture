package ms.arqlib.issues;

public class BookDescriptionChanged {
    private long bookId;
    private String newDescription;

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
