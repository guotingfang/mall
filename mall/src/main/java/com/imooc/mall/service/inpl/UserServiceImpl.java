package com.imooc.mall.service.inpl;

import com.imooc.mall.dao.UserMapper;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.IUserService;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.imooc.mall.enums.ResponseEnum.*;
import static com.imooc.mall.enums.RoleEnum.CUSTOMER;

/**
 * Greated by Guo
 *
 * @date2020/5/5 10:19
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 注册
     *
     * @param user
     */
    @Override
    public ResponseVo<User> register(User user) {
        //username 不能重复
        int contByUsername = userMapper.contByUsername(user.getUsername());
        if (contByUsername > 0) {
            return ResponseVo.error(USERNAME_EXIST);
        }
        //email不能重复
        int contByEmail = userMapper.contByEmail(user.getEmail());
        if (contByEmail > 0) {
            return ResponseVo.error(EMAIL_EXIST);
        }
        user.setRole(CUSTOMER.getCode());
        //写入数据库之前对密码进行MD5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        //写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount ==  0){
            return ResponseVo.error(ERROR);
        }
        return ResponseVo.success();
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null){
            //返回用户名或密码错误
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        //判断密码是否正确
        if (!user.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8))
        )){
            //返回用户名或密码错误
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        user.setPassword("");
        return ResponseVo.success(user);
    }


}
