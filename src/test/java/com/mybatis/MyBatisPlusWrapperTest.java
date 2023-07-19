package com.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mybatis.mapper.UserMapper;
import com.mybatis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询用户名包含a，年龄在20到30之间，邮箱信息不为null的用户信息
     * SELECT id,name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
     */
    @Test
    public void test01() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "a").between("age", 20, 30).isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 查询用户信息，按照年龄的降序排序，若年龄相同，则按照id升序排序
     * SELECT id,name,age,email,is_deleted FROM t_user WHERE is_deleted=0 ORDER BY age DESC,id ASC
     */
    @Test
    public void test02() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age").orderByAsc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    /**
     * 删除邮箱地址为null的用户信息
     * UPDATE t_user SET is_deleted=1 WHERE is_deleted=0 AND (email IS NULL)
     */
    @Test
    public void test03() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println(result > 0 ? "删除成功！" : "删除失败！");
        System.out.println("受影响的行数为：" + result);
    }

    /**
     * 将（年龄大于20并且用户名中包含有a）或邮箱为null的用户信息修改
     * UPDATE t_user SET name=?, email=? WHERE is_deleted=0 AND (age > ? AND name LIKE ? OR email IS NULL)
     */
    @Test
    public void test04() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.gt("age", 20).like("name", "a").or().isNull("email");
        User user = new User();
        user.setName("Eggy");
        user.setEmail("test@qy.com");
        int result = userMapper.update(user, updateWrapper);
        System.out.println(result > 0 ? "修改成功！" : "修改失败！");
        System.out.println("受影响的行数为：" + result);
    }

    /**
     * 将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
     * UPDATE t_user SET name=?, email=? WHERE is_deleted=0 AND (name LIKE ? AND (age > ? OR email IS NULL))
     */
    @Test
    public void test05() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("name", "a").and(i -> i.gt("age", 20).or().isNull("email"));
        User user = new User();
        user.setName("Joeyyyy");
        user.setEmail("test@joeyyyy.com");

        int result = userMapper.update(user, updateWrapper);
        System.out.println(result > 0 ? "修改成功！" : "修改失败！");
        System.out.println("受影响的行数为：" + result);
    }

    /**
     * 查询用户的用户名、年龄、邮箱信息
     */
    @Test
    public void test06() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "age", "email");
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }
}
