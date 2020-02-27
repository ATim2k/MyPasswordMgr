package top.atim.mypasswordmgr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddDataActivity extends AppCompatActivity {
    boolean upacc = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
    }

    public void onaddclick(View view) throws InterruptedException {

        final EditText s =findViewById(R.id.uploadeditText);
        System.out.println(s.getText());
        if (s.getText().toString().equals("")){
            Toast.makeText(this,"输入不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }

        final int Max = 100;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgress(0);
        progressDialog.setTitle("正在上传");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(Max);
        progressDialog.show();
        Thread load = new Thread(new Runnable() {
            @Override
            public void run() {
                int p = 0;
                while (p < 99) {
                    try {
                        Thread.sleep(100);
                        p++;
                        progressDialog.setProgress(p);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (upacc){
                        System.out.println("up线程已结束");
                        progressDialog.setProgress(100);
                        progressDialog.cancel();
                        break;
                    }
                }
            }
        });
        load.start();
        Thread up = new Thread(new Runnable() {
            @Override
            public void run() {
                JDBCUpload lg = new JDBCUpload();
                lg.upload(s.getText().toString());
            }
        });
        up.start();
        up.join();
        System.out.println("up线程已结束");
        upacc = true;
        progressDialog.cancel();
        Toast.makeText(this,"上传完毕",Toast.LENGTH_SHORT).show();
        AddDataActivity.this.finish();
    }
}
