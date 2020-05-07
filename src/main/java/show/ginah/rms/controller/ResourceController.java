package show.ginah.rms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import show.ginah.rms.annotation.Permission;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.common.PageData;
import show.ginah.rms.model.Resource;
import show.ginah.rms.service.FileService;
import show.ginah.rms.service.ResourceService;

@Controller
@RequestMapping("/api/resource")
@CrossOrigin
public class ResourceController {
    private final FileService fileService;
    private final ResourceService resourceService;

    public ResourceController(FileService fileService,
                              ResourceService resourceService) {
        this.fileService = fileService;
        this.resourceService = resourceService;
    }

    @Permission("admin")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<PageData<Resource>> apiList(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        PageData<Resource> resourcePageData = PageData.<Resource>builder()
                .list(resourceService.getResourcesByPage(page, size)).page(page)
                .total(resourceService.count())
                .build();
        return ApiResponse.<PageData<Resource>>builder().status(0).msg("ok").data(resourcePageData).build();
    }
  /*
    @RequestMapping("/listByProjectId")
    @ResponseBody
    public ApiResponse<PageData<Resource>> apiListByProjectId() {

    }*/

    @RequestMapping("/test")
    @ResponseBody
    public String test(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file, "res").toString();
    }
}
