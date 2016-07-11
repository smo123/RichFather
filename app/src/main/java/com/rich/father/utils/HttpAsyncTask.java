package com.rich.father.utils;

import android.os.AsyncTask;

import java.util.concurrent.Executors;

/**
 * Created by kinpo on 2016/3/1.
 */
public class HttpAsyncTask extends AsyncTask<Object, Integer, Object>{

    private static HttpAsyncTask mHttpAsyncTask;

    private static IHttpAsyncTask mIHttpAsyncTask;
    public interface IHttpAsyncTask{
        void onPreExecute();
        Object doInBackground(Object... params);
        void onProgressUpdate(Integer... values);
        void onPostExecute(Object result);
    }

    public static HttpAsyncTask getInstance(IHttpAsyncTask mIHttpAsyncTask, Object...params){
        mHttpAsyncTask = new HttpAsyncTask(mIHttpAsyncTask);
        mHttpAsyncTask.executeOnExecutor(Executors.newCachedThreadPool(), params);
        return mHttpAsyncTask;
    }

    public HttpAsyncTask(IHttpAsyncTask mIHttpAsyncTask){
        this.mIHttpAsyncTask = mIHttpAsyncTask;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mIHttpAsyncTask.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object... params) {
        return mIHttpAsyncTask.doInBackground(params);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mIHttpAsyncTask.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        mIHttpAsyncTask.onPostExecute(result);
    }
}
