package bnsit.ms.arq;

import bnsit.ms.arq.library.Book;
import bnsit.ms.arq.library.BooksManager;
import bnsit.ms.arq.library.BorrowingManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Application {

    private UserIn input;
    private UserOut output;
    private BooksManager booksManager;
    private BorrowingManager borrowingManager;

    //for now application works for the only sample user with id = 1
    //it will be more dynamic when login process will be supported
    private long userId = 1L;

    public Application(UserIn input, UserOut output) {

        this.input = input;
        this.output = output;
    }

    public void setup(BooksManager booksManager) {
        this.booksManager = booksManager;
    }

    public void setup(BorrowingManager borrowingManager) {
        this.borrowingManager = borrowingManager;
    }

    public void start() {
        output.printLine("Welcome to the ArqLibrarian");

        boolean running = true;

        while (running) {
            output.print("> ");
            String commandString = input.readLine();
            String[] args = commandString.split(" ");
            String commandName = args[0];

            switch (commandName)
            {
                case "exit":
                    running = false;
                    break;
                case "search":
                    this.searchBook(commandString);
                    break;
                case "rate":
                    this.rateBook(args);
                    break;
                case "add":
                    this.addBook();
                    break;
                case "borrow":
                    this.borrowBook(args);
                    break;
                case "status":
                    this.showStatus(args);
                    break;
                default:
                    output.printLine(String.format("Command %s not recognized", commandName));
                    break;
            }
        }
    }

    private void showStatus(String[] args) {
        long bookId = Long.parseLong(args[1]);

        boolean borrowed = borrowingManager.borrowed(bookId);
        Book book = booksManager.findById(bookId);

        output.printLine(String.format("%s %s", borrowedToString(borrowed), basicInfoFor(book)));
    }

    private String borrowedToString(boolean borrowed) {
        return borrowed ? "[borrowed]" : "[available]";
    }

    private void borrowBook(String[] args) {
        long bookId = Long.parseLong(args[1]);
        borrowingManager.borrow(userId, bookId);
        Book book = booksManager.findById(bookId);

        output.printLine(String.format("Borrowed: %s", basicInfoFor(book)));
    }

    private void addBook() {
        output.printLine("Adding a new book");
        output.print("Title: ");
        String title = input.readLine();

        output.print("Author: ");
        String author = input.readLine();

        output.print("isbn: ");
        String isbn = input.readLine();

        output.print("Publihser: ");
        String publisher = input.readLine();

        output.print("Year: ");
        String yearString = input.readLine();
        int year = Integer.parseInt(yearString);

        output.print("Category: ");
        String category = input.readLine();

        booksManager.create(title, author, isbn, publisher, year, category);
    }

    private void rateBook(String[] args) {
        if (args.length != 3)
        {
            output.printLine("Wrong rate command format. Try: rate [book id] [rating]");
        }
        long bookId = Long.parseLong(args[1]);
        int rating = Integer.parseInt(args[2]);

        booksManager.rate(bookId, rating);
        Book book = booksManager.findById(bookId);
        double totalRating = booksManager.computeRatingFor(bookId);

        output.printLine(String.format("%s rated: %d, total rating: %1.1f", book.getTitle(), rating, totalRating));
    }

    private void searchBook(String commandString) {
        Iterator<Book> books = null;
        if (hasParameters(commandString))
        {
            books = booksManager.findAll();
        }
        else
        {
            String toSearch = substringAfterFirstSpace(commandString);

            books = booksManager.findByTitle(toSearch);
            output.printLine(books.hasNext()
                    ? String.format("Found: '%s'", toSearch)
                    : String.format("'%s' title not Found", toSearch));
        }

        this.print(books);
    }

    private void print(Iterator<Book> iterator) {
        List<Book> books = new ArrayList<>();
        iterator.forEachRemaining(books::add);

        for(Book book : books)
        {
            double rating = booksManager.computeRatingFor(book.getId());
            output.printLine(String.format("%s: %s, rating: %s", book.getId(), basicInfoFor(book), formatted(rating)));
        }
    }

    private String formatted(double rating) {
        if (rating < 0.0) {
            return "Not rated yet";
        }

        return String.format("%1.1f", rating);
    }

    private String basicInfoFor(Book book) {
        return String.format("\"%s\" - %s, %s", book.getTitle(), book.getAuthor(), book.getCategory());
    }

    private static String substringAfterFirstSpace(String command)
    {
        return command.substring(command.indexOf(' ') + 1);
    }

    private static boolean hasParameters(String command)
    {
        return command.indexOf(' ') < 0;
    }
}
