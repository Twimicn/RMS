package show.ginah.rms.service.impl;

import org.springframework.stereotype.Service;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.dao.UserDao;
import show.ginah.rms.model.User;
import show.ginah.rms.service.UserService;
import show.ginah.rms.util.MD5;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    private String generateToken(String username) {
        return MD5.encode("BUNNY_" + (System.currentTimeMillis() / 86400000) + "$" + username);
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByToken(String token) {
        return userDao.getUserByToken(token);
    }

    @Override
    public List<User> getUsersByPage(int page, int size) {
        return userDao.getUsersByPage((page - 1) * size, size);
    }

    @Override
    public ApiResponse<User> login(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            return ApiResponse.<User>builder().status(1002).msg("用户不存在").build();
        }
        if (!user.getPassword().equals(MD5.encode(password))) {
            return ApiResponse.<User>builder().status(1001).msg("密码错误").build();
        }
        if (user.getState() != 1) {
            if (user.getState() == 0) {
                return ApiResponse.<User>builder().status(1005).msg("账号未启用").build();
            }
            if (user.getState() == -1) {
                return ApiResponse.<User>builder().status(1005).msg("账号已被禁用").build();
            }
        }
        user.setToken(generateToken(user.getUsername()));
        user.setUpdateTime(new Date());
        if (userDao.updateToken(user) > 0) {
            return ApiResponse.<User>builder().status(0).msg("ok").data(user).build();
        } else {
            return ApiResponse.<User>builder().status(-6).msg("数据库出错").build();
        }
    }

    @Override
    public ApiResponse<User> register(User user) {
        User u = userDao.getUserByUsername(user.getUsername());
        if (u != null) {
            return ApiResponse.<User>builder().status(1003).msg("用户名已存在").build();
        }
        user.setPassword(MD5.encode(user.getPassword()));
        user.setToken(generateToken(user.getUsername()));
        long uid = userDao.create(user);
        if (uid <= 0) {
            return ApiResponse.<User>builder().status(-6).msg("数据库出错").build();
        }
        return ApiResponse.<User>builder().status(0).msg("ok").data(user).build();
    }
}
