package show.ginah.rms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import show.ginah.rms.service.FileService;

@Controller
public class ResourceController {
    @Autowired
    FileService fileService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file).toString();
    }
}
