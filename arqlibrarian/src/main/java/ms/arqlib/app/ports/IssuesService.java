package ms.arqlib.app.ports;

public interface IssuesService {
    void issue(IssueBookRequest request);
    boolean issued(long bookId);
}


