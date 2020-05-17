package com.imooc.mall.exceptions;

import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.imooc.mall.enums.ResponseEnum.*;

/**
 * Greated by Guo
 *
 * @date2020/5/7 10:33
 */
@Slf4j
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVo notValidExceptionHandler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        return ResponseVo.error(PARAM_ERROR,bindingResult);
    }
}
