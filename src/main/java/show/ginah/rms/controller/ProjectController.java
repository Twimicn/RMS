package show.ginah.rms.controller;

import org.springframework.web.bind.annotation.*;
import show.ginah.rms.annotation.Permission;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.common.Constant;
import show.ginah.rms.common.PageData;
import show.ginah.rms.model.Project;
import show.ginah.rms.model.User;
import show.ginah.rms.service.ProjectService;
import show.ginah.rms.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
    private ProjectService projectService;
    private UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @Permission("admin")
    @PostMapping("/list")
    public ApiResponse<PageData<Project>> apiList(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        PageData<Project> projectPageData = PageData.<Project>builder()
                .list(projectService.getProjectsByPage(page, size)).page(page)
                .total(projectService.count())
                .build();
        return ApiResponse.<PageData<Project>>builder().status(0).msg("ok").data(projectPageData).build();
    }

    @Permission("teacher,tutor,student")
    @PostMapping("/userList")
    public ApiResponse<PageData<User>> apiUserList(
            HttpServletRequest request,
            @RequestParam long projectId) {
        User user = (User) request.getAttribute("curUser");
        if (projectService.checkInProject(user.getId(), projectId)) {
            List<User> users = userService.getUsersByProjectId(projectId);
            PageData<User> userPageData = PageData.<User>builder()
                    .list(users).page(1)
                    .total(users.size())
                    .build();
            return ApiResponse.<PageData<User>>builder().status(0).msg("ok").data(userPageData).build();
        }
        return ApiResponse.<PageData<User>>builder().status(2002).msg("权限不足").build();
    }

    @Permission("teacher,tutor,student")
    @PostMapping("/mine")
    public ApiResponse<PageData<Project>> apiMineList(
            HttpServletRequest request) {
        User user = (User) request.getAttribute("curUser");
        List<Project> projects = projectService.getProjectsByUserId(user.getId());
        PageData<Project> projectPageData = PageData.<Project>builder()
                .list(projects).page(1)
                .total(projects.size())
                .build();
        return ApiResponse.<PageData<Project>>builder().status(0).msg("ok").data(projectPageData).build();
    }

    @Permission("teacher,tutor")
    @PostMapping("/create")
    public ApiResponse<Long> apiCreate(
            HttpServletRequest request,
            @RequestParam String name) {
        User user = (User) request.getAttribute("curUser");
        return projectService.create(user.getId(), new Project(name));
    }

    @Permission("teacher,tutor")
    @PostMapping("/addMember")
    public ApiResponse<Boolean> apiAddMember(
            HttpServletRequest request,
            @RequestParam long userId,
            @RequestParam long projectId,
            @RequestParam int role) {
        User user = (User) request.getAttribute("curUser");
        if (projectService.checkAuth(user.getId(), projectId, Constant.PROJECT_SPONSOR)
                || user.getRoleId() == 1) {
            return projectService.addMember(userId, projectId, role);
        }
        return ApiResponse.<Boolean>builder().status(2002).msg("权限不足").build();
    }

    @Permission("teacher,tutor")
    @PostMapping("/removeMember")
    public ApiResponse<Boolean> apiRemoveMember(
            HttpServletRequest request,
            @RequestParam long userId,
            @RequestParam long projectId) {
        User user = (User) request.getAttribute("curUser");
        if (projectService.checkAuth(user.getId(), projectId, Constant.PROJECT_SPONSOR)
                || user.getRoleId() == 1) {
            return projectService.removeMember(userId, projectId);
        }
        return ApiResponse.<Boolean>builder().status(2002).msg("权限不足").build();
    }
}
