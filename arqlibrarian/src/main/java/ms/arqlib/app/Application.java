package ms.arqlib.app;

import ms.arqlib.app.ports.BooksService;
import ms.arqlib.app.ports.IssuesService;
import ms.arqlib.app.ports.UsersService;
import ms.arqlib.app.ports.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Application {

    private UserIn input;
    private UserOut output;
    private BooksService booksService;
    private IssuesService issuesService;
    private UsersService usersService;

    //for now application works for the only sample user with id = 1
    //it will be more dynamic when login process will be supported
    private long userId = 1L;

    public Application(UserIn input, UserOut output) {

        this.input = input;
        this.output = output;
    }

    public void setup(BooksService booksService) {
        this.booksService = booksService;
    }

    public void setup(IssuesService issuesService) {
        this.issuesService = issuesService;
    }

    public void setup(UsersService usersApplicationService) {
        this.usersService = usersService;
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
                case "issue":
                    this.issueBook(args);
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

        boolean issued = issuesService.issued(bookId);
        Book book = booksService.findById(bookId);

        output.printLine(String.format("%s %s", issuedToString(issued), basicInfoFor(book)));
    }

    private String issuedToString(boolean issued) {
        return issued ? "[issued]" : "[available]";
    }

    private void issueBook(String[] args) {
        long bookId = Long.parseLong(args[1]);
        issuesService.issue(userId, bookId);
        Book book = booksService.findById(bookId);

        output.printLine(String.format("Issued: %s", basicInfoFor(book)));
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

        booksService.addBook(title, author, isbn, publisher, year, category);
    }

    private void rateBook(String[] args) {
        if (args.length != 3)
        {
            output.printLine("Wrong rate command format. Try: rate [book id] [rating]");
        }
        long bookId = Long.parseLong(args[1]);
        int rating = Integer.parseInt(args[2]);

        booksService.rate(bookId, rating);
        Book book = booksService.findById(bookId);
        double totalRating = booksService.computeRatingFor(bookId);

        output.printLine(String.format("%s rated: %d, total rating: %1.1f", book.getTitle(), rating, totalRating));
    }

    private void searchBook(String commandString) {
        Collection<Book> books = null;
        if (hasParameters(commandString)) {
            books = booksService.findAll();
        } else {
            String toSearch = substringAfterFirstSpace(commandString);

            books = booksService.findByTitle(toSearch);
            output.printLine(books.isEmpty()
                    ? String.format("'%s' title not Found", toSearch)
                    : String.format("Found: '%s'", toSearch));
        }

        this.print(books);
    }

    private void print(Collection<Book> books) {
        for(Book book : books)
        {
            double rating = booksService.computeRatingFor(book.getId());
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
