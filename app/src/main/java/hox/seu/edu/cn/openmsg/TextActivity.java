package hox.seu.edu.cn.openmsg;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TextActivity extends AppCompatActivity {

    private String message;
    private  List<CoolqGourpInfo> groups;
    private  ArrayList<CoolqGourpInfo> select_groups;

    public static final String COOLQ_IP = "255.255.255.255"; //不告诉你，哼~
    public static final int PORT_OUT = 6655;
    public static final int PORT_IN = 5566;
    public static final String ROOT_QQ1 = "892552162";
    public static final String ROOT_QQ2 = "3487442086";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        message = getIntent().getStringExtra("message");
        select_groups = new ArrayList<>();
        if(message == null){
            message = "NO MESSAGE!";
        }
        if(groups == null){
//            new Thread(getGrpList).start();
            CoolqCore coolqCore = new CoolqCore(COOLQ_IP,PORT_OUT,PORT_IN);
            List<CoolqGourpInfo> reList = coolqCore.getGroupList();
            if(reList != null) {
                groups = reList;
            }
        }
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.qq_group_msg_btn:
                qq_groupMsg_send("你将要在QQ发送:"+this.message);
                break;
            case R.id.wechat_group_msg_btn:
                wechat_groupMsg_send("你将要在微信发送:"+this.message);
                break;
            case R.id.text_edit_btn:
                tim_groupMsg_send("你将要在TIM发送:"+this.message);
                break;
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            Toast.makeText(TextActivity.this,data.getString("result"),Toast.LENGTH_SHORT).show();
        }
    };

    // /**  * 网络操作相关的子线程  */
    Runnable sendMsgQQ = () -> {
        CoolqCore coolqCore = new CoolqCore(COOLQ_IP,PORT_OUT,PORT_IN);
        int count = 1;
        for(CoolqGourpInfo id : select_groups){
            boolean re =  coolqCore.sendGroup(new Long(id.group_id).toString(),this.message);
            Message msg = new Message();
            Bundle data = new Bundle();
            if(!re){
                data.putString("result", "发送失败!");
            }else {
                data.putString("result", "发送成功: " + count++);
                String report = "向QQ群:《"+id.group_name+"》 发送了:\n"+this.message;
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    continue;
                }
                coolqCore.sendPrivate(ROOT_QQ1,report);
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    continue;
                }
                coolqCore.sendPrivate(ROOT_QQ2,report);
            }
            msg.setData(data);
            handler.sendMessage(msg);
            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                continue;
            }
        }
    };

    public void qq_groupMsg_send(String msg){
        if(groups != null){
            List<String> nameList = new ArrayList<>();
            boolean[] isCheck = new boolean[groups.size()];
            for(int i=0; i<groups.size();i++){
                isCheck[i] = false;
            }
            for(CoolqGourpInfo gourpInfo : this.groups){
                nameList.add(gourpInfo.group_name);
            }
            CharSequence[] items = nameList.toArray(new CharSequence[nameList.size()]);
//
            AlertDialog.Builder checkBoxDialog = new AlertDialog.Builder(this);
            checkBoxDialog.setTitle("选择群组")
                    .setNegativeButton("取消", null).setPositiveButton("发送", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(select_groups != null && message != null){
                        new Thread(sendMsgQQ).start();
                    }else {
                        Toast.makeText(TextActivity.this,"非法的输入!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            checkBoxDialog.setMultiChoiceItems(items, isCheck
                    , new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if (isChecked){
                                select_groups.add(groups.get(which));
                            }else {
                                select_groups.remove(groups.get(which));
                            }
                        }
                    });
            checkBoxDialog.show();
        }else {
            Toast.makeText(TextActivity.this,"无法获取群组列表，请检查网络连接",Toast.LENGTH_SHORT).show();
        }

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
