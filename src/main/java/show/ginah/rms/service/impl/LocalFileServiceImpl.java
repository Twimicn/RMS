package show.ginah.rms.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import show.ginah.rms.service.FileService;
import show.ginah.rms.util.MD5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service("localFileService")
public class LocalFileServiceImpl implements FileService {

    @Value("${bunny.upload}")
    private String uploadDir;

    private File getDir() throws FileNotFoundException {
        String basePath = ResourceUtils.getURL("classpath:").getPath();
        File dir = new File(basePath + uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        System.out.println(basePath);
        System.out.println(dir);
        System.out.println(dir.getAbsolutePath());
        return dir;
    }

    @Override
    public String upload(MultipartFile file, String path) {
        try {
            String filename = MD5.encode("res" + System.currentTimeMillis()) + ".txt";
            File dir = getDir();
            File uploadFile = new File(dir.getAbsolutePath() + File.separator + filename);
            file.transferTo(uploadFile);
            return dir.getAbsolutePath() + File.separator + filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
