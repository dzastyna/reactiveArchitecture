package ms.arqlib.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class Application {

	@Bean
	public UsersApplicationService usersService() {
        MemoryUsersRepository repository = new MemoryUsersRepository();
        repository.init();

        return new UsersApplicationService(repository);
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

@RestController
@RequestMapping("/users")
class UsersController {
    private UsersApplicationService service;

    UsersController(UsersApplicationService service) {
        this.service = service;
    }

    @GetMapping
    List<User> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/{id}")
    User findById(@PathVariable("id") Long id) {
        return this.service.findById(id);
    }

    @GetMapping("/{userId}/description")
    String findDescription(@PathVariable Long userId) {
        return this.service.findDescription(userId);
    }
}