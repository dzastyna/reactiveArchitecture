package ms.arqlib.issues;

import java.util.ArrayList;
import java.util.List;

public class MemoryIssuesRepository implements IssuesRepository {
    private static List<Issue> issues = new ArrayList<>();

    public void clear() {
        issues.clear();
    }

    public void init() {
        // no issues at start yet
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
}
