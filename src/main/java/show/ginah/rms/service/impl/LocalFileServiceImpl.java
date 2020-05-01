package show.ginah.rms.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import show.ginah.rms.service.FileService;
import show.ginah.rms.util.MD5;

import java.io.File;
import java.io.IOException;

@Service("localFileService")
public class LocalFileServiceImpl implements FileService {

    @Value("${bunny.upload}")
    private String uploadDir;

    private File getDir() {
        File dir = new File("src/main/resources/static/" + uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    @Override
    public String upload(MultipartFile file, String path) {
        try {
            String filename = MD5.encode("res" + System.currentTimeMillis()) + ".tmp";
            File dir = getDir();
            File uploadFile = new File(dir.getAbsolutePath() + File.separator + filename);
            file.transferTo(uploadFile);
            return filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
