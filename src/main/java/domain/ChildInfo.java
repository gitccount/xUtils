package domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/*
 * 项目名:     MayProject2.2
 * 包名:       domain
 * 文件名:     ChildInfo
 * 创建者:     dell
 * 创建时间:   2016/10/22 12:14
 * 描述:       TODO
 */
@Table(name = "zhong")//onCreated ="sql语句" 表一创建会执行的sql语句
public class ChildInfo {
    //指明字段,主键,是否自增长,约束(不能为空)
    @Column(name = "id", isId = true, autoGen = true, property = "NOT NULL")
    private int id;
    @Column(name = "age")
    private int age;
    @Column(name = "name")
    private String name;

    //必须声明这个默认构造方法，否则表无法创建
    public ChildInfo() {
    }

    public ChildInfo(String name, int age) {
        this.age = age;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChildInfo{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
