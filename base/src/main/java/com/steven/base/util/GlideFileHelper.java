package com.steven.base.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.youth.banner.WeakHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @user steven
 * @createDate 2019/4/2 13:13
 * @description 自定义
 */
public class GlideFileHelper {
    private Context context;
    private int msg_save_img_success = 1001;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1001:
                    ToastUitl.showShort("图片已保存到:" + msg.obj);
                    handler.removeMessages(msg_save_img_success);
                    break;
            }
        }
    };

    public GlideFileHelper(Context context) {
        super();
        this.context = context;
    }

    /**
     * Glide保存图片
     *
     * @param url 图片地址
     */
    public void savePicture(final String url) {
        Observable.create(new ObservableOnSubscribe<File>() {

            @Override
            public void subscribe(ObservableEmitter<File> e) throws Exception {
                //通过gilde下载得到file文件
                e.onNext(Glide.with(context)
                        .load(url)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Exception {
                        //获取到下载得到的图片，进行本地保存
                        File pictureFolder = Environment.getExternalStorageDirectory();
                        //第二个参数为你想要保存的目录名称
                        File appDir = new File(pictureFolder, "snow");
                        if (!appDir.exists()) {
                            appDir.mkdirs();
                        }
                        String fileName = System.currentTimeMillis() + ".jpg";
                        File destFile = new File(appDir, fileName);
                        //把gilde下载得到图片复制到定义好的目录中去
                        copy(file, destFile);
                        // 最后通知图库更新
                        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(new File(destFile.getPath()))));
                        Message message = new Message();
                        message.what = msg_save_img_success;
                        message.obj = destFile.getAbsoluteFile();
                        handler.sendMessage(message);
                    }
                });
    }

    /**
     * 复制文件
     *
     * @param file
     * @param destFile
     */
    private void copy(File file, File destFile) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Glide保存本地资源图片
     *
     * @param fileName 文件名
     * @param imgRId   本地资源id
     */
    public void savePicture(final String fileName, Integer imgRId) {
    }

    //往SD卡写入文件的方法
    public void savaFileToSD(String filename, byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/budejie";
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            filename = filePath + "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            //关闭输出流
//            Toast.makeText(context, context.getString(R.string.save_success) + filePath, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);


            Uri uri = Uri.fromFile(dir1);
            intent.setData(uri);
            //这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
            context.sendBroadcast(intent);
        } else Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
    }
}
