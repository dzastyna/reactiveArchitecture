package ms.arqlib.issues.adapters;

import ms.arqlib.issues.ports.BooksService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Deprecated
@FeignClient(url="${books-service.url}", name="${books-service.name}")
public interface FeignBooksAdapter extends BooksService {
    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/books/{bookId}/description")
    String findDescription(@PathVariable("bookId") Long bookId);
}
