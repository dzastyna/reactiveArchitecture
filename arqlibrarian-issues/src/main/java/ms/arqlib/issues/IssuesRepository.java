package ms.arqlib.issues;

import java.util.Collection;

public interface IssuesRepository {
    void add(Issue issue);

    Issue findByBookId(long bookId);

    Collection<Issue> findAll();
}
