package com.mybatis;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.mapper.ProductMapper;
import com.mybatis.mapper.UserMapper;
import com.mybatis.pojo.Product;
import com.mybatis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyBatisPlusPluginsTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * SELECT COUNT(*) AS total FROM t_user WHERE is_deleted = 0 LIMIT ?,?
     */
    @Test
    public void testPage() {
        //new Page()中的两个参数分别是当前页码，每页显示数量
        Page<User> page = userMapper.selectPage(new Page<>(2, 2), null);
        List<User> users = page.getRecords();
        users.forEach(System.out::println);
    }

    @Test
    public void testPageVo() {
        Page<User> page = userMapper.selectPageVo(new Page<User>(1, 2), 20);
        List<User> users = page.getRecords();
        users.forEach(System.out::println);
    }

    @Test
    public void testProduct01() {
        //1.小李获取商品价格
        Product productLi = productMapper.selectById(1);
        System.out.println("小李获取的商品价格为：" + productLi.getPrice());

        //2.小王获取商品价格
        Product productWang = productMapper.selectById(1);
        System.out.println("小李获取的商品价格为：" + productWang.getPrice());

        //3.小李修改商品价格+50
        productLi.setPrice(productLi.getPrice() + 50);
        productMapper.updateById(productLi);

        //4.小王修改商品价格-30
        productWang.setPrice(productWang.getPrice() - 30);
        int result = productMapper.updateById(productWang);
        if (result == 0) {
            //操作失败，重试
            Product productNew = productMapper.selectById(1);
            productNew.setPrice(productNew.getPrice() - 30);
            productMapper.updateById(productNew);
        }

        //5.老板查询商品价格
        Product productBoss = productMapper.selectById(1);
        System.out.println("老板获取的商品价格为：" + productBoss.getPrice());

    }
}
