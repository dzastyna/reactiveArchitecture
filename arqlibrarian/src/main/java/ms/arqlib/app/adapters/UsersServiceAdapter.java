package ms.arqlib.app.adapters;

import ms.arqlib.app.ports.UsersService;
import ms.arqlib.users.UsersApplicationService;

public class UsersServiceAdapter implements UsersService {
    private UsersApplicationService usersApplicationService;

    public UsersServiceAdapter(UsersApplicationService usersApplicationService) {
        this.usersApplicationService = usersApplicationService;
    }
}
