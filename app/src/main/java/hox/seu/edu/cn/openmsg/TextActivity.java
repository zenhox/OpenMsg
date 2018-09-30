package hox.seu.edu.cn.openmsg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TextActivity extends AppCompatActivity {

    private String string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        string = getIntent().getStringExtra("message");
        if(string == null){
            string = "NO MESSAGE!";
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qq_group_msg_btn:
                qq_groupMsg_send("你将要在QQ发送:"+this.string);
                break;
            case R.id.wechat_group_msg_btn:
                wechat_groupMsg_send("你将要在微信发送:"+this.string);
                break;
            case R.id.text_edit_btn:
                tim_groupMsg_send("你将要在TIM发送:"+this.string);
                break;
        }
    }

    public void qq_groupMsg_send(String msg){
        //TODO :
        Toast.makeText(TextActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    public void wechat_groupMsg_send(String msg){
        //TODO :
        Toast.makeText(TextActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    public void tim_groupMsg_send(String msg){
        //TODO :
        Toast.makeText(TextActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}
