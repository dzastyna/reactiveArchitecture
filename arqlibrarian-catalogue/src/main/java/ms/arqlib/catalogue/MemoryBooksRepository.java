package ms.arqlib.catalogue;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryBooksRepository implements BooksRepository {
    private static final List<Book> books = new ArrayList<>();

    public void clear() {
        books.clear();
    }

    @Override
    public Book add(Book book) {
        book.setId(Generated.bookId());
        books.add(book);

        return book;
    }

    @Override
    public Collection<Book> findAll() {
        return Collections.unmodifiableCollection(books);
    }

    @Override
    public Collection<Book> findByTitle(String title) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(long id) {
        return books.stream().filter(b -> b.getId() == id).findFirst();
    }

    @Override
    public void save(Book book) {
        //nothing to be done, it's memory implementation, so everything is "persisted" instantly
    }

    public void init()
    {
        add(new Book("Karolcia", "Maria Kruger", "978-83-7568-638-8", "Siedmioróg", 2011, "Literatura dla dzieci i młodzieży"));
        add(new Book("Komunikacja niewerbalna. Płeć i kultura", "Ewa Głażewska, Urszula Kusio", "978-83-7784-177-8", "Wydawnictwo Uniwersytetu Marii Curie-Skłodowskiej", 2012, "Nauki społeczne"));
        add(new Book("O powstawaniu gatunków", "Karol Darwin", "978-83-62948-42-0", "Biblioteka Analiz", 2006, "Literatura popularnonaukowa"));
        add(new Book("Pedagogika ogólna", "Bogusław Śliwerski", "978-83-7850-169-5", "Oficyna Wydawnicza IMPULS", 2013, "Nauki społeczne"));
        add(new Book("Pinokio", "Carlo Collodi", "978-83-7895-249-7", "ZIELONA SOWA", 2009, "Podręczniki i lektury szkolne"));
        add(new Book("Podstawy detektywistyki", "Tomasz Aleksandrowicz, Jerzy Konieczny, Anna Konik", "978-83-60807-30-9", "Łośgraf", 2008, "Prawo"));
        add(new Book("Renesans", "Adam Karpiński","978-83-01-15409-7", "Wydawnictwo Naukowe PWN", 2007, "Nauki humanistyczne"));
    }
}
