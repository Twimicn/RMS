package show.ginah.rms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import show.ginah.rms.annotation.Permission;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.common.PageData;
import show.ginah.rms.model.Project;
import show.ginah.rms.model.User;
import show.ginah.rms.service.ProjectService;

@Controller
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
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
}
