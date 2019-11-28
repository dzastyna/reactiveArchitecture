package ms.arqlib.catalogue;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private MessageChannel direct;
    private MessageChannel broadcast;

    public GreetingController(BooksChannel channel) {
        this.direct = channel.direct();
        this.broadcast = channel.broadcast();
    }

    @RequestMapping("/hi/{name}")
    ResponseEntity<String> hi(@PathVariable String name) {
        this.direct.send(MessageBuilder
                        .withPayload("Direct " + name).build());
        this.broadcast.send(MessageBuilder
                .withPayload("Direct " + name).build());

        return ResponseEntity.ok(name);
    }
}
