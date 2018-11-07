package ms.arqlib.app.adapters;

public class IssueNotFoundException extends IssueServiceException {
    public IssueNotFoundException(String message) {
        super(message);
    }
}
