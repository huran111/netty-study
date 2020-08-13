package netty.guide.demo07;

import org.msgpack.annotation.Message;

/**
 * @program: netty-study
 * @description:
 * @author: HuRan
 * @create: 2020-08-13 15:43
 */
@Message
public class User {
    private String name;
    private int age;
    private String id;
    private String sex;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id='" + id + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}