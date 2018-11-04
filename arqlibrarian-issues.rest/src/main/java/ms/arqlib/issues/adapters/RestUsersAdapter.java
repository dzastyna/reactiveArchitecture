package ms.arqlib.issues.adapters;

import ms.arqlib.issues.ports.UsersService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url="${users-service.url}", name="${users-service.name}")
public interface RestUsersAdapter extends UsersService {

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}/description" )
    String findDescription(@PathVariable Long userId);
}