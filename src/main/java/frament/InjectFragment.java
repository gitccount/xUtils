package frament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.xutils.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import adapter.ListViewAdapter;
import domain.ListItem;

/*
 * Created by dell on 2016/6/17.
 */
@ContentView(R.layout.inject_view)//加载布局
public class InjectFragment extends Fragment {

    private List<ListItem> list = new ArrayList<>();
    @ViewInject(R.id.list_item)
    ListView listView;//加载控件

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);//初始化
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 50; i++) {
            list.add(new ListItem("阿钟程序员" + (i + 1)));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(new ListViewAdapter(getActivity(), list));
    }

    /**
     * button的点击事件
     * 访问修饰符必须是private
     * type 默认值是View.OnClickListener.class，如果是这个可以不写
     * 参数必须与原来的一样
     *
     * @param view
     *
     */
    @Event(type = View.OnClickListener.class, value = R.id.btn)
    private void click(View view) {
        Snackbar.make(view, "点击事件", Snackbar.LENGTH_SHORT).show();
    }

    /**
     * button的点击事件
     * 访问修饰符必须是private
     * 参数必须与原来的一样
     *
     * @param view
     */
    @Event(type = View.OnLongClickListener.class, value = R.id.btn)
    private boolean Longclick(View view) {
        Snackbar.make(view, "长按事件", Snackbar.LENGTH_SHORT).show();
        return true;
    }

    /**
     * 用注解给ListView设置Item的点击事件
     * type 类型
     * value 控件id 访问修饰符必须为private
     * 参数列表必须跟正常的一模一样
     */
    @Event(type = AdapterView.OnItemClickListener.class, value = R.id.list_item)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Snackbar.make(view, list.get(position).getName(), Snackbar.LENGTH_SHORT).show();
    }
}
