package show.ginah.rms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import show.ginah.rms.annotation.Permission;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.common.PageData;
import show.ginah.rms.model.Project;
import show.ginah.rms.model.User;
import show.ginah.rms.service.ProjectService;
import show.ginah.rms.service.UserService;

import java.util.List;

@Controller
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
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
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
    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<PageData<User>> apiUserList(
            @RequestParam long projectId) {
        List<User> users = userService.getUsersByProjectId(projectId);
        PageData<User> userPageData = PageData.<User>builder()
                .list(users).page(1)
                .total(users.size())
                .build();
        return ApiResponse.<PageData<User>>builder().status(0).msg("ok").data(userPageData).build();
    }

    @Permission("teacher,tutor,student")
    @RequestMapping(value = "/mine", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<PageData<Project>> apiMineList(
            @RequestParam long userId) {
        List<Project> projects = projectService.getProjectByUserId(userId);
        PageData<Project> projectPageData = PageData.<Project>builder()
                .list(projects).page(1)
                .total(projects.size())
                .build();
        return ApiResponse.<PageData<Project>>builder().status(0).msg("ok").data(projectPageData).build();
    }
}
