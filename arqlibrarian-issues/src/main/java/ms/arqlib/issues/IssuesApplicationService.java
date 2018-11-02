package ms.arqlib.issues;


import ms.arqlib.issues.ports.BooksService;
import ms.arqlib.issues.ports.UsersService;

public class IssuesApplicationService {
    private UsersService usersService;
    private BooksService booksService;
    private IssuesRepository issuesRepository;

    public IssuesApplicationService(UsersService usersService, BooksService booksService, IssuesRepository borrowingRepository) {
        this.usersService = usersService;
        this.booksService = booksService;
        this.issuesRepository = borrowingRepository;
    }

    public void issue(long userId, long bookId) {
        if (issued(bookId)) {
            throw new IssueException(S.$("Book with id = %d is already issued", bookId));
        }

        String userDescription = usersService.findDescription(userId);
        String bookDescription = booksService.findDescription(bookId);

        issuesRepository.add(new Issue(userId, bookId, userDescription, bookDescription));
    }

    public boolean issued(long bookId) {
        Issue issue = issuesRepository.findByBookId(bookId);
        return issue != null && issue.returned() == false;
    }
}
