package cn.libery.activeandroiddemo;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Libery on 2015/7/20.
 * Email:libery.szq@qq.com
 */
@Table(name = "Students")
public class Students extends Model {

    @Column(name = "Name")
    private String name;
    @Column(name = "Age")
    private String age;

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Column(name = "Gender")
    private String gender;

    public Students() {
    }

    public Students(String name, String age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
