package show.ginah.rms.service.impl;

import org.springframework.stereotype.Service;
import show.ginah.rms.common.ApiResponse;
import show.ginah.rms.common.Constant;
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

    @Override
    public List<Project> getProjectsByUserId(long userId) {
        return projectDao.getProjectsByUserId(userId);
    }

    @Override
    public ApiResponse<Long> create(long userId, Project project) {
        int row = projectDao.create(project);
        if (row > 0) {
            row = projectDao.addMember(project.getId(), userId, Constant.PROJECT_SPONSOR);
            if (row > 0) {
                return ApiResponse.<Long>builder().msg("ok").status(0).data(project.getId()).build();
            }
        }
        return ApiResponse.<Long>builder().status(-6).msg("数据库出错").build();
    }

    @Override
    public boolean checkInProject(long useId, long projectId) {
        return projectDao.checkInProject(useId, projectId);
    }

    @Override
    public boolean checkAuth(long userId, long projectId, int role) {
        return projectDao.checkAuth(userId, projectId, role);
    }

    @Override
    public ApiResponse<Boolean> addMember(long userId, long projectId, int role) {
        if (checkInProject(userId, projectId)) {
            return ApiResponse.<Boolean>builder().msg("成员已存在").status(3001).data(false).build();
        }
        int row = projectDao.addMember(projectId, userId, role);
        return ApiResponse.<Boolean>builder().msg("ok").status(0).data(row > 0).build();
    }

    @Override
    public ApiResponse<Boolean> editMember(long userId, long projectId, int role) {
        int row = projectDao.editMember(projectId, userId, role);
        if (row <= 0) {
            return ApiResponse.<Boolean>builder().status(2002).msg("权限不足").build();
        }
        return ApiResponse.<Boolean>builder().msg("ok").status(0).data(true).build();
    }

    @Override
    public ApiResponse<Boolean> removeMember(long userId, long projectId) {
        int row = projectDao.removeMember(projectId, userId);
        if (row <= 0) {
            return ApiResponse.<Boolean>builder().status(2002).msg("权限不足").build();
        }
        return ApiResponse.<Boolean>builder().msg("ok").status(0).data(true).build();
    }
}
