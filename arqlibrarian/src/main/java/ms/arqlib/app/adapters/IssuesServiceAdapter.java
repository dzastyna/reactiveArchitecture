package ms.arqlib.app.adapters;

import ms.arqlib.app.ports.Issue;
import ms.arqlib.app.ports.IssueBookRequest;
import ms.arqlib.app.ports.IssuesService;
import ms.arqlib.issues.IssuesApplicationService;

public class IssuesServiceAdapter implements IssuesService {
    private IssuesApplicationService service;

    public IssuesServiceAdapter(IssuesApplicationService service) {
        this.service = service;
    }

    @Override
    public Issue issue(IssueBookRequest request) {
        ms.arqlib.issues.Issue i = this.service.issue(request.userId, request.bookId);
        return new Issue(i.getId(), i.getUserId(), i.getBookId(), i.getUserDescription(), i.getBookDescription(), i.getIssuedOn(), i.getReturnedOn());
    }

    @Override
    public boolean issued(long bookId) {
        return this.service.issued(bookId);
    }
}
