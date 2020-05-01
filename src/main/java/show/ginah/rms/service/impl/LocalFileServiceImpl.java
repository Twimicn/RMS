package show.ginah.rms.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import show.ginah.rms.model.FileInfo;
import show.ginah.rms.service.FileService;
import show.ginah.rms.util.MD5;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Service
@Profile("local")
public class LocalFileServiceImpl implements FileService {

    private final ServletContext context;

    public LocalFileServiceImpl(ServletContext context) {
        this.context = context;
    }

    private File getDir() {
        String basePath = context.getRealPath("upload");
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    @Override
    public FileInfo upload(MultipartFile file) {
        String path = "";
        try {
            String filename = MD5.encode("res" + System.currentTimeMillis()) + ".txt";
            File dir = getDir();
            File uploadFile = new File(dir.getAbsolutePath() + File.separator + filename);
            file.transferTo(uploadFile);
            path = filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileInfo.builder()
                .filename(file.getOriginalFilename())
                .path(path)
                .type("local")
                .build();
    }
}
