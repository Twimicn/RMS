package show.ginah.rms.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import show.ginah.rms.model.Project;

import java.util.List;

@Mapper
@Component
public interface ProjectDao {
    @Select("select * from rms_project where id=#{id} limit 1")
    Project getProjectById(long id);

    @Select("select * from rms_project order by id limit #{size} offset #{st}")
    List<Project> getProjectsByPage(@Param("st") int start, @Param("size") int size);

    @Select("select count(1) from rms_project")
    int count();

    @Select("select rms_project.*, rms_user_project.role from rms_project left join rms_user_project on rms_project.id = rms_user_project.project_id where user_id=#{userId} order by rms_project.id")
    List<Project> getProjectsByUserId(long userId);

    @Select("select * from rms_project where name like #{search}")
    List<Project> getProjectsBySearch(String search);

    @Insert("insert into rms_project(name,create_time,state) values (#{name},#{createTime},#{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Project project);

    @Select("select count(1)>0 as auth from rms_user_project where user_id=#{userId} and project_id=#{projectId}")
    boolean checkInProject(long userId, long projectId);

    @Select("select count(1)>0 as auth from rms_user_project where user_id=#{userId} and project_id=#{projectId} and role=#{role}")
    boolean checkAuth(long userId, long projectId, int role);

    @Insert("insert into rms_user_project(project_id,user_id,role) values (#{projectId},#{userId},#{role})")
    int addMember(long projectId, long userId, int role);

    @Insert("update rms_user_project set role=#{role} where user_id=#{userId} and project_id=#{projectId} and role!=2")
    int editMember(long projectId, long userId, int role);

    @Delete("delete from rms_user_project where user_id=#{userId} and project_id=#{projectId} and role!=2")
    int removeMember(long projectId, long userId);
}
