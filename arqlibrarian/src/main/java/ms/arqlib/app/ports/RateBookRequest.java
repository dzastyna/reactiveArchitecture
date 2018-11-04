package ms.arqlib.app.ports;

public class RateBookRequest {
    public final long bookId;
    public final int rating;

    public RateBookRequest(long bookId, int rating) {
        this.bookId = bookId;
        this.rating = rating;
    }
}
