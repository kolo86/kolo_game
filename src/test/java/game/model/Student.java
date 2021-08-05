package game.model;

import com.alibaba.fastjson.JSON;

/**
 * 学生对象
 *
 * @author: koloc
 * @date: 2021/8/6
 **/
public class Student {
    /**
     * 唯一标识
     */
    private int id;
    /**
     * 语文
     */
    private int language;
    /**
     * 数学
     */
    private int mathematics;

    public static Student valueOf(int id, int language, int mathematics) {
        Student student = new Student();
        student.id = id;
        student.language = language;
        student.mathematics = mathematics;
        return student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public int getMathematics() {
        return mathematics;
    }

    public void setMathematics(int mathematics) {
        this.mathematics = mathematics;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
