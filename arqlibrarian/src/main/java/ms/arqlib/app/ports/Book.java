package ms.arqlib.app.ports;

import ms.arqlib.catalogue.SingleRating;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int year;
    private String category;
    private final List<SingleRating> ratings = new ArrayList<>();

    public Book() {}

    public Book(long id, String title, String author, String isbn, String publisher, int year, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.year = year;
        this.category = category;
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

    public long getId() {
        return id;
    }
}
