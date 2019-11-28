package ms.arqlib.catalogue;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface BooksChannel {
    String DIRECT = "direct";
    String BROADCAST = "broadcast";

    @Output(DIRECT)
    MessageChannel direct();

    @Output(BROADCAST)
    MessageChannel broadcast();
}
