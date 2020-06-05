package show.ginah.rms.service;

import org.springframework.stereotype.Service;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.model.Project;

import java.util.List;

@Service
public interface ProjectService {
    Project getProjectById(long id);

    List<Project> getProjectsByPage(int page, int size);

    int count();

    List<Project> getProjectsByUserId(long userId);

    List<Project> getProjectsBySearch(String search);

    ApiResponse<Long> create(long userId, Project project);

    boolean checkInProject(long useId, long projectId);

    boolean checkAuth(long userId, long projectId, int role);

    ApiResponse<Boolean> addMember(long userId, long projectId, int role);

    ApiResponse<Boolean> editMember(long userId, long projectId, int role);

    ApiResponse<Boolean> removeMember(long userId, long projectId);
}
