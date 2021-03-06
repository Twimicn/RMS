package show.ginah.rms.controller;

import org.springframework.web.bind.annotation.*;
import show.ginah.rms.annotation.Permission;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.common.Constant;
import show.ginah.rms.common.PageData;
import show.ginah.rms.model.Project;
import show.ginah.rms.model.Resource;
import show.ginah.rms.model.User;
import show.ginah.rms.service.ProjectService;
import show.ginah.rms.service.ResourceService;
import show.ginah.rms.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
    private ProjectService projectService;
    private UserService userService;
    private ResourceService resourceService;

    public ProjectController(ProjectService projectService,
                             UserService userService,
                             ResourceService resourceService) {
        this.projectService = projectService;
        this.userService = userService;
        this.resourceService = resourceService;
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
    @PostMapping("/view")
    public ApiResponse<Project> apiView(
            HttpServletRequest request,
            @RequestParam long projectId) {
        User user = (User) request.getAttribute("curUser");
        if (projectService.checkInProject(user.getId(), projectId)
                || user.getRoleId() == 1) {
            Project project = projectService.getProjectById(projectId);
            return ApiResponse.<Project>builder().status(0).msg("ok").data(project).build();
        }
        return ApiResponse.<Project>builder().status(2002).msg("权限不足").build();
    }

    @Permission("teacher,tutor,student")
    @PostMapping("/userList")
    public ApiResponse<PageData<User>> apiUserList(
            HttpServletRequest request,
            @RequestParam long projectId) {
        User user = (User) request.getAttribute("curUser");
        if (projectService.checkInProject(user.getId(), projectId)
                || user.getRoleId() == 1) {
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
    @PostMapping("/resList")
    public ApiResponse<PageData<Resource>> apiResList(
            HttpServletRequest request,
            @RequestParam("projectId") long projectId) {
        User user = (User) request.getAttribute("curUser");
        if (projectService.checkInProject(user.getId(), projectId)
                || user.getRoleId() == 1) {
            List<Resource> resources = resourceService.getResourcesByProjectId(projectId);
            PageData<Resource> resourcePageData = PageData.<Resource>builder()
                    .list(resources).page(1)
                    .total(resources.size())
                    .build();
            return ApiResponse.<PageData<Resource>>builder().status(0).msg("ok").data(resourcePageData).build();
        }
        return ApiResponse.<PageData<Resource>>builder().status(2002).msg("权限不足").build();
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

    @Permission("teacher,tutor,student")
    @PostMapping("/their")
    public ApiResponse<PageData<Project>> apiTheirList(
            @RequestParam long id) {
        List<Project> projects = projectService.getProjectsByUserId(id);
        PageData<Project> projectPageData = PageData.<Project>builder()
                .list(projects).page(1)
                .total(projects.size())
                .build();
        return ApiResponse.<PageData<Project>>builder().status(0).msg("ok").data(projectPageData).build();
    }

    @Permission("teacher,tutor,student")
    @PostMapping("/search")
    public ApiResponse<PageData<Project>> apiSearch(
            @RequestParam String word) {
        List<Project> projects = projectService.getProjectsBySearch(word);
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
            User user1 = userService.getUserById(userId);
            if (user1 == null) {
                return ApiResponse.<Boolean>builder().status(1002).msg("用户不存在").build();
            }
            return projectService.addMember(userId, projectId, role);
        }
        return ApiResponse.<Boolean>builder().status(2002).msg("权限不足").build();
    }

    @Permission("teacher,tutor")
    @PostMapping("/editMember")
    public ApiResponse<Boolean> apiEditMember(
            HttpServletRequest request,
            @RequestParam long userId,
            @RequestParam long projectId,
            @RequestParam int role) {
        User user = (User) request.getAttribute("curUser");
        if (projectService.checkAuth(user.getId(), projectId, Constant.PROJECT_SPONSOR)
                || user.getRoleId() == 1) {
            return projectService.editMember(userId, projectId, role);
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
