package ms.arqlib.issues;

import ms.arqlib.issues.adapters.BooksChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class BooksEventHandler {

    @Autowired
    private IssuesApplicationService service;

    @StreamListener(BooksChannel.BROADCAST)
    private void handleBookDescriptionChanged(BookDescriptionChanged event) {
        service.bookDescriptionChanged(event.getBookId(), event.getNewDescription());
    }

    @StreamListener(BooksChannel.BROADCAST)
    private void handleBroadcastedString(String msg) {
        System.out.println("BS " + msg);
    }

    @StreamListener(BooksChannel.DIRECT)
    private void handleDirectString(String msg) {
        System.out.println("DIR " + msg);
    }
}
