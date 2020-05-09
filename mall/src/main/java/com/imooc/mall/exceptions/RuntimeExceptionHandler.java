package com.imooc.mall.exceptions;

import com.imooc.mall.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.imooc.mall.enums.ResponseEnum.ERROR;
import static com.imooc.mall.enums.ResponseEnum.NEED_LOGIN;

/**
 * Greated by Guo
 *
 * @date2020/5/7 10:33
 */

@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    //@ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseVo handler(RuntimeException e){
        return ResponseVo.error(ERROR,e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo userLoginHandler(){
        return ResponseVo.error(NEED_LOGIN);
    }


}
