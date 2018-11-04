package ms.arqlib.app.adapters;

import ms.arqlib.app.ports.UsersService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url="${users-service.url}", name="${users-service.name}")
public interface RestUsersServiceAdapter extends UsersService {

}
