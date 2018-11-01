package ms.arqlib.issues;

public interface IssuesRepository {
    void add(Issue issue);

    Issue findByBookId(long bookId);
}
