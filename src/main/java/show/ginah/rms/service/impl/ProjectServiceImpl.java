package show.ginah.rms.service.impl;

import org.springframework.stereotype.Service;
import show.ginah.rms.dao.ProjectDao;
import show.ginah.rms.model.Project;
import show.ginah.rms.service.ProjectService;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectDao projectDao;

    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Project getProjectById(long id) {
        return projectDao.getProjectById(id);
    }

    @Override
    public List<Project> getProjectsByPage(int page, int size) {
        return projectDao.getProjectsByPage((page - 1) * size, size);
    }

    @Override
    public int count() {
        return projectDao.count();
    }
}
