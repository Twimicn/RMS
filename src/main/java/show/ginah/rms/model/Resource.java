package show.ginah.rms.model;

import lombok.Data;

import java.util.Date;

@Data
public class Resource {
    private long id;
    private String name;
    private String memo;
    private String storage;
    private Date createTime;
    private long userId;
    private long projectId;
    private int state;

    private String userName;
    private String projectName;
}
