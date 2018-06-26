package com.gadgetsaint.downloadmanagerexample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DownloadManager downloadManager;
    private Uri Download_Uri;
    NotificationManager notificationManager;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        //http://www.gadgetsaint.com/wp-content/uploads/2016/11/cropped-web_hi_res_512.png
        Download_Uri = Uri.parse("http://www.gadgetsaint.com/wp-content/uploads/2016/11/cropped-web_hi_res_512.png");
        //https://download.learn2crack.com/files/Node-Android-Chat.zip
        TextView btnSingle = findViewById(R.id.single);
      //  StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
        if (!isStoragePermissionGranted()) {


        }


        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(true);
                request.setTitle("Trakit Downloading " + "Sample");
                request.setDescription("Downloading " + "Sample");
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Trakit/" + "/" + "Sample"+".png");
              //  request.setMimeType("image/*");
                downloadManager.enqueue(request);


            }
        });


    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {
            int notificationId = 0;
            String channelId = "channel-01";
            String channelName = "Channel Name1";
            int importance = NotificationManager.IMPORTANCE_LOW;
            Intent intent1 = new Intent();
            intent1.setAction(android.content.Intent.ACTION_VIEW);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Trakit/" + "/" + "Sample"+".png");

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                mChannel.setSound(null,null);
                notificationManager.createNotificationChannel(mChannel);
                if (file.toString().endsWith(".doc") || file.toString().endsWith(".docx")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/msword");
                } else if (file.toString().endsWith(".png")) {
                    intent1.setDataAndType(Uri.fromFile(file), "image/*");
                } else if (file.toString().endsWith(".gif")) {
                    intent1.setDataAndType(Uri.fromFile(file), "image/gif");
                } else if (file.toString().endsWith(".jpeg")) {
                    intent1.setDataAndType(Uri.fromFile(file), "image/jpeg");
                } else if (file.toString().endsWith(".jpg")) {
                    intent1.setDataAndType(Uri.fromFile(file), "image/*");
                } else if (file.toString().endsWith(".pdf")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/pdf");
                } else if (file.toString().endsWith(".zip") || file.toString().endsWith(".rar")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/zip");
                } else if (file.toString().endsWith(".txt")) {
                    intent1.setDataAndType(Uri.fromFile(file), "text/plain");
                } else if (file.toString().endsWith(".ppt") || file.toString().endsWith(".pptx")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/vnd.ms-powerpoint");
                } else if (file.toString().endsWith(".xls") || file.toString().endsWith(".xlsx")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/vnd.ms-excel");
                } else if (file.toString().endsWith(".rtf")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/rtf");
                } else if (file.toString().endsWith(".wav") || file.toString().endsWith(".mp3")) {
                    intent1.setDataAndType(Uri.fromFile(file), "audio/*");
                } else if (file.toString().endsWith(".3gp") || file.toString().endsWith(".mpg") || file.toString().endsWith(".mpeg") || file.toString().endsWith(".mpe") || file.toString().endsWith(".mp4") || file.toString().endsWith(".avi")) {
                    intent1.setDataAndType(Uri.fromFile(file), "video/*");
                } else if (file.toString().endsWith(".csv")) {
                    intent1.setDataAndType(Uri.fromFile(file), "text/csv");
                } else {
                    intent1.setDataAndType(Uri.fromFile(file), "*/*");
                }
                @SuppressLint("WrongConstant") PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, notificationId, intent1, PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this,channelId)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Trakit")
                                .setAutoCancel(true)
                                .setContentText("All Download completed")
                                .setContentIntent(pIntent);


                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                assert notificationManager != null;
                mBuilder.setOnlyAlertOnce(true);
                notificationManager.notify(notificationId, mBuilder.build());
            }
             if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                 intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                 photoURI = FileProvider.getUriForFile(getApplicationContext(), "com.gadgetsaint.downloadmanagerexample.fileprovider", file);
                 Log.i("Photo1235",""+photoURI.toString());
                 if (photoURI.toString().endsWith(".doc") || photoURI.toString().endsWith(".docx")) {
                    intent1.setDataAndType(photoURI, "application/msword");
                } else if (photoURI.toString().endsWith(".png")) {
                    intent1.setDataAndType(photoURI, "image/*");
                } else if (photoURI.toString().endsWith(".gif")) {
                    intent1.setDataAndType(photoURI, "image/gif");
                } else if (photoURI.toString().endsWith(".jpeg")) {
                    intent1.setDataAndType(photoURI, "image/jpeg");
                } else if (photoURI.toString().endsWith(".jpg")) {
                    intent1.setDataAndType(photoURI, "image/*");
                } else if (photoURI.toString().endsWith(".pdf")) {
                    intent1.setDataAndType(photoURI, "application/pdf");
                } else if (photoURI.toString().endsWith(".zip") || file.toString().endsWith(".rar")) {
                    intent1.setDataAndType(photoURI, "application/zip");
                } else if (photoURI.toString().endsWith(".txt")) {
                    intent1.setDataAndType(photoURI, "text/plain");
                } else if (photoURI.toString().endsWith(".ppt") || file.toString().endsWith(".pptx")) {
                    intent1.setDataAndType(photoURI, "application/vnd.ms-powerpoint");
                } else if (photoURI.toString().endsWith(".xls") || file.toString().endsWith(".xlsx")) {
                    intent1.setDataAndType(photoURI, "application/vnd.ms-excel");
                } else if (photoURI.toString().endsWith(".rtf")) {
                    intent1.setDataAndType(photoURI, "application/rtf");
                } else if (photoURI.toString().endsWith(".wav") || file.toString().endsWith(".mp3")) {
                    intent1.setDataAndType(photoURI, "audio/*");
                } else if (photoURI.toString().endsWith(".3gp") || file.toString().endsWith(".mpg") || file.toString().endsWith(".mpeg") || file.toString().endsWith(".mpe") || file.toString().endsWith(".mp4") || file.toString().endsWith(".avi")) {
                    intent1.setDataAndType(photoURI, "video/*");
                } else if (photoURI.toString().endsWith(".csv")) {
                    intent1.setDataAndType(photoURI, "text/csv");
                } else {
                    intent1.setDataAndType(photoURI, "*/*");
                }

                @SuppressLint("WrongConstant") PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, notificationId, intent1, PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this,channelId)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Trakit")
                                .setAutoCancel(true)
                                .setContentText("All Download completed")
                                .setContentIntent(pIntent);


                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                assert notificationManager != null;
                mBuilder.setOnlyAlertOnce(true);
                notificationManager.notify(notificationId, mBuilder.build());
            }else {

                if (file.toString().endsWith(".doc") || file.toString().endsWith(".docx")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/msword");
                } else if (file.toString().endsWith(".png")) {
                    intent1.setDataAndType(Uri.fromFile(file), "image/*");
                } else if (file.toString().endsWith(".gif")) {
                    intent1.setDataAndType(Uri.fromFile(file), "image/gif");
                } else if (file.toString().endsWith(".jpeg")) {
                    intent1.setDataAndType(Uri.fromFile(file), "image/jpeg");
                } else if (file.toString().endsWith(".jpg")) {
                    intent1.setDataAndType(Uri.fromFile(file), "image/*");
                } else if (file.toString().endsWith(".pdf")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/pdf");
                } else if (file.toString().endsWith(".zip") || file.toString().endsWith(".rar")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/zip");
                } else if (file.toString().endsWith(".txt")) {
                    intent1.setDataAndType(Uri.fromFile(file), "text/plain");
                } else if (file.toString().endsWith(".ppt") || file.toString().endsWith(".pptx")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/vnd.ms-powerpoint");
                } else if (file.toString().endsWith(".xls") || file.toString().endsWith(".xlsx")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/vnd.ms-excel");
                } else if (file.toString().endsWith(".rtf")) {
                    intent1.setDataAndType(Uri.fromFile(file), "application/rtf");
                } else if (file.toString().endsWith(".wav") || file.toString().endsWith(".mp3")) {
                    intent1.setDataAndType(Uri.fromFile(file), "audio/*");
                } else if (file.toString().endsWith(".3gp") || file.toString().endsWith(".mpg") || file.toString().endsWith(".mpeg") || file.toString().endsWith(".mpe") || file.toString().endsWith(".mp4") || file.toString().endsWith(".avi")) {
                    intent1.setDataAndType(Uri.fromFile(file), "video/*");
                } else if (file.toString().endsWith(".csv")) {
                    intent1.setDataAndType(Uri.fromFile(file), "text/csv");
                } else {
                    intent1.setDataAndType(Uri.fromFile(file), "*/*");
                }
                @SuppressLint("WrongConstant") PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, notificationId, intent1, PendingIntent.FLAG_ONE_SHOT);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this,channelId)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Trakit")
                                .setAutoCancel(true)
                                .setContentText("All Download completed")
                                .setContentIntent(pIntent);


                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                assert notificationManager != null;
                mBuilder.setOnlyAlertOnce(true);
                notificationManager.notify(notificationId, mBuilder.build());
            }
        }
    };


    @Override
    protected void onDestroy() {


        super.onDestroy();

        unregisterReceiver(onComplete);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            // permission granted

        }
    }
}
