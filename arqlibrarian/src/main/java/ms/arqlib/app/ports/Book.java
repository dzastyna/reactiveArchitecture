package ms.arqlib.app.ports;

public class Book {
    private long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int year;
    private String category;
    private double rating;

    public Book() {}

    public Book(long id, String title, String author, String isbn, String publisher, int year, String category, double rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.year = year;
        this.category = category;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }

    public double getRating() {
        return rating;
    }

    public long getId() {
        return id;
    }
}
