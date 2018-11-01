package ms.arqlib.users;

import java.util.List;

public interface UsersRepository {
    void add(User user);

    User findById(long userId);

    List<User> findAll();
}
