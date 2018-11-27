package ms.arqlib.issues;

import ms.rest.service.ErrorInfo;
import ms.rest.service.ResponseMessage;
import ms.strings.S;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/issues")
class IssuesController {
    private final Log log = LogFactory.getLog(getClass());

    @ExceptionHandler(IssueValidationException.class)
    public ResponseEntity issueValidation(IssueValidationException ex) {
        ErrorInfo error = new ErrorInfo(ex);
        log.error(error);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    private IssuesApplicationService applicationService;

    IssuesController(IssuesApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> issue(@RequestBody IssueRequest request) {
        Issue issue = this.applicationService.issue(request.userId, request.bookId);

        return ResponseEntity
                .ok()
                .body(new ResponseMessage(S.$("Book id = %d issued to user id = %d", issue.getBookId(), issue.getUserId())));
    }

    @GetMapping("/{id}/issued")
    public boolean issued(@PathVariable("id") Long bookId) {
        return this.applicationService.issued(bookId);
    }

    @GetMapping
    public Collection<Issue> findAll() {
        return this.applicationService.findAll();
    }
}

class IssueRequest {
    long userId;
    long bookId;

    public IssueRequest(long userId, long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    IssueRequest() {}

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

