package ms.arqlib.catalogue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RubbitPublisher implements Publisher {
    @Autowired
    private BooksChannel channel;

    @Override public void publish(List<DomainEvent> events) {
        events.forEach(e ->
                channel.broadcast().send(MessageBuilder.withPayload(e).build()));
    }

}
