/*
 * Copyright (c) 2025-Present by Sahariyar Ahamad
 *
 * This file is part of DrovixUpdater Library and is provided for use without modification.
 * Redistribution or modification is strictly prohibited.
 * Contact: https://github.com/sahariyarahamad/
 */

package com.sahariyar.drovixupdater;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.webkit.URLUtil;

import androidx.core.content.FileProvider;

import com.sahariyar.drovixupdater.utiles.OnUpdateDownloadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DrovixUpdater {

    private Context context;
    private String updateFileUrl;
    private String updateFileName;
    private String connectionError;
    private InputStream inputStream;
    private FileOutputStream fileOutputStream;
    private OnUpdateDownloadListener onUpdateDownloadListener;


    public DrovixUpdater setUp(Context context){
        this.context = context;
        return this;
    }

    public DrovixUpdater setUrl(String url){
        if (URLUtil.isValidUrl(url)){
            this.updateFileUrl = url;
        }else {
            throw new IllegalArgumentException("your url is not valid");
        }
        return this;
    }

    public DrovixUpdater setFileNameFromUrl(){
        if (updateFileUrl.isEmpty()){
            throw new IllegalArgumentException("url is empty");
        }else {
            this.updateFileName = URLUtil.guessFileName(updateFileUrl, null, null);
        }
        return this;
    }

    public DrovixUpdater setFileName(String fileName){
        if (fileName.isEmpty()){
            throw new IllegalArgumentException("file name is not defined");
        }else {
            this.updateFileName = fileName;
        }
        return this;
    }

    public DrovixUpdater setOnUpdateDownloadListener(OnUpdateDownloadListener onUpdateDownloadListener){
        this.onUpdateDownloadListener = onUpdateDownloadListener;
        return this;
    }

    public void start(){
        new DownloadTask().execute(updateFileUrl);
    }


    private class DownloadTask extends AsyncTask<String, Integer, Boolean>{

        private File downloadFile;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (onUpdateDownloadListener != null){
                onUpdateDownloadListener.OnConnectingBuffer("Connecting...");
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            try {

                String fileUrl = strings[0];

                downloadFile = new File(context.getCacheDir(), updateFileName);

                if (downloadFile.exists())  downloadFile.delete();

                URL url = new URL(fileUrl);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                int fileLength = httpURLConnection.getContentLength();
                inputStream = httpURLConnection.getInputStream();
                fileOutputStream = new FileOutputStream(downloadFile);

                byte[] bytes = new byte[1024];
                int total = 0;
                int count;

                while ((count = inputStream.read(bytes)) != -1){
                    if (isCancelled()){
                        inputStream.close();
                        fileOutputStream.close();
                    }
                    total += count;

                    if (fileLength > 0){
                        publishProgress((int) total * 100 / fileLength);
                    }

                    fileOutputStream.write(bytes, 0, count);
                }

                inputStream.close();
                fileOutputStream.close();
                installApk(context, downloadFile);

                return true;
            } catch (Exception e) {
                connectionError = e.getMessage();
                return false;
            }




        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (onUpdateDownloadListener != null) onUpdateDownloadListener.OnDownloadProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result){
                if (onUpdateDownloadListener != null){
                    onUpdateDownloadListener.OnDownloadComplete(downloadFile);
                }
            }else{
                if (onUpdateDownloadListener != null){
                    onUpdateDownloadListener.OnDownloadError(connectionError);
                }
            }

            result = null;

        }


        private void installApk(Context context, File file){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(getUriPath(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        }

        private Uri getUriPath(File file){
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
                return Uri.fromFile(file);
            }else {
                return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            }
        }


    }


}
