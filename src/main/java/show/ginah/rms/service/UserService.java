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

    int countUser();

    ApiResponse<Boolean> updateState(long id, int state);

    ApiResponse<User> login(String username, String password);

    ApiResponse<User> register(User user);

    List<User> getUsersByProjectId(long projectId);
}
