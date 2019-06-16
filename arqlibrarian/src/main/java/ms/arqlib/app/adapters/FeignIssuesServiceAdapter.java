package ms.arqlib.app.adapters;

import ms.arqlib.app.ports.IssueBookRequest;
import ms.arqlib.app.ports.IssuesService;
import ms.arqlib.app.ports.Issue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Deprecated
@FeignClient(url="${issues-service.url}", name="${issues-service.name}")
public interface FeignIssuesServiceAdapter extends IssuesService {
    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/issues")
    Issue issue(@RequestBody IssueBookRequest request);

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/issues/{id}/issued")
    boolean issued(@PathVariable("id") long bookId);
}