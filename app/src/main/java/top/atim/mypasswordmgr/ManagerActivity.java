package top.atim.mypasswordmgr;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ManagerActivity extends AppCompatActivity {
    List<String> Giao = new ArrayList<>();
    @Override
    protected void onRestart() {
        super.onRestart();
        Thread getdata = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                System.out.println("创建线程完成");
                JDBCGetData woGiao = new JDBCGetData();
                Giao = woGiao.GetData();
            }
        });
        getdata.start();
        try {
            getdata.join();
            ListView listView=findViewById(R.id.lw);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Giao);
            listView.setAdapter(adapter);
            Toast.makeText(this,"数据已刷新",Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this,"登录成功!",Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manager);
        Thread getdata = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                System.out.println("创建线程完成");
                JDBCGetData woGiao = new JDBCGetData();
                Giao = woGiao.GetData();
            }
        });
        getdata.start();
        try {
            getdata.join();
            ListView listView=findViewById(R.id.lw);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Giao);
            listView.setAdapter(adapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*if (Giao == null){
            //Looper.prepare();
            Toast.makeText(ManagerActivity.this,"通讯故障",Toast.LENGTH_SHORT).show();
            //Looper.loop();
        }else{
            //Looper.prepare();
            for (String G:Giao){
                LinearLayout mainLinerLayout = ManagerActivity.this.findViewById(R.id.MyTable);
                TextView textview=new TextView(ManagerActivity.this);
                G = G + "\n——————————\n";
                textview.setText(G);
                mainLinerLayout.addView(textview);
            }
        }*/
    }
    public void onfbtclick(View view){
        startActivity( new Intent(this,AddDataActivity.class));
    }
}
