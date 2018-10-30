package bnsit.ms.arq.library;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MemoryBooksDao implements BooksDao {
    private static final List<Book> books = new ArrayList<>();

    public void clear() {
        books.clear();
    }

    @Override
    public void insert(Book book) {
        book.setId(Generated.bookId());
        books.add(book);
    }

    @Override
    public Iterator<Book> findAll() {
        return books.iterator();
    }

    @Override
    public Iterator<Book> findByTitle(String title) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().equals(title.toLowerCase()))
                .collect(Collectors.toList()).iterator();
    }

    @Override
    public Book findById(long id) {
        Book book = books.stream().filter(b -> b.getId() == id).findFirst().get();

        if (book == null) {
            throw new LibrarianException(String.format("Book not found with id = %d", id));
        }

        return book;
    }

    @Override
    public void save(Book book) {
        //nothing to be done, it's memory implementation, so everything is "persisted" instantly
    }

    public void init()
    {
        insert(new Book("Karolcia", "Maria Kruger", "978-83-7568-638-8", "Siedmioróg", 2011, "Literatura dla dzieci i młodzieży"));
        insert(new Book("Komunikacja niewerbalna. Płeć i kultura", "Ewa Głażewska, Urszula Kusio", "978-83-7784-177-8", "Wydawnictwo Uniwersytetu Marii Curie-Skłodowskiej", 2012, "Nauki społeczne"));
        insert(new Book("O powstawaniu gatunków", "Karol Darwin", "978-83-62948-42-0", "Biblioteka Analiz", 2006, "Literatura popularnonaukowa"));
        insert(new Book("Pedagogika ogólna", "Bogusław Śliwerski", "978-83-7850-169-5", "Oficyna Wydawnicza IMPULS", 2013, "Nauki społeczne"));
        insert(new Book("Pinokio", "Carlo Collodi", "978-83-7895-249-7", "ZIELONA SOWA", 2009, "Podręczniki i lektury szkolne"));
        insert(new Book("Podstawy detektywistyki", "Tomasz Aleksandrowicz, Jerzy Konieczny, Anna Konik", "978-83-60807-30-9", "Łośgraf", 2008, "Prawo"));
        insert(new Book("Renesans", "Adam Karpiński","978-83-01-15409-7", "Wydawnictwo Naukowe PWN", 2007, "Nauki humanistyczne"));
    }
}
