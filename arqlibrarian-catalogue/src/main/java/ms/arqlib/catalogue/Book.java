package ms.arqlib.catalogue;

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

    public void rate(int rating) {
        ratings.add(new SingleRating(rating));
    }

    public double averageRating() {
        if (this.ratings.size() == 0)
        {
            return -1.0;
        }

        return this.ratings.stream()
                .mapToDouble(r -> r.getRating())
                .average().getAsDouble();

    }

    public String descrption() {
        return S.$("%s %s", this.title, this.author);
    }
}
