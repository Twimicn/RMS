package show.ginah.rms.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import show.ginah.rms.model.User;

import java.util.List;

@Mapper
@Component
public interface UserDao {
    @Select("select * from rms_user where id = #{id}")
    User getUserById(long id);

    @Select("select * from rms_user where username=#{username} or email=#{username} limit 1")
    User getUserByUsername(String username);

    @Select("select * from rms_user where token=#{token} limit 1")
    User getUserByToken(String token);

    @Select("select * from rms_user where name like #{search} or username like #{search}")
    List<User> getUsersBySearch(String search);

    @Select("select * from rms_user order by id limit #{size} offset #{st}")
    List<User> getUsersByPage(@Param("st") int start, @Param("size") int size);

    @Select("select count(1) from rms_user")
    int countUser();

    @Insert("insert into rms_user(username,password,name,email,phone,create_time,role_id) values (#{username},#{password},#{name},#{email},#{phone},#{createTime},#{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int create(User user);

    @Update("update rms_user set token=#{token},update_time=#{updateTime},expire=#{expire} where id=#{id}")
    int updateToken(User user);

    @Update("update rms_user set state=#{state} where id=#{id}")
    int updateState(long id, int state);

    @Select("select rms_user.*, rms_user_project.role from rms_user left join rms_user_project on rms_user.id = rms_user_project.user_id where project_id=#{projectId} order by rms_user_project.role desc,rms_user.id")
    List<User> getUserByProjectId(long projectId);
}
