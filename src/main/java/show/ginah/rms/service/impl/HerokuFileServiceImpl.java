package show.ginah.rms.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import show.ginah.rms.service.FileService;
import show.ginah.rms.util.IPFSUtil;

import java.io.IOException;

@Service
@Profile("heroku")
public class HerokuFileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file, String path) {
        try {
            return IPFSUtil.uploadFile(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
