package com.yue.controller;


import com.yue.common.Result;
import com.yue.entity.dto.UserEditPasswordDto;
import com.yue.entity.dto.UserLoginDto;
import com.yue.entity.User;
import com.yue.excetion.ExceptionEnum;
import com.yue.excetion.PartJobException;
import com.yue.service.UserService;
import com.yue.utils.CurPool;
import com.yue.utils.VerificationCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author liuy
 * @since 2022-03-23
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("register")
    public Result register(@RequestBody User user){
        log.info("执行 UserController.register: "+user);
        userService.register(user);
        //注册成功，给用户邮箱发送邮件通知用户
        rabbitTemplate.convertAndSend("send_email",user);
        return Result.succ("注册成功");
    }

    @PostMapping("login")
    public Result login(@RequestBody UserLoginDto userDto,HttpServletRequest request){
        log.info("执行 UserController.login: "+userDto);
        //校验验证码是否正确
        /*String verify_code = (String) request.getSession().getAttribute("verify_code");
        if(null == userDto.getCode() || null == verify_code || !verify_code.toLowerCase(Locale.ROOT).equals(userDto.getCode().toLowerCase(Locale.ROOT))){
            throw new PartJobException(ExceptionEnum.USER_CODE_ERROR);
        }*/
        try{
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(userDto.getUsername(), userDto.getPassword()));
            User user = this.userService.getUserByUsername(userDto.getUsername());
            CurPool.curUserPool.put(user.getUserId(), user);
            return Result.succ("登陆成功",user);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            throw new PartJobException(ExceptionEnum.USERNAME_NOT_FOUND);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            throw new PartJobException(ExceptionEnum.PASSWORD_NOT_EXISTED);
        } catch (Exception e){
            e.printStackTrace();
            throw new PartJobException(ExceptionEnum.USERNAME_NOT_FOUND);
        }
    }

    @PostMapping("/edit")
    public Result editUser(@RequestBody User user){
        log.info("执行 UserController.editUser: "+ user);
        this.userService.editUser(user);
        return Result.succ("修改成功",user);
    }

    @PostMapping("/editPassword")
    public Result editPassword(@RequestBody UserEditPasswordDto user){
        this.userService.editPassword(user);
        return Result.succ("修改成功",user);
    }

    @GetMapping("/logout")
    public Result logout(){
        log.info("执行 UserController.logout");
        //-----BaseController
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.getUserByUsername(username);
        CurPool.curUserPool.remove(user.getUserId());
        SecurityUtils.getSubject().logout();
        return Result.succ("注销成功");
    }

    /**
     * 获取验证码图片写到响应的输出流中，保存验证码到session
     * @param response
     * @param session
     * @throws IOException
     */
    @GetMapping("/verifyCode")
    public void getVerifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        log.info("执行 UserController.getVerifyCode");
        VerificationCodeUtil code = new VerificationCodeUtil();
        BufferedImage image = code.getImage();
        String text = code.getText();
        session.setAttribute("verify_code",text);
        VerificationCodeUtil.output(image,response.getOutputStream());
    }

    /**
     * 兼职详情页面查询兼职发布着的详细信息
     * @param userId
     * @return
     */
    @GetMapping("/get/{userId}")
    public Result getUserInfo(@PathVariable String userId){
        log.info("执行 UserController.getUserInfo: userId: "+ userId);
        User user = this.userService.getById(userId);
        return Result.succ("获取成功",user);
    }

    //测试邮件功能
    /*@GetMapping("/sendMail")
    public Result testSendMail(){
        User user = new User();
        user.setNickname("wenxv");
        user.setEmail("3047200846@qq.com");
        rabbitTemplate.convertAndSend("send_email",user);
        return Result.succ("发送成功");
    }*/

}

