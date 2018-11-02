package ms.arqlib.issues.adapters;

import ms.arqlib.issues.ports.UsersService;
import ms.arqlib.users.UsersApplicationService;

public class UsersServiceAdapter implements UsersService {
    private UsersApplicationService usersApplicationService;

    public UsersServiceAdapter(UsersApplicationService usersApplicationService) {
        this.usersApplicationService = usersApplicationService;
    }

    @Override
    public String findDescription(long userId) {
        return this.usersApplicationService.findDescription(userId);
    }
}
