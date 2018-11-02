package ms.arqlib.issues.ports;

import ms.arqlib.users.User;
import ms.arqlib.users.UsersRepository;

import java.util.List;

// INFO interface segregation
public interface UsersService {
    String findDescription(long userId);
}


