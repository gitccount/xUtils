package frament;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xutils.R;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

/*
 * Created by dell on 2016/6/17.
 */
@ContentView(R.layout.image_view)
public class ImageFragment extends Fragment {
    @ViewInject(R.id.image01)
    ImageView imageView01;
    @ViewInject(R.id.image02)
    ImageView imageView02;
    @ViewInject(R.id.image03)
    ImageView imageView03;
    @ViewInject(R.id.image04)
    ImageView imageView04;
    @ViewInject(R.id.image05)
    ImageView imageView05;
    private String[] url = {
            "http://www.photophoto.cn/m65/127/004/1270040015.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3490836602,2778212280&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=1606799301,1002054715&fm=21&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1906367822,2393211191&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3238389602,2656067550&fm=21&gp=0.jpg"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        download();
    }

    private void download() {
        ImageOptions options = new ImageOptions.Builder()
                .setConfig(Bitmap.Config.RGB_565)//设置图片质量,这个是默认的
                .setSquare(true)
                .setCrop(true)//设置图片大小
                .setSize(200, 200)//设置图片大小
                .setFadeIn(true)//淡入效果
                .setCircular(true)//展示为圆形
                .build();
        x.image().bind(imageView01, url[0]);
        x.image().bind(imageView02, url[1], options);
        x.image().bind(imageView03, url[2], new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {

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
        x.image().bind(imageView04, url[3], options, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
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
        x.image().loadDrawable(url[4], options, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                //加载成功回调
                imageView05.setImageDrawable(result);
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
        x.image().loadFile(url[4], options, new Callback.CacheCallback<File>() {
            @Override
            public boolean onCache(File result) {
                //true相信本地缓存，第二次加载图片将不会请求网络同时onSuccess返回为空
                return true;
            }

            @Override
            public void onSuccess(File result) {

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
