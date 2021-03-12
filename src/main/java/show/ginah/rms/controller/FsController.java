package show.ginah.rms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/fs/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] test(@PathVariable("id") String id) throws Exception {
        logger.info("id = " + id);
        return IPFSUtil.fetchFile(id);
    }
}
