package ms.arqlib.users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ms.strings.S.$;

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
        Optional<User> user = users.stream().filter(u -> u.getId() == userId).findFirst();

        if (!user.isPresent()) {
            throw new UserNotFoundException($("User not found with id = %d", userId));
        }

        return user.get();
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(users);
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
