package bnsit.ms.arq.library;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private long id;
    private final String title;
    private final String author;
    private final String isbn;
    private final String publisher;
    private final int year;
    private final String category;
    private final List<SingleRating> ratings = new ArrayList<>();

    public Book(String title, String author, String isbn, String publisher, int year, String category) {

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

    public void setId(long id) {
        this.id = id;
    }

    public List<SingleRating> getRatings() {
        return ratings;
    }

    public void add(SingleRating singleRating) {
        this.ratings.add(singleRating);
    }
}
