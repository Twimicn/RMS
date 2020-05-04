package show.ginah.rms.service.impl;

import org.springframework.stereotype.Service;
import show.ginah.rms.dao.ResourceDao;
import show.ginah.rms.model.Resource;
import show.ginah.rms.service.ResourceService;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    private ResourceDao resourceDao;

    public ResourceServiceImpl(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }

    @Override
    public Resource getResourceById(long id) {
        return resourceDao.getResourceById(id);
    }

    @Override
    public List<Resource> getResourcesByPage(int page, int size) {
        return resourceDao.getResourcesByPage((page - 1) * size, size);
    }

    @Override
    public int count() {
        return resourceDao.count();
    }
}
