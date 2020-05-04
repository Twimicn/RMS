package show.ginah.rms.service;

import org.springframework.stereotype.Service;
import show.ginah.rms.model.Resource;

import java.util.List;

@Service
public interface ResourceService {
    Resource getResourceById(long id);

    List<Resource> getResourcesByPage(int page, int size);

    int count();
}
