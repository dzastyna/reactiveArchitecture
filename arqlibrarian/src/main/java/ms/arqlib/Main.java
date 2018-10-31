package ms.arqlib;

import ms.arqlib.catalogue.BooksApplicationService;
import ms.arqlib.catalogue.MemoryBooksRepository;
import ms.arqlib.issues.*;
import ms.arqlib.users.MemoryUsersRepository;
import ms.arqlib.users.UsersApplicationService;

public class Main {

    public static void main(String[] args) {
        Application application = new Application(new ConsoleIn(), new ConsoleOut());
        application.setup(createBooksApplicationService());

        application.setup(new IssuesApplicationService(
                createUsersService(), createBooksApplicationService(), createMemoryIssuesRepository()));

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
