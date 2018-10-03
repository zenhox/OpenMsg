package hox.seu.edu.cn.openmsg;

public class CoolqGroupMsg {
    public long group_id;
    public String message;
    public boolean auto_escape;
    public CoolqGroupMsg(long group_id, String message, boolean auto_escape){
        this.group_id = group_id;
        this.message = message;
        this.auto_escape = auto_escape;
    }
}
