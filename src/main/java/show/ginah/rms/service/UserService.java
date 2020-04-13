package show.ginah.rms.service;

import org.springframework.stereotype.Service;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.model.User;

import java.util.List;

@Service
public interface UserService {
    User getUserById(long id);

    User getUserByToken(String token);

    List<User> getUsersByPage(int page, int size);

    ApiResponse<User> login(String username, String password);

    ApiResponse<User> register(User user);
}
