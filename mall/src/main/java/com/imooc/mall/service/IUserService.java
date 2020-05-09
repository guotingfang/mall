package com.imooc.mall.service;


import com.imooc.mall.pojo.User;
import com.imooc.mall.vo.ResponseVo;

/**
 * Greated by Guo
 *
 * @date2020/5/5 10:05
 */

public interface IUserService {

    /**
     * 注册
     */
    ResponseVo<User> register(User user);

    /**
     * 登录
     */
    ResponseVo<User>  login(String username, String password);
}
