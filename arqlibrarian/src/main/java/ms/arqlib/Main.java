package ms.arqlib;

import ms.arqlib.app.Application;
import ms.arqlib.app.ConsoleIn;
import ms.arqlib.app.ConsoleOut;
import ms.arqlib.app.adapters.IssuesServiceAdapter;
import ms.arqlib.app.adapters.UsersServiceAdapter;
import ms.arqlib.app.adapters.BooksServiceAdapter;
import ms.arqlib.app.ports.UsersService;
import ms.arqlib.catalogue.BooksApplicationService;
import ms.arqlib.catalogue.MemoryBooksRepository;
import ms.arqlib.issues.IssuesApplicationService;
import ms.arqlib.issues.IssuesRepository;
import ms.arqlib.issues.MemoryIssuesRepository;
import ms.arqlib.users.MemoryUsersRepository;
import ms.arqlib.users.UsersApplicationService;

public class Main {

    public static void main(String[] args) {
        Application application = new Application(new ConsoleIn(), new ConsoleOut());

        BooksApplicationService booksApplicationService = createBooksApplicationService();
        application.setup(new BooksServiceAdapter(createBooksApplicationService()));

        UsersApplicationService usersApplicationService = createUsersService();
        UsersService usersService = new UsersServiceAdapter(usersApplicationService);
        application.setup(usersService);

        application.setup(new IssuesServiceAdapter(new IssuesApplicationService(
                new ms.arqlib.issues.adapters.UsersServiceAdapter(usersApplicationService),
                new ms.arqlib.issues.adapters.BooksServiceAdapter(booksApplicationService),
                createMemoryIssuesRepository())));

        application.start();
    }

    private static BooksApplicationService createBooksApplicationService() {
        MemoryBooksRepository booksRepository = new MemoryBooksRepository();
        booksRepository.init();

        return new BooksApplicationService(booksRepository);
    }

    private static IssuesRepository createMemoryIssuesRepository()
    {
        MemoryIssuesRepository repo = new MemoryIssuesRepository();
        repo.init();

        return repo;
    }

    private static UsersApplicationService createUsersService()
    {
        MemoryUsersRepository repo = new MemoryUsersRepository();
        repo.init();

        return new UsersApplicationService(repo);
    }
}
