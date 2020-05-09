package com.imooc.mall.service.inpl;


import com.imooc.mall.MallApplicationTests;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.enums.RoleEnum;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserServiceImplTest extends MallApplicationTests {

    @Autowired
    private IUserService userService;

    private static final String USERNAME = "jack";
    private static final String PASSWORD = "123456";

    @Test
    public void register() {
        User user = new User(USERNAME,PASSWORD,"guotingfang@163.com", RoleEnum.CUSTOMER.getCode());
        userService.register(user);
    }

    @Test
    public void login(){
        ResponseVo<User> login = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), login.getStatus());
    }
}