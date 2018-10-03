package hox.seu.edu.cn.openmsg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    protected ArrayList<String> dataSet;
    protected Context context;
    public CustomAdapter(Context context, ArrayList<String> data) {
        this.context = context;
        this.dataSet = data;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView groupMsg;
        Button button_send;
        Button button_del;
        Context context;

        public MyViewHolder(Context context,View itemView) {
            super(itemView);
            this.groupMsg = (TextView) itemView.findViewById(R.id.groupMessage);
            this.button_send = (Button) itemView.findViewById(R.id.btnSend);
            this.button_del = (Button) itemView.findViewById(R.id.btnDel);
            this.context = context;
            button_del.setOnClickListener(this);
            button_send.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnSend:
                    Intent intent = new Intent(this.context,TextActivity.class);
                    intent.putExtra("message",this.groupMsg.getText());
                    context.startActivity(intent);
                    break;
                case R.id.btnDel:
                    dataSet.remove(this.groupMsg.getText());
                    ItemDao itemDao = new ItemDao(this.context);
                    itemDao.delItem((String) this.groupMsg.getText());
                    notifyDataSetChanged();
                    break;
            }

        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(this.context,view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView groupMsg = holder.groupMsg;

        groupMsg.setText(dataSet.get(listPosition));
//        imageView.setImageResource(null);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}