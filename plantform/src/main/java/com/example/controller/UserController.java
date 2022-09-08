package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.R;
import com.example.domain.User;
import com.example.service.UserService;
import com.example.utils.SMSUtils;
import com.example.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user,HttpSession httpSession){
        // log.error(user.toString());
        String phone = user.getPhone();
        if (StringUtils.isNoneEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            code = "1234";
            // SMSUtils.sendMessage("外卖","",phone,code);
            httpSession.setAttribute(phone,code);
    
            return R.success("短信发送成功");
        }
        return R.error("手机号不存在或者系统错误");
    }
    
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession httpSession){
        // log.error(user.toString());
        String phone = map.get("phone").toString();
        String code  = map.get("code").toString();
    
        Object codeInSession = httpSession.getAttribute(phone);
        if (codeInSession != null && codeInSession.equals(code)){
            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getPhone, phone);
            User user = userService.getOne(lqw);
            if (user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
                user = userService.getOne(lqw);
            }
            httpSession.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("验证码错误");
    }
}
























