package show.ginah.rms.service;

import org.springframework.web.multipart.MultipartFile;
import show.ginah.rms.model.FileInfo;

public interface FileService {
    FileInfo upload(MultipartFile file);
}
