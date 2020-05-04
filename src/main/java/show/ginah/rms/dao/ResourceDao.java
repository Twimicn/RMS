package show.ginah.rms.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    @Select("select rms_resource.*,rms_user.name as user_name from rms_resource left join rms_user on rms_resource.user_id=rms_user.id where rms_resource.project_id=#{projectId} order by rms_resource.id")
    List<Resource> getResourcesByProjectId(long projectId);
}
