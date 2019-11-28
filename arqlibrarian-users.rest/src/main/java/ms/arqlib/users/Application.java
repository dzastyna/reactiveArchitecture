package ms.arqlib.users;

import ms.rest.service.ErrorInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@EnableBinding(InputChannel.class)
public class Application {

    @StreamListener(InputChannel.DIRECT)
    public void logDirected(String msg) {
        System.out.println("Dostalem " + msg);
    }

    @StreamListener(InputChannel.BROADCAST)
    public void logBroadcast(String msg) {
        System.out.println("Dostalem-BROAD " + msg);
    }

    @StreamListener(InputChannel.DIRECT)
    public void logDirected(BookDescriptionChanged event) {
        System.out.println("Przez " + event);
    }

    @StreamListener(InputChannel.BROADCAST)
    public void logBroadcast(BookDescriptionChanged event) {
        System.out.println("Przez-BROAD " + event);
    }

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
    private final Log log = LogFactory.getLog(getClass());

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity userNotFound(UserNotFoundException ex) {
        ErrorInfo error = new ErrorInfo(ex);
        log.error(error);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity general(UserException ex) {
        ErrorInfo error = new ErrorInfo(ex);
        log.error(error);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

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
