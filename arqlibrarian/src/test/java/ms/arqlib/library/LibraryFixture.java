package ms.arqlib.library;

import ms.arqlib.SpyUserIn;
import ms.arqlib.SpyUserOut;
import ms.arqlib.app.Application;
import ms.arqlib.app.adapters.IssuesServiceAdapter;
import ms.arqlib.catalogue.Book;
import ms.arqlib.catalogue.BooksApplicationService;
import ms.arqlib.catalogue.BooksRepository;
import ms.arqlib.catalogue.MemoryBooksRepository;
import ms.arqlib.issues.IssuesApplicationService;
import ms.arqlib.issues.IssuesRepository;
import ms.arqlib.issues.MemoryIssuesRepository;
import ms.arqlib.issues.adapters.BooksServiceAdapter;
import ms.arqlib.issues.adapters.UsersServiceAdapter;
import ms.arqlib.users.MemoryUsersRepository;
import ms.arqlib.users.UsersApplicationService;
import ms.arqlib.users.UsersRepository;

import java.util.Collection;

public class LibraryFixture {

    private int startBooksCount = 0;

    public int startBooksCount() {
        return startBooksCount;
    }

    private Application application;
    private SpyUserIn userIn;
    private SpyUserOut userOut;

    private BooksApplicationService booksApplicationService;
    private IssuesApplicationService issuesApplicationService;
    private UsersApplicationService usersApplicationService;

    public void applicationStarted() {
        this.userOut = new SpyUserOut();
        this.userIn = new SpyUserIn(userOut);

        this.application = new Application(userIn, userOut);

        BooksRepository booksRepository = createBooksRepository();
        this.booksApplicationService = new BooksApplicationService(booksRepository, e -> System.out.println("Dummy publisher"));
        this.application.setup(new ms.arqlib.app.adapters.BooksServiceAdapter(this.booksApplicationService));

        UsersRepository usersRepository = createUserRepository();
        this.usersApplicationService = new UsersApplicationService(usersRepository);
        this.application.setup(new ms.arqlib.app.adapters.UsersServiceAdapter(this.usersApplicationService));

        this.issuesApplicationService = new IssuesApplicationService(
                new UsersServiceAdapter(this.usersApplicationService),
                new BooksServiceAdapter(this.booksApplicationService),
                createIssuesRepository());

        this.application.setup(new IssuesServiceAdapter(this.issuesApplicationService));
    }

    public void hasSampleBooks() {
        this.booksApplicationService.addBook("Karolcia", "Maria Kruger", "978-83-7568-638-8", "Siedmioróg", 2011, "Literatura dla dzieci i młodzieży");
        this.booksApplicationService.addBook("Komunikacja niewerbalna. Płeć i kultura", "Ewa Głażewska, Urszula Kusio", "978-83-7784-177-8", "Wydawnictwo Uniwersytetu Marii Curie-Skłodowskiej", 2012, "Nauki społeczne");
        this.booksApplicationService.addBook("O powstawaniu gatunków", "Karol Darwin", "978-83-62948-42-0", "Biblioteka Analiz", 2006, "Literatura popularnonaukowa");
        this.booksApplicationService.addBook("Pedagogika ogólna", "Bogusław Śliwerski", "978-83-7850-169-5", "Oficyna Wydawnicza IMPULS", 2013, "Nauki społeczne");
        this.booksApplicationService.addBook("Pinokio", "Carlo Collodi", "978-83-7895-249-7", "ZIELONA SOWA", 2009, "Podręczniki i lektury szkolne");
        this.booksApplicationService.addBook("Podstawy detektywistyki", "Tomasz Aleksandrowicz, Jerzy Konieczny, Anna Konik", "978-83-60807-30-9", "Łośgraf", 2008, "Prawo");
        this.booksApplicationService.addBook("Renesans", "Adam Karpiński", "978-83-01-15409-7", "Wydawnictwo Naukowe PWN", 2007, "Nauki humanistyczne");
        this.startBooksCount = 7;
    }

    public void hasSampleUsers() {
        this.usersApplicationService.addUser("kowalski", "kowal", "Jan Kowalski", "Gdynia", "87052507754");
        this.usersApplicationService.addUser("nowak", "nowypass", "Piotr Nowak", "Warszawa", "890224031121");
        this.usersApplicationService.addUser("koper", "dupadupa", "Wojciech Koperski", "Zakopane", "91121202176");
    }


    public void then() {
        this.userIn.enterLine("exit");
        this.application.start();
    }

    public void systemShows(String text) {   // converting from more friedly regexp
        this.userOut.assertContains(text.replace("*", ".*"));
    }

    public void hasBook(String title, String author, String isbn, String publisher, int year, String category) {
        this.booksApplicationService.addBook(title, author, isbn, publisher, year, category);
    }

    public void systemShowsAtLeastLines(int expectedCount) {
        this.userOut.assertContainsAtLeastLines(expectedCount);
    }

    public void userEnters(String text) {
        this.userIn.enterLine(text);
    }

    private IssuesRepository createIssuesRepository() {
        ms.arqlib.issues.Generated.resetIssueId();
        MemoryIssuesRepository repo = new MemoryIssuesRepository();
        repo.clear();

        return repo;
    }

    private UsersRepository createUserRepository() {
        ms.arqlib.users.Generated.resetUserId();
        MemoryUsersRepository dao = new MemoryUsersRepository();
        dao.clear();

        return dao;
    }

    private BooksRepository createBooksRepository() {
        ms.arqlib.catalogue.Generated.resetBookId();
        MemoryBooksRepository dao = new MemoryBooksRepository();
        dao.clear();

        return dao;
    }

    public static String title(String title) {
        return title;
    }

    public static String author(String author) {
        return author;
    }

    public static String publisher(String publisher) {
        return publisher;
    }

    public long bookIdByTitle(String title) {
        Collection<Book> books = this.booksApplicationService.findByTitle(title);
        Book firstBook = books.iterator().next();
        return firstBook.getId();
    }

}
