package ms.arqlib.app.ports;

public interface IssuesService {
    void issue(long userId, long bookId);
    boolean issued(long bookId);
}
