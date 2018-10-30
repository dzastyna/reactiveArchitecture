package bnsit.ms.arq.library;

import java.util.ArrayList;
import java.util.List;

public class MemoryUserDao implements UserDao {
    private static final List<User> users = new ArrayList<User>();

    @Override
    public void insert(User user)
    {
        user.setId(Generated.userId());
        users.add(user);
    }

    @Override
    public User findById(long userId) {
        User user = users.stream().filter(u -> u.getId() == userId).findFirst().get();

        if (user == null)
        {
            throw new LibrarianException(String.format("User not found with id = %d", userId));
        }

        return user;
    }

    public void clear() {
        users.clear();
    }

    public void init() {
        insert(new User("kowalski", "kowal", "Jan Kowalski", "Gdynia", "87052507754"));
        insert(new User("nowak", "nowypass", "Piotr Nowak", "Warszawa", "890224031121"));
        insert(new User("koper", "dupadupa", "Wojciech Koperski", "Zakopane", "91121202176"));
    }
}
