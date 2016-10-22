package frament;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xutils.R;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import domain.ChildInfo;

/**
 * 数据库模块
 * <p>
 * 1.创建数据库
 * 2.删除数据库
 * 3.创建一张表
 * 4.删除表
 * 5.往数据库表中插入一条数据
 * 6.修改表中的一条数据
 * 7.查询表中的数据
 * 8.删除表中的数据
 */
@ContentView(R.layout.db_view)
public class DbFragment extends Fragment {
    @ViewInject(R.id.tv)
    TextView textView;

    private DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("azhong.db")//设置数据库的名字
            .setTableCreateListener(new DbManager.TableCreateListener() {//创建表的监听
                @Override
                public void onTableCreated(DbManager db, TableEntity<?> table) {
                    Log.d("TAG", table.getName());
                }
            })
            .setAllowTransaction(true)//设置是否允许事务，默认是true
            .setDbDir(new File(Environment.getExternalStorageDirectory() + "/zsy"))//设置数据库路径，默认是data/data/包名/database
            .setDbOpenListener(new DbManager.DbOpenListener() {//设置数据库打开的监听
                @Override
                public void onDbOpened(DbManager db) {
                    db.getDatabase().enableWriteAheadLogging();//开启多线程操作
                }
            });
//          .setDbUpgradeListener()//设置数据库更新的监听
//          .setDbVersion(1);//设置数据库的版本

    private DbManager dbManager = x.getDb(daoConfig);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    /**
     * 创建数据库,创建了表，插入了一条数据
     * 再次点击，只会插入数据不会在创建数据库 表
     */
    @Event(R.id.create_db)
    private void createDb(View view) throws DbException {
        List<ChildInfo> childInfo = new ArrayList<>();
        childInfo.add(new ChildInfo("阿钟0", 18));
        childInfo.add(new ChildInfo("阿钟1", 19));
        childInfo.add(new ChildInfo("阿钟2", 20));
        childInfo.add(new ChildInfo("阿钟3", 21));
        childInfo.add(new ChildInfo("阿钟4", 22));
        childInfo.add(new ChildInfo("阿钟5", 23));
        childInfo.add(new ChildInfo("阿钟6", 24));
        childInfo.add(new ChildInfo("阿钟7", 25));
        dbManager.save(childInfo);//创建表和数据库
    }

    //删除数据库
    @Event(R.id.delete_db)
    private void deleteDb(View view) throws DbException {
        dbManager.dropDb();
    }

    //删除表
    @Event(R.id.delete_table)
    private void deleteTable(View view) throws DbException {
        dbManager.dropTable(ChildInfo.class);
    }

    /**
     * 查询表
     */
    @Event(R.id.select_table)
    private void selectTable(View view) throws DbException {
        //从数据库中查询第一条数据
//        ChildInfo first = dbManager.findFirst(ChildInfo.class);
//        dbManager.findAll()//从数据库中查询所有数据
//        textView.setText(first.toString());

        //加条件查询  25>age>22
        WhereBuilder b = WhereBuilder.b();
        b.and("age", ">", 22);//条件
        b.and("age", "<", 25);
        List<ChildInfo> all = dbManager.selector(ChildInfo.class).where(b).findAll();//查询
        StringBuilder s = new StringBuilder();
        for (ChildInfo info : all) {
            s.append(info.toString());
        }
        textView.setText(s);
    }

    /**
     * 更新数据
     *
     * @param view
     * @throws DbException
     */
    @Event(R.id.update_table)
    private void updateTable(View view) throws DbException {
        //第一种
        ChildInfo first = dbManager.findFirst(ChildInfo.class);//修改了第一条数据
//        first.setName("老王");
//        first.setAge(30);
//        dbManager.update(first, "name", "age");
//        ChildInfo f = dbManager.findFirst(ChildInfo.class);
//        textView.setText(f.toString());
        //第二种
        WhereBuilder b = WhereBuilder.b();
        b.and("id", "=", first.getId());//条件
        KeyValue age = new KeyValue("age", "100");
        KeyValue name = new KeyValue("name", "老李");
        dbManager.update(ChildInfo.class, b, age, name);
        //第三种
        ChildInfo fi = dbManager.findFirst(ChildInfo.class);
        fi.setAge(12);
        fi.setName("隔壁老王");
        dbManager.save(fi);
    }

    //删除数据
    @Event(R.id.delete_data)
    private void deleteData(View view) throws DbException {
        dbManager.delete(ChildInfo.class);//删除所有数据

        WhereBuilder b = WhereBuilder.b();
        dbManager.delete(ChildInfo.class, b);//添加条件删除
    }
}
