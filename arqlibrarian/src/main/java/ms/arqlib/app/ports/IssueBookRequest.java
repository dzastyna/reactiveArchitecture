package ms.arqlib.app.ports;

public class IssueBookRequest {
    public final long userId;
    public final long bookId;

    public IssueBookRequest(long userId, long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }
}
