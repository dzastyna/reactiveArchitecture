package ms.arqlib.library;

import ms.arqlib.Application;
import ms.arqlib.SpyUserIn;
import ms.arqlib.SpyUserOut;
import ms.arqlib.catalogue.BooksRepository;
import ms.arqlib.catalogue.BooksApplicationService;
import ms.arqlib.catalogue.MemoryBooksRepository;

public class LibraryFixture {

    private int startBooksCount = 0;

    public int startBooksCount() {
        return startBooksCount;
    }

    private Application application;
    private SpyUserIn userIn;
    private SpyUserOut userOut;

    private BooksApplicationService booksApplicationService;
    private BorrowingManager borrowingManager;
    private UserDao userDao;

    public void applicationStarted() {
        this.userOut = new SpyUserOut();
        this.userIn = new SpyUserIn(userOut);

        this.application = new Application(userIn, userOut);

        BooksRepository booksRepository = createBooksDao();
        this.booksApplicationService = new BooksApplicationService(booksRepository);
        this.application.setup(this.booksApplicationService);

        this.userDao = createUserDao();
        this.borrowingManager = new BorrowingManager(this.userDao, booksRepository, createBorrowingDao());
        this.application.setup(this.borrowingManager);
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
        this.userDao.insert(new User("kowalski", "kowal", "Jan Kowalski", "Gdynia", "87052507754"));
        this.userDao.insert(new User("nowak", "nowypass", "Piotr Nowak", "Warszawa", "890224031121"));
        this.userDao.insert(new User("koper", "dupadupa", "Wojciech Koperski", "Zakopane", "91121202176"));
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

    private BorrowingDao createBorrowingDao() {
        Generated.resetBorrowingId();
        MemoryBorrowingDao dao = new MemoryBorrowingDao();
        dao.clear();

        return dao;
    }

    private UserDao createUserDao() {
        Generated.resetUserId();
        MemoryUserDao dao = new MemoryUserDao();
        dao.clear();

        return dao;
    }

    private BooksRepository createBooksDao() {
        Generated.resetBookId();
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

    public long bookByTitle(String title) {
        return this.booksApplicationService.findByTitle(title).next().getId();
    }

    public long bookIdByTitle(String title) {
        return this.booksApplicationService.findByTitle(title).next().getId();
    }
}
