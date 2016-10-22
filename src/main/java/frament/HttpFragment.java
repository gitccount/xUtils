package frament;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xutils.R;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/*
 * Created by dell on 2016/6/17.
 */
@ContentView(R.layout.http_view)
public class HttpFragment extends Fragment {
    @ViewInject(R.id.tv)
    TextView textView;
    @ViewInject(R.id.pb)
    SeekBar seekBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //GET网络请求
    @Event(R.id.get)
    private void get(View view) {
        //请求参数
        RequestParams params = new RequestParams("http://www.omghz.cn/FirstService/getString");
        //添加请求参数
//        params.addQueryStringParameter("username", "小明");
//        params.addQueryStringParameter("password", "123456");
        //Callback.CommonCallback<String>String-->请求成功后返回的数据类型
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //请求成功回调，result:返回的结果
                textView.setText("Get: " + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //请求发生异常回调
                //ex--->异常信息
                //isOnCallback--->true说明异常信息来源于回调方法(onSuccess,onCancelled,onFinished)
                //isOnCallback--->false说明不是来源于回调方法
            }

            @Override
            public void onCancelled(CancelledException cex) {
                //主动调用取消请求后回调
            }

            @Override
            public void onFinished() {
                //不管成功失败都会回调
            }
        });
//        cancelable.cancel();//将会调用onCancelled()
    }

    //POST网络请求
    @Event(R.id.post)
    private void post(View view) {
        String path = "http://www.omghz.cn/FirstService/getString";
        RequestParams params = new RequestParams(path);
        //将请求参数添加至body中
        params.addBodyParameter("username", "小明");
        //根据请求方式不同将参数添加至body或链接地址后面
        params.addBodyParameter("password", "123456");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                textView.setText("Post: " + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //其他请求方式
    @Event(R.id.other)
    private void other() {
        RequestParams params = new RequestParams("http://www.omghz.cn/FirstService/getString");
        x.http().request(HttpMethod.GET, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                textView.setText("Other： " + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //文件上传
    @Event(R.id.upload)
    private void upLoad(View view) {
        //上传文件路径
        String path = Environment.getExternalStorageDirectory() + "/1.docx";
        //上传到的地址
        String url = "http://www.omghz.cn/FirstService/FileReceive";
        RequestParams params = new RequestParams(url);
        //使用Multipart表单上传
        //params.setMultipart(true);
        params.addHeader("FileName", "1.docx");
        File file = new File(path);
        params.addBodyParameter("File", file);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //下载文件
    @Event(R.id.download)
    private void downLoad(View view) {
        String url = "http://www.omghz.cn/FirstService/File/SwipeBack.apk";
        RequestParams params = new RequestParams(url);
        //设置保存路径
        params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/zsy/");
        //设置自动找寻文件名字
        params.setAutoRename(true);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Intent intent = new Intent();
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                getActivity().startActivity(intent);
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                seekBar.setMax((int) total);
                seekBar.setProgress((int) current);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {
                //网络请求之前被调用，最先被调用

            }

            @Override
            public void onStarted() {
                //网络请求开始的时候回调
            }

        });
    }

    @Event(R.id.cache)
    private void cache(View view) {
        String url = "http://www.omghz.cn/FirstService/getString";
        RequestParams params = new RequestParams(url);
        //添加缓存时间-->单位ms
        params.setCacheMaxAge(1000 * 60);
        x.http().get(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                textView.setText("Cache: " + result);
            }

            @Override
            public boolean onCache(String result) {
                //result--->缓存内容
                //如果返回true则相信本地缓存,在60s之内再次调用get请求,onSuccess返回为空
                //如果返回false则不相信本地缓存，在60s之内再次调用get请求,onSuccess有返回值
                return false;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

        });
    }
}
