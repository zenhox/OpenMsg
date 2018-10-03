package hox.seu.edu.cn.openmsg;


import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClients;

public class CoolqCore {
    private String url_path;
    private String url_get;
    private int sPort;
    private int rPort;

    public CoolqCore(String ip, int sendport, int report){
        url_path = "http://" + ip + ":" + sendport;
        url_get =  "http://" + ip + ":" + report;
        sPort = sendport;
        rPort = report;
    }

    public boolean sendMsg(String url, String json){
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity stringEntity = new StringEntity(json, "UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        post.setEntity(stringEntity);
        try {
            HttpResponse res = client.execute(post);
            ((CloseableHttpClient) client).close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            try {
                ((CloseableHttpClient) client).close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }
    }

    public boolean sendPrivate(String id,String message) {
        String url = this.url_path + CoolqApiStore.SEND_MSG_PRIVATE;
        CoolqPrivateMsg privateMsg = new CoolqPrivateMsg(Long.parseLong(id),message,false);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return sendMsg(url,gson.toJson(privateMsg));
    }

    public boolean sendGroup(String id,String message) {
        // init the http client
        String url = this.url_path + CoolqApiStore.SEND_MSG_GROUP;
        //init post body
        CoolqGroupMsg groupMsg = new CoolqGroupMsg(Long.parseLong(id),message,false);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return sendMsg(url,gson.toJson(groupMsg));
    }

    public List<CoolqGourpInfo> getGroupList(){
        String url = this.url_path + CoolqApiStore.GET_LIST_GROUP;
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
            {
                StringBuilder stringBuilder = new StringBuilder();
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(entity.getContent(),"UTF-8"));
                String line = null;
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
//                System.out.println(stringBuilder.toString());
                CoolqReList reList = gson.fromJson(stringBuilder.toString(),CoolqReList.class);
                return reList.data;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
