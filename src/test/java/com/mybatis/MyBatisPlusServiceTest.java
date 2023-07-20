package com.mybatis;

import com.mybatis.pojo.User;
import com.mybatis.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MyBatisPlusServiceTest {

    @Autowired
    private UserService userService;

    /**
     * 查询总记录数
     * SELECT COUNT( * ) AS total FROM user
     */
    @Test
    public void testGetCount() {
        long count = userService.count();
        System.out.println("总记录数：" + count);
    }

    /**
     * 批量插入数据
     * INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
     */
    @Test
    public void testInsertMore() {
        List<User> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setName("qy" + i);
            user.setAge(19 + i);
            list.add(user);
        }
        boolean b = userService.saveBatch(list);
        System.out.println(b ? "添加成功！" : "添加失败！");
    }
}
