package com.example.admin.login;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Xml;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.login.domain.Message;
import com.example.admin.login.domain.weather;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("WorldReadableFiles")
public class MainActivity extends AppCompatActivity {
    private EditText et_name,et_key;
    private TextView tv_sdcard;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;

    private InputStream mInputStream;

    private SharedPreferences sp;
    File path = Environment.getExternalStorageDirectory();
    List<Message> smsList;
    List<weather> mWeatherList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = (EditText) findViewById(R.id.et_name);
        et_key = (EditText) findViewById(R.id.et_key);

        readAccount();
        sdcard();
// import com.example.admin.login.domain.Message;
        smsList = new ArrayList<Message>();
        for (int i = 0;i < 10; i++){
            Message sms = new Message("You are greate."+i,System.currentTimeMillis()+"","138"+i+i,"1");
            smsList.add(sms);
        }
    }


//获取SD卡剩余容量
    private void sdcard() {
        StatFs statfs = new StatFs(path.getPath());
        long blockSize;
        long availableBlocks;
        //获取当前系统版本的等级
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            blockSize = statfs.getBlockSizeLong();
            availableBlocks = statfs.getAvailableBlocksLong();
        }else {
            blockSize = statfs.getBlockSize();
            availableBlocks = statfs.getAvailableBlocks();
        }
        tv_sdcard = (TextView) findViewById(R.id.tv_sdcard);
//        区块大小(totalBlocks = stat.getBlockCountLong();) * 区块数量 等于 存储设备的总大小
        tv_sdcard.setText("  sd卡剩余空间："+formatSize(availableBlocks * blockSize));

    }
    private String formatSize(long size){
        return Formatter.formatFileSize(this,size);
    }
    //读写文件
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
            /**if (path.equals(Environment.MEDIA_MOUNTED)){
                //返回一个File对象，其路径是sd卡的真实路径
                File file = new File(path,"info.txt");

            try {
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write((name+"##"+key).getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
//        SharedPreferences
            sp = getSharedPreferences("info",MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("name",name);
            ed.putString("key",key);
            ed.commit();
        Toast.makeText(this, "Login succeed", Toast.LENGTH_SHORT).show();
     }
    }
    public void readAccount() {
//        File file = new File("data/data/com.example.admin.login/info.txt");
//            用API在内部读写文件
//        File file = new File(getCacheDir(),"info.txt");
//        外部存储
        //MEDIA_MOUNTED：sd卡已经挂载，可用
       /** if (path.equals(Environment.MEDIA_MOUNTED)) {
            File file = new File("sdcard/info.txt");
            if (file.exists()) {
                try {
                    fileInputStream = new FileInputStream(file);
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
        }*/
//        SharedPreferences
        sp = getSharedPreferences("info",MODE_PRIVATE);
        String name = sp.getString("name","");
        String key = sp.getString("key","");
        et_name.setText(name);
        et_key.setText(key);
    }
    //文件访问权限
    public void create1(View view){
        try {
            fileOutputStream = openFileOutput("info1.txt",MODE_PRIVATE);
            try {
                fileOutputStream.write("hello".getBytes());
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void create2(View view) {
        try {
//                    @SuppressWarnings("deprecation")
                    fileOutputStream = openFileOutput("info1.txt", MODE_WORLD_READABLE | MODE_WORLD_WRITEABLE);
            try {
                fileOutputStream.write("hehe".getBytes());
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //读取全局可读的文件
    public void click(View view){
        File file =new File("data/data/com.example.admin.login/info.txt");
        try {
            fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            Toast.makeText(this, bufferedReader.readLine(), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    XmlSerializer备份短信
    public void backupMessage(View view){
        //在内存中把xml备份短信的格式拼接出来
       /** StringBuffer sb = new StringBuffer();
        sb.append("<?xml version='1.0' encoding='utf-8' standalone='yes'?");
        sb.append("<message>");
        for (Message sms : smsList){
            sb.append("<sms>");

            sb.append("<bady>");
            sb.append(sms.getBody());
            sb.append("</bady>");

            sb.append("<data>");
            sb.append(sms.getData());
            sb.append("</data>");

            sb.append("<type>");
            sb.append(sms.getType());
            sb.append("</type>");

            sb.append("<address>");
            sb.append(sms.getAddress());
            sb.append("</address>");

            sb.append("</sms>");
        }
        sb.append("</message>");
        File file = new File("data/data/com.example.admin.login/sms.xml");
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(sb.toString().getBytes());
            fileOutputStream.close();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}*/
        //使用xml序列化器生成xml文件
        //1.拿到序列化器对象
        XmlSerializer xmlSerializer = Xml.newSerializer();
        //2.初始化
        File file = new File("data/data/com.example.admin.login/sms2.xml");
        try {
                fileOutputStream = new FileOutputStream(file);
                 //enconding:指定用什么编码生成xml文件
                xmlSerializer.setOutput(fileOutputStream,"utf-8");
                xmlSerializer.startTag(null,"message");
                for (Message message : smsList){
                xmlSerializer.startTag(null,"sms");
                    xmlSerializer.startTag(null,"body");
                    xmlSerializer.text(message.getBody()+"<body>");
                    xmlSerializer.endTag(null,"body");

                    xmlSerializer.startTag(null,"data");
                    xmlSerializer.text(message.getData()+"<data>");
                    xmlSerializer.endTag(null,"data");

                    xmlSerializer.startTag(null,"type");
                    xmlSerializer.text(message.getType()+"<type>");
                    xmlSerializer.endTag(null,"type");

                    xmlSerializer.startTag(null,"address");
                    xmlSerializer.text(message.getAddress()+"<address>");
                    xmlSerializer.endTag(null,"address");
                xmlSerializer.endTag(null,"sms");
                }
            xmlSerializer.endTag(null,"message");
            //告诉序列化器，文件生成完毕
            xmlSerializer.endDocument();
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
//    XmlPullParser解析天气信息
    public void weatherMessage(View view){
        //获取到src文件夹下的资源文件
        mInputStream = getClassLoader().getResourceAsStream("weather.xml");
        //拿到pull解析器对象
        XmlPullParser xmlPullParser = Xml.newPullParser();
        try {
            xmlPullParser.setInput(mInputStream,"gbk");
            /**获取当前节点的事件类型事件类型主要有五种
             START_DOCUMENT：xml头的事件类型
                    * END_DOCUMENT：xml尾的事件类型
                    * START_TAG：开始节点的事件类型
                    * END_TAG：结束节点的事件类型
                    * TEXT：文本节点的事件类型*/
            int type = xmlPullParser.getEventType();
            weather weather = null;
            while (type != XmlPullParser.END_DOCUMENT){
                switch (type){
                    case XmlPullParser.START_TAG:
                        if ("weather".equals(xmlPullParser.getName())) {
                            mWeatherList = new ArrayList<weather>();
                        }else if ("city".equals(xmlPullParser.getName())){
                            //创建weather的javabean对象
                            weather = new weather();
                        }else if ("name".equals(xmlPullParser.getName())){
                            String name = xmlPullParser.nextText();
                            weather.setName(name);
                        }else if ("temp".equals(xmlPullParser.getName())){
                            String temp = xmlPullParser.nextText();
                            weather.setName(temp);
                        }else if ("pm".equals(xmlPullParser.getName())){
                            String pm = xmlPullParser.nextText();
                            weather.setName(pm);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("weather".equals(xmlPullParser.getName())){
                            mWeatherList.add(weather);
                        }break;
                }
                //把指针移动到下一个节点，并返回该节点的事件类型
                type = xmlPullParser.next();
            }
            for (weather weather1:mWeatherList){
                System.out.println(weather1.toString());
            }
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
    }

    

