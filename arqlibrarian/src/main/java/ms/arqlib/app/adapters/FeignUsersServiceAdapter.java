package ms.arqlib.app.adapters;

import ms.arqlib.app.ports.UsersService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url="${users-service.url}", name="${users-service.name}")
public interface FeignUsersServiceAdapter extends UsersService {
    // nothing used yet
}
