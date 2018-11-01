package ms.arqlib.users;

import java.util.ArrayList;
import java.util.List;

public class MemoryUsersRepository implements UsersRepository {
    private static final List<User> users = new ArrayList<User>();

    @Override
    public void add(User user)
    {
        user.setId(Generated.userId());
        users.add(user);
    }

    @Override
    public User findById(long userId) {
        User user = users.stream().filter(u -> u.getId() == userId).findFirst().get();

        if (user == null)
        {
            throw new UserException(S.$("User not found with id = %d", userId));
        }

        return user;
    }

    public void clear() {
        users.clear();
    }

    public void init() {
        add(new User("kowalski", "kowal", "Jan Kowalski", "Gdynia", "87052507754"));
        add(new User("nowak", "nowypass", "Piotr Nowak", "Warszawa", "890224031121"));
        add(new User("koper", "dupadupa", "Wojciech Koperski", "Zakopane", "91121202176"));
    }
}
