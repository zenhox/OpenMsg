package hox.seu.edu.cn.openmsg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qq_group_msg_btn:
                qq_groupMsg_send("你点击了QQ群发！");
                break;
            case R.id.wechat_group_msg_btn:
                wechat_groupMsg_send("你点击了微信群发！");
                break;
            case R.id.text_edit_btn:
                textEdit_activity();
                break;
        }
    }

    public void qq_groupMsg_send(String msg){
        //TODO :
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    public void wechat_groupMsg_send(String msg){
        //TODO :
        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

    public void textEdit_activity(){
        //在按钮响应函数中添加如下两句话就ok了
        Intent intent=new Intent(MainActivity.this,TextEditActivity.class);
        startActivity(intent);
    }
}
