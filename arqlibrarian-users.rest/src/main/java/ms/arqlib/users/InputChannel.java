package ms.arqlib.users;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface InputChannel {

    String DIRECT="direct";
    String BROADCAST="broadcast";

    @Input(DIRECT)
    MessageChannel direct();

    @Input(BROADCAST)
    MessageChannel broadcast();
}
