package show.ginah.rms.model;

import lombok.Data;

import java.util.Date;

@Data
public class Project {
    private long id;
    private String name;
    private Date createTime;
    private int state;

    public Project(String name) {
        this.name = name;
        this.createTime = new Date();
        this.state = 0;
    }
}
