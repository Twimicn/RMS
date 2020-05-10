package show.ginah.rms.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import show.ginah.rms.model.Resource;

import java.util.List;

@Mapper
@Component
public interface ResourceDao {
    @Select("select * from rms_resource where id=#{id} limit 1")
    Resource getResourceById(long id);

    @Select("select * from rms_resource order by id limit #{size} offset #{st}")
    List<Resource> getResourcesByPage(@Param("st") int start, @Param("size") int size);

    @Select("select count(1) from rms_resource")
    int count();

    @Select("select * from rms_resource where project_id=#{projectId} order by id")
    List<Resource> getResourcesByProjectId(long projectId);

    @Insert("insert into rms_resource(name,storage,user_id,user_name,project_id,project_name,create_time,state) values (#{name},#{storage},#{userId},#{userName},#{projectId},#{projectName},#{createTime},#{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Resource resource);
}
