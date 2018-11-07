package ms.arqlib.app.ports;

import ms.arqlib.issues.Issue;

public interface IssuesService {
    Issue issue(IssueBookRequest request);
    boolean issued(long bookId);
}


