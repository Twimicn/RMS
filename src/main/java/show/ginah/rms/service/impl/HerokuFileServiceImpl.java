package show.ginah.rms.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import show.ginah.rms.model.FileInfo;
import show.ginah.rms.service.FileService;
import show.ginah.rms.util.IPFSUtil;

import java.io.IOException;

@Service
@Profile("heroku")
public class HerokuFileServiceImpl implements FileService {
    @Override
    public FileInfo upload(MultipartFile file, String subDir) {
        String path = "";
        try {
            path = IPFSUtil.uploadFile(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileInfo.builder()
                .filename(file.getOriginalFilename())
                .path(path)
                .type("ipfs")
                .build();
    }

    @Override
    public String pathToUrl(String path) {
        return "https://ginahan.herokuapp.com/file/p/" + path;
    }
}
