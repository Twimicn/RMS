package show.ginah.rms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import show.ginah.rms.annotation.Permission;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.common.PageData;
import show.ginah.rms.model.User;
import show.ginah.rms.service.UserService;

@Controller
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<User> apiLogin(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @Permission("admin")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<User> apiCreate(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(required = false, defaultValue = "") String phone,
            @RequestParam(required = false, defaultValue = "0") int roleId
    ) {
        User user = new User(username, password, name, email, phone);
        user.setRoleId(roleId);
        return userService.register(user);
    }

    @Permission("admin")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<PageData<User>> apiList(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        PageData<User> userPageData = PageData.<User>builder()
                .list(userService.getUsersByPage(page, size)).page(page)
                .total(userService.countUser())
                .build();
        return ApiResponse.<PageData<User>>builder().status(0).msg("ok").data(userPageData).build();
    }

    @Permission("admin")
    @RequestMapping(value = "/updateState", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<Boolean> apiUpdateState(
            @RequestParam("id") long id,
            @RequestParam("state") int state) {
        return userService.updateState(id, state);
    }

    @Permission("login")
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<User> apiUserInfo(
            @RequestParam("id") long id
    ) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ApiResponse.<User>builder().data(user).msg("ok").status(0).build();
        } else {
            return ApiResponse.<User>builder().msg("用户不存在").status(1002).build();
        }
    }
}
