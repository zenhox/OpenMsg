package hox.seu.edu.cn.openmsg;

public class CoolqPrivateMsg {
    public long user_id;
    public String message;
    public boolean auto_escape;
    public CoolqPrivateMsg(long group_id, String message, boolean auto_escape){
        this.user_id = group_id;
        this.message = message;
        this.auto_escape = auto_escape;
    }
}
