package ms.arqlib.users;

import java.util.List;

public class UsersApplicationService {
    private UsersRepository repository;

    public UsersApplicationService(UsersRepository repository) {
        this.repository = repository;
    }

    public String findDescription(long userId) {
        User user = this.repository.findById(userId);
        return user.description();
    }

    public void addUser(String nickname, String password, String fulname, String address, String pesel) {
        this.repository.add(new User(nickname, password, fulname, address, pesel));
    }

    public List<User> findAll() {
        return this.repository.findAll();
    }

    public User findById(long userId) {
        return this.repository.findById(userId);
    }
}
