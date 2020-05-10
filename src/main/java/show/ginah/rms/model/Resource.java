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
    private String userName;
    private String projectName;
    private int state;
    private String link;

    public Resource() {

    }

    public Resource(String name, String storage, long userId, long projectId, String userName, String projectName) {
        this.name = name;
        this.storage = storage;
        this.userId = userId;
        this.projectId = projectId;
        this.userName = userName;
        this.projectName = projectName;
        this.createTime = new Date();
        this.state = 0;
    }
}
