package ms.arqlib.app.adapters;

import ms.arqlib.app.ports.Issue;
import ms.arqlib.app.ports.IssueBookRequest;
import ms.arqlib.app.ports.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestTemplateIssuesServiceAdapter implements IssuesService {
    private RestTemplate restTemplate;

    public RestTemplateIssuesServiceAdapter(@Autowired @Qualifier("issuesRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Issue issue(IssueBookRequest request) {
        return this.restTemplate.postForObject("/issues", request, Issue.class);
    }

    @Override
    public boolean issued(long bookId) {
        return this.restTemplate.getForObject("/issues/{id}/issued", boolean.class, bookId);
    }

}
