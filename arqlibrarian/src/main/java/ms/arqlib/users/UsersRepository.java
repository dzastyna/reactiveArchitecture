package ms.arqlib.users;

public interface UsersRepository {
    void add(User user);

    User findById(long userId);
}
