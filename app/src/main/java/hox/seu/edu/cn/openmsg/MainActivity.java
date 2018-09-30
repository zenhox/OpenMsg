package hox.seu.edu.cn.openmsg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<String> data;
    static View.OnClickListener myOnClickListener;
    private ItemDao itemDao;

    protected int cycleViewIndex;
    protected EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        try{
            itemDao = new ItemDao(this);
        }catch (Exception e){
            Toast.makeText(this, "DAO创建失败", Toast.LENGTH_SHORT).show();
        }

        data = new ArrayList<String>();
        data.add("这是一条示例文本，请点击右下角的信封按钮，手动添加您自定义的文本。");
        cycleViewIndex = 1;

        adapter = new CustomAdapter(this,data);
        recyclerView.setAdapter(adapter);

        List<String> texts = null;
        try{
            texts = itemDao.getAllItems();
        }catch (Exception e){
            Toast.makeText(this, "读取List失败", Toast.LENGTH_SHORT).show();
            texts = null;
        }

        if(texts!= null){
            for(String item : texts){
                data.add(item);
                adapter.notifyItemInserted(cycleViewIndex);
                cycleViewIndex += 1;
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });
    }

    /**
     * 弹出的输入框
     * @param
     */
    private void showDialog(final View view){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("请输入你要保存的文本");
        editText = new EditText(view.getContext());

        builder.setView(editText);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点击确认后的处理函数
                if( editText.getText().toString().length() > 0){
//                    Toast.makeText(view.getContext(), "输入成功!", Toast.LENGTH_SHORT).show();
                    data.add(editText.getText().toString());
                    adapter.notifyItemInserted(cycleViewIndex++);
                    if(itemDao.addItem(editText.getText().toString()))
                    {
                        Toast.makeText(view.getContext(), "写入成功!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(view.getContext(), "写入失败!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(view.getContext(), "输入非法", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }



    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "你点击了一个卡片", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToSend(){
        Intent intent = new Intent();
        intent.setClass(this,TextActivity.class);

    }
}
