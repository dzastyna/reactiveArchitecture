package ms.arqlib.catalogue;

import java.util.List;

public interface Publisher {
    void publish(List<DomainEvent> events);
}
