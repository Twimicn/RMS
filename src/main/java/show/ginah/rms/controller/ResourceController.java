package show.ginah.rms.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import show.ginah.rms.annotation.Permission;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.common.PageData;
import show.ginah.rms.model.Project;
import show.ginah.rms.model.Resource;
import show.ginah.rms.model.User;
import show.ginah.rms.service.ProjectService;
import show.ginah.rms.service.ResourceService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/resource")
@CrossOrigin
public class ResourceController {
    private final ProjectService projectService;
    private final ResourceService resourceService;

    public ResourceController(
            ProjectService projectService,
            ResourceService resourceService) {
        this.projectService = projectService;
        this.resourceService = resourceService;
    }

    @Permission("admin")
    @PostMapping(value = "/list")
    public ApiResponse<PageData<Resource>> apiList(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        PageData<Resource> resourcePageData = PageData.<Resource>builder()
                .list(resourceService.getResourcesByPage(page, size)).page(page)
                .total(resourceService.count())
                .build();
        return ApiResponse.<PageData<Resource>>builder().status(0).msg("ok").data(resourcePageData).build();
    }

    @Permission("student")
    @PostMapping("/upload")
    public ApiResponse<Void> apiAddRes(
            HttpServletRequest request,
            @RequestParam("projectId") long projectId,
            @RequestParam("file") MultipartFile file) {
        User user = (User) request.getAttribute("curUser");
        Project project = projectService.getProjectById(projectId);
        if (projectService.checkInProject(user.getId(), projectId)
                || user.getRoleId() == 1) {
            return resourceService.addRes(file, user, project);
        }
        return ApiResponse.<Void>builder().status(2002).msg("权限不足").build();
    }
}
