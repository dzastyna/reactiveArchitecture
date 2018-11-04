package ms.arqlib.app.ports;

public class AddBookRequest {
    public final String title;
    public final String author;
    public final String isbn;
    public final String publisher;
    public final int year;
    public final String category;

    public AddBookRequest(String title, String author, String isbn, String publisher, int year, String category) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.year = year;
        this.category = category;
    }
}
