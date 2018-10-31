package ms.arqlib.issues;


import ms.arqlib.catalogue.BooksApplicationService;
import ms.arqlib.users.UsersApplicationService;
import static ms.arqlib.users.S.$;

public class IssuesApplicationService {
    private UsersApplicationService usersService;
    private BooksApplicationService booksService;
    private IssuesRepository issuesRepository;

    public IssuesApplicationService(UsersApplicationService usersService, BooksApplicationService booksApplicationService, IssuesRepository borrowingRepository) {
        this.usersService = usersService;
        this.booksService = booksApplicationService;
        this.issuesRepository = borrowingRepository;
    }

    public void issue(long userId, long bookId) {
        if (issued(bookId)) {
            throw new IssueException($("Book with id = %d is already issued", bookId));
        }

        String userDescription = usersService.findDescription(userId);
        String bookDescription = booksService.findDescription(bookId);

        issuesRepository.add(new Issue(userId, bookId, userDescription, bookDescription));
    }

    public boolean issued(long bookId) {
        // TODO and was not returned
        return issuesRepository.findByBookId(bookId) != null;
    }
}
