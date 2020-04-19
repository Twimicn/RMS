package show.ginah.rms.service;

import org.springframework.stereotype.Service;
import show.ginah.rms.model.Project;

import java.util.List;

@Service
public interface ProjectService {
    Project getProjectById(long id);

    List<Project> getProjectsByPage(int page, int size);

    int count();

    List<Project> getProjectByUserId(long userId);
}
