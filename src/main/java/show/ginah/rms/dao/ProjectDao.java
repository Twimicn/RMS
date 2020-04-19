package show.ginah.rms.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import show.ginah.rms.model.Project;

import java.util.List;

@Mapper
@Component
public interface ProjectDao {
    @Select("select * from rms_project where id=#{id} limit 1")
    Project getProjectById(long id);

    @Select("select * from rms_project limit #{size} offset #{st}")
    List<Project> getProjectsByPage(@Param("st") int start, @Param("size") int size);

    @Select("select count(1) from rms_project")
    int count();

    @Select("select rms_project.*, rms_user_project.role from rms_project left join rms_user_project on rms_project.id = rms_user_project.project_id where user_id=#{userId}")
    List<Project> getProjectByUserId(long userId);
}
