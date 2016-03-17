package com.shouyingbao.pbs.web.controller;

import com.shouyingbao.pbs.constants.ConstantEnum;
import com.shouyingbao.pbs.core.bean.ResponseData;
import com.shouyingbao.pbs.entity.User;
import com.shouyingbao.pbs.service.AuthorityService;
import com.shouyingbao.pbs.service.UserService;
import com.shouyingbao.pbs.vo.UserParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * kejun
 * 2016/3/14 16:22
 **/
@Controller
@RequestMapping("/auth")
public class LoginController{

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    AuthorityService authorityService;

    @Autowired
    UserService userService;
    /**
     * 指向登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(
            @RequestParam(value = "error", required = false) boolean error,
            ModelMap model,HttpServletRequest request) {
        if (error == true) {
            model.put("error", ConstantEnum.ERROR_LOGIN.getValueStr());
        } else {
            model.put("error", "");
        }
        return "login";

    }

    /**
     * 指定无访问额权限页面
     *
     * @return
     */
    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String test() {
        LOGGER.debug("Received request to show denied page");
        return "denied";

    }

    @RequestMapping(value = "/mobile/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData mobileLogin(HttpServletRequest request,@RequestBody UserParam userParam) {
        ResponseData responseData;
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        userParam.setMd5Pwd(md5.encodePassword(userParam.getUserPwd(), null));
        User user = userService.selectByUserAccountAndPwd(userParam.getUserAccount(),userParam.getMd5Pwd());
        if(user != null){
            user.setUserPwd(null);
            responseData = ResponseData.success(user);
        }else {
            responseData = ResponseData.failure(ConstantEnum.ERROR_LOGIN.getCodeStr(),ConstantEnum.ERROR_LOGIN.getValueStr());
        }
        return responseData;
    }


    /**
     * 检查退款权限
     *
     * @return
     */
    @RequestMapping(value = "/refundPermission", method = RequestMethod.POST)
    @ResponseBody
   public ResponseData refundPermission(@RequestBody UserParam userParam) {
        LOGGER.info("refundPermission:userId={}", userParam.getUserId());
        boolean result;
        try {
            result = authorityService.checkAuthority(ConstantEnum.AUTHORITY_MCH_SHOPKEEPER.getCodeStr(), userParam.getUserId());
            if(result){
                return ResponseData.success(result);
            }else{
                return ResponseData.failure(ConstantEnum.NO_REFUND_AUTHORITY.getCodeStr(),ConstantEnum.NO_REFUND_AUTHORITY.getValueStr());
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return ResponseData.failure(ConstantEnum.EXCEPTION_REFUND_AUTHORITY.getCodeStr(),ConstantEnum.EXCEPTION_REFUND_AUTHORITY.getValueStr());
        }
    }
}