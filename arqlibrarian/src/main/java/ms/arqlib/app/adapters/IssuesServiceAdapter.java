package ms.arqlib.app.adapters;

import ms.arqlib.app.ports.IssuesService;
import ms.arqlib.issues.IssuesApplicationService;

public class IssuesServiceAdapter implements IssuesService {
    private IssuesApplicationService service;

    public IssuesServiceAdapter(IssuesApplicationService service) {
        this.service = service;
    }

    @Override
    public void issue(long userId, long bookId) {
        this.service.issue(userId, bookId);
    }

    @Override
    public boolean issued(long bookId) {
        return this.service.issued(bookId);
    }
}
