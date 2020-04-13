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

    @Select("select * from rms_user where username=#{username} limit 1")
    User getUserByUsername(String username);

    @Select("select * from rms_user where token=#{token} limit 1")
    User getUserByToken(String token);

    @Select("select * from rms_user limit #{size} offset #{st}")
    List<User> getUsersByPage(@Param("st") int start, @Param("size") int size);

    @Insert("insert into rms_user(username,password,email,phone,create_time,role_id) values (#{username},#{password},#{email},#{phone},#{createTime},#{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long create(User user);

    @Update("update rms_user set token=#{token},update_time=#{updateTime} where id=#{id}")
    int updateToken(User user);
}
