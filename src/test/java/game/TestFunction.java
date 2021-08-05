package game;

import com.google.common.collect.Lists;
import game.model.Student;
import org.junit.Test;

import java.util.List;

/**
 * 测试功能
 *
 * @author: koloc
 * @date: 2021/8/5
 **/
public class TestFunction {

    @Test
    public void fun() {
        List<Integer> list = Lists.newArrayList();
        list.add(5);
        list.add(9);
        list.add(1);
        list.sort((n1, n2) -> Integer.compare(n2, n1));
        System.out.println(list);
    }

    @Test
    public void fun1() {
        Student student1 = Student.valueOf(1, 80, 60);
        Student student2 = Student.valueOf(6, 70, 60);
        Student student3 = Student.valueOf(3, 70, 80);

        // 在比较的时候，记住默认s1是小的，s2的大的。然后当我们需要升序的时候，返回-1。当我们需要降序的时候，返回1
        List<Student> list = Lists.newArrayList(student1, student2, student3);
        list.sort((s1, s2) -> {
            // 先比较数学，数学高的在后面
            if (s1.getMathematics() != s2.getMathematics()) {
                return Integer.compare(s1.getMathematics(), s2.getMathematics());
            }
            // 在比较语文，语文高的在前面
            if (s1.getLanguage() != s2.getLanguage()) {
                return Integer.compare(s2.getLanguage(), s1.getLanguage());
            }
            // 最好比较id，id大的在前面
            return Integer.compare(s2.getId(), s1.getId());
        });
        System.out.println(list);
    }
}
