package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xutils.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import domain.ListItem;

/*
 * Created by dell on 2016/6/17.
 */
public class ListViewAdapter extends BaseAdapter {
    private List<ListItem> listItems;
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, List<ListItem> listItems) {
        this.listItems = listItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;//ViewHolder注解
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
            viewHolder = new ViewHolder();
            x.view().inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.childName.setText(listItems.get(position).getName());
        return convertView;
    }

    private class ViewHolder {
        @ViewInject(R.id.name)
        TextView childName;
    }
}
