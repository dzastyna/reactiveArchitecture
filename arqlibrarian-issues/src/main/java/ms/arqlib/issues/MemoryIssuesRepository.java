package ms.arqlib.issues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MemoryIssuesRepository implements IssuesRepository {
    private static List<Issue> issues = new ArrayList<>();

    public void clear() {
        issues.clear();
    }

    public void init() {
    }

    @Override
    public void add(Issue issue) {
        issue.setId(Generated.issueId());
        issues.add(issue);
    }

    @Override
    public Issue findByBookId(long bookId) {
        for (Issue issue : issues)
        {
            if (issue.getBookId() == bookId)
            {
                return issue;
            }
        }

        return null;
    }

    @Override
    public Collection<Issue> findAll() {
        return Collections.unmodifiableCollection(issues);
    }
}
