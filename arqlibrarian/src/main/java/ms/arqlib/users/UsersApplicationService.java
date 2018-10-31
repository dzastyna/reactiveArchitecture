package ms.arqlib.users;

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
}
