package top.atim.mypasswordmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText user;
    private EditText password;
    ProgressBar deng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        deng = findViewById(R.id.progressbar);
        deng.setVisibility(View.INVISIBLE);
    }
    public void onbtclick(View view) {
        trun(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("创建线程完成");
                String n = user.getText().toString().trim();
                String psw = password.getText().toString().trim();
                if (n.equals("")||psw.equals("")){
                    Looper.prepare();
                    Toast toast = Toast.makeText(MainActivity.this,"输入不能为空！",Toast.LENGTH_SHORT);
                    toast.show();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            trun(false);
                        }
                    });
                    Looper.loop();
                    return;
                }
                JDBCLogin lg = new JDBCLogin();
                Boolean result = lg.Login(n,psw);
                if (!result){
                    Looper.prepare();
                    Toast toast=Toast.makeText(MainActivity.this,"用户名不存在或密码错误！",Toast.LENGTH_SHORT);
                    toast.show();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            trun(false);
                        }
                    });
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast toast=Toast.makeText(MainActivity.this,"登录成功，正在加载数据",Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent=new Intent(MainActivity.this,ManagerActivity.class);
                    //intent.putExtra("name",n); 2-26 传递数据，暂不需要
                    startActivity(intent);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            trun(false);
                        }
                    });
                }

            }
        }).start();
    }
    private void trun(Boolean type){
        if (type) deng.setVisibility(View.VISIBLE);else deng.setVisibility(View.INVISIBLE);
    }
}
