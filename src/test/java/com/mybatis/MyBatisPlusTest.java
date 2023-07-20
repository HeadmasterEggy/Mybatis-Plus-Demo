package com.mybatis;

import com.mybatis.mapper.UserMapper;
import com.mybatis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        //通过条件构造器查询一个list集合，若没有条件，则可以设置null为参数
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

    /**
     * 测试插入一条数据
     * MyBatis-Plus在实现插入数据时，会默认基于雪花算法的策略生成id
     * INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
     */
    @Test
    public void testInsert() {
        User user = new User();
        user.setName("qy");
        user.setAge(19);
        user.setEmail("headmastereggy@gmail.com");
        int result = userMapper.insert(user);
        System.out.println(result > 0 ? "添加成功！" : "添加失败！");
        System.out.println("受影响的行数为：" + result);
        //1527206783590903810（当前 id 为雪花算法自动生成的id）
        System.out.println("id自动获取" + user.getId());
    }

    /**
     * 测试根据id删除一条数据
     * DELETE FROM user WHERE id=?
     */
    @Test
    public void testDeleteById() {
        int result = userMapper.deleteById(1681492487220252674L);
        System.out.println(result > 0 ? "删除成功！" : "删除失败！");
        System.out.println("受影响的行数为：" + result);
    }

    /**
     * 测试根据Map集合中所设置的条件删除数据
     * DELETE FROM user WHERE name = ? AND age = ?
     */
    @Test
    public void testDeleteByMap() {
        //当前演示为根据name和age删除数据
        Map<String, Object> map = new HashMap<>();
        map.put("name", "qy");
        map.put("age", 19);
        int result = userMapper.deleteByMap(map);
        System.out.println(result > 0 ? "删除成功！" : "删除失败！");
        System.out.println("受影响的行数为：" + result);
    }

    /**
     * 测试通过id批量删除数据
     * DELETE FROM user WHERE id IN ( ? , ? , ? )
     */
    @Test
    public void testDeleteBatchIds() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        int result = userMapper.deleteBatchIds(ids);
        System.out.println(result > 0 ? "删除成功！" : "删除失败！");
        System.out.println("受影响的行数为：" + result);
    }

    /**
     * 测试根据id修改用户信息
     * UPDATE user SET name=?, age=?, email=? WHERE id=?
     */
    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(4L);
        user.setName("qy");
        user.setAge(19);
        user.setEmail("qy@gmail.com");
        int result = userMapper.updateById(user);
        System.out.println(result > 0 ? "修改成功！" : "修改失败！");
        System.out.println("受影响的行数为：" + result);
    }

    /**
     * 测试根据id查询用户数据
     * SELECT id,name,age,email FROM user WHERE id=?
     */
    @Test
    public void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    /**
     * 根据多个id查询用户数据
     * SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
     */
    @Test
    public void testSelectBatchIds() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<User> users = userMapper.selectBatchIds(ids);
        users.forEach(System.out::println);
    }

    /**
     * 根据Map所设置的条件查询用户
     * SELECT id,name,age,email FROM user WHERE age = ?
     */
    @Test
    public void testSelectByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }


}
