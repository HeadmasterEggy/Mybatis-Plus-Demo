package com.mybatis;

import com.mybatis.mapper.UserMapper;
import com.mybatis.pojo.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyBatisPlusTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        //通过条件构造器查询一个list集合，若没有条件，则可以设置null为参数
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }
}
