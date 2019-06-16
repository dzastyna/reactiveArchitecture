package ms.arqlib.app.ports;

public interface IssuesService {
    Issue issue(IssueBookRequest request);
    boolean issued(long bookId);
}


