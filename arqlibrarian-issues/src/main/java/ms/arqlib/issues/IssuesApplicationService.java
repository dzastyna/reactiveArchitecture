package ms.arqlib.issues;


import ms.arqlib.issues.ports.BookNotFoundException;
import ms.arqlib.issues.ports.BooksService;
import ms.arqlib.issues.ports.UserNotFoundException;
import ms.arqlib.issues.ports.UsersService;
import ms.strings.S;

import java.util.Collection;

import static ms.strings.S.$;

public class IssuesApplicationService {
    private UsersService usersService;
    private BooksService booksService;
    private IssuesRepository issuesRepository;

    public IssuesApplicationService(UsersService usersService, BooksService booksService, IssuesRepository borrowingRepository) {
        this.usersService = usersService;
        this.booksService = booksService;
        this.issuesRepository = borrowingRepository;
    }

    public Issue issue(long userId, long bookId) {
        if (issued(bookId)) {
            throw new IssueValidationException($("Book with id = %d is already issued", bookId));
        }

        try {
            String userDescription = usersService.findDescription(userId);
            String bookDescription = booksService.findDescription(bookId);

            Issue issue = new Issue(userId, bookId, userDescription, bookDescription);
            issuesRepository.add(issue);

            return issue;
        } catch (UserNotFoundException e) {
            throw new IssueValidationException(S.$("User not found with id = %d", userId));
        } catch (BookNotFoundException e) {
            throw new IssueValidationException(S.$("Book not found with id = %d", bookId));
        }
    }

    public boolean issued(long bookId) {
        Issue issue = issuesRepository.findByBookId(bookId);
        return issue != null && issue.returned() == false;
    }

    public Collection<Issue> findAll() {
        return this.issuesRepository.findAll();
    }

    public void
    bookDescriptionChanged(long bookId, String bookDesscription) {
        Issue issue = issuesRepository.findByBookId(bookId);
        if (issue != null) {
            issue.changeBookDescription(bookDesscription);
        }
    }
}
