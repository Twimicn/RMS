package show.ginah.rms.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import show.ginah.rms.util.IPFSUtil;

@Controller
@CrossOrigin
public class FsController {
    @RequestMapping(value = "/fs/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] test(@PathVariable("id") String id) throws Exception {
        return IPFSUtil.fetchFile(id);
    }
}
