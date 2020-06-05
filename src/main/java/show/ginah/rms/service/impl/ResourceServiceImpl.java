package show.ginah.rms.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.dao.ResourceDao;
import show.ginah.rms.model.FileInfo;
import show.ginah.rms.model.Project;
import show.ginah.rms.model.Resource;
import show.ginah.rms.model.User;
import show.ginah.rms.service.FileService;
import show.ginah.rms.service.ResourceService;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    private ResourceDao resourceDao;
    private FileService fileService;

    public ResourceServiceImpl(ResourceDao resourceDao, FileService fileService) {
        this.resourceDao = resourceDao;
        this.fileService = fileService;
    }

    @Override
    public Resource getResourceById(long id) {
        return resourceDao.getResourceById(id);
    }

    @Override
    public List<Resource> getResourcesByPage(int page, int size) {
        List<Resource> resources = resourceDao.getResourcesByPage((page - 1) * size, size);
        for (Resource resource : resources) {
            resource.setLink(fileService.pathToUrl(resource.getStorage()));
        }
        return resources;
    }

    @Override
    public int count() {
        return resourceDao.count();
    }

    @Override
    public List<Resource> getResourcesByProjectId(long id) {
        List<Resource> resources = resourceDao.getResourcesByProjectId(id);
        for (Resource resource : resources) {
            resource.setLink(fileService.pathToUrl(resource.getStorage()));
        }
        return resources;
    }

    @Override
    public List<Resource> getResourcesByUserId(long id) {
        List<Resource> resources = resourceDao.getResourcesByUserId(id);
        for (Resource resource : resources) {
            resource.setLink(fileService.pathToUrl(resource.getStorage()));
        }
        return resources;
    }

    @Override
    public List<Resource> getResourcesBySearch(String search) {
        List<Resource> resources = resourceDao.getResourcesBySearch("%" + search + "%");
        for (Resource resource : resources) {
            resource.setLink(fileService.pathToUrl(resource.getStorage()));
        }
        return resources;
    }

    @Override
    public ApiResponse<Void> addRes(MultipartFile file, User user, Project project) {
        if (project == null) {
            return ApiResponse.<Void>builder().status(3001).msg("项目不存在").build();
        }
        FileInfo fileInfo = fileService.upload(file, "res");
        if (fileInfo.getPath().equals("")) {
            return ApiResponse.<Void>builder().status(3002).msg("文件上传失败").build();
        }
        Resource resource = new Resource(fileInfo.getFilename(), fileInfo.getPath(), user.getId(), project.getId(), user.getName(), project.getName());
        int r = resourceDao.create(resource);
        if (r <= 0) {
            return ApiResponse.<Void>builder().status(-6).msg("数据库出错").build();
        }
        return ApiResponse.<Void>builder().status(0).msg("ok").build();
    }
}
