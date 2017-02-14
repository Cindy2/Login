package com.example.admin.login;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText et_name,et_key;
    private TextView tv_sdcard;

    File path = Environment.getExternalStorageDirectory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = (EditText) findViewById(R.id.et_name);
        et_key = (EditText) findViewById(R.id.et_key);

        readAccount();
        sdcard();
    }
//获取SD卡剩余容量
    private void sdcard() {
        StatFs statfs = new StatFs(path.getPath());
        long blockSize;
        long totalBlocks;
        long availableBlocks;
        //获取当前系统版本的等级
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            blockSize = statfs.getBlockSizeLong();
            totalBlocks = statfs.getBlockCountLong();
            availableBlocks = statfs.getAvailableBlocksLong();
        }else {
            blockSize = statfs.getBlockSize();
            totalBlocks = statfs.getBlockCount();
            availableBlocks = statfs.getAvailableBlocks();
        }
        tv_sdcard = (TextView) findViewById(R.id.tv_sdcard);
        tv_sdcard.setText("  sd卡剩余空间："+formatSize(availableBlocks * blockSize));

    }
    private String formatSize(long size){
        return Formatter.formatFileSize(this,size);
    }

    public void login(View view){
        String name = et_name.getText().toString();
        String key = et_key.getText().toString();

        CheckBox checkBox = (CheckBox) findViewById(R.id.cb_remember);

        if (checkBox.isChecked()){
//            File file =new File("data/data/com.example.admin.login/info.txt");
//            用API在内部读写文件
//            File file = new File(getCacheDir(),"info.txt");
            //MEDIA_UNKNOWN:不能识别sd卡
            //MEDIA_REMOVED:没有sd卡
            //MEDIA_UNMOUNTED:sd卡存在但是没有挂载
            //MEDIA_CHECKING:sd卡正在准备
            //MEDIA_MOUNTED：sd卡已经挂载，可用
            if (path.equals(Environment.MEDIA_MOUNTED)){
                //返回一个File对象，其路径是sd卡的真实路径
                File file = new File(path,"info.txt");
                FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write((name+"##"+key).getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "Login succeed", Toast.LENGTH_SHORT).show();
     }
    }
    public void readAccount() {
//        File file = new File("data/data/com.example.admin.login/info.txt");
//            用API在内部读写文件
//        File file = new File(getCacheDir(),"info.txt");
//        外部存储
        //MEDIA_MOUNTED：sd卡已经挂载，可用
        if (path.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File("sdcard/info.txt");
            if (file.exists()) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    //把字节流转换成字符流
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                    //读取txt文件里的用户名和密码
                    String text = bufferedReader.readLine();
                    String[] strings = text.split("##");

                    et_name.setText(strings[0]);
                    et_key.setText(strings[1]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
