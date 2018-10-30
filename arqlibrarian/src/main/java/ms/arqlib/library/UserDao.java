package ms.arqlib.library;

public interface UserDao {
    public void insert(User user);

    User findById(long userId);
}
