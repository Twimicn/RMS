package show.ginah.rms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.model.Project;
import show.ginah.rms.model.Resource;
import show.ginah.rms.model.User;

import java.util.List;

@Service
public interface ResourceService {
    Resource getResourceById(long id);

    List<Resource> getResourcesByPage(int page, int size);

    int count();

    List<Resource> getResourcesByProjectId(long id);

    List<Resource> getResourcesByUserId(long id);

    ApiResponse<Void> addRes(MultipartFile file, User user, Project project);
}
