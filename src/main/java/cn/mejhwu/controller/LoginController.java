package cn.mejhwu.controller;

import cn.mejhwu.model.LoginTicketDO;
import cn.mejhwu.service.LoginTicketService;
import cn.mejhwu.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.standard.InstantFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Author: mejhwu
 * Email:  mejhwu@163.com
 * Date:   2017/5/19
 * Time:   19:25
 * Description:
 */
@Controller
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model,
                           @RequestParam String password,
                           @RequestParam String userName,
                           HttpServletResponse response) {
        Map<String, String> map = userService.register(userName, password);

        logger.info("userName: " + userName + ", password: " + password);

        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket"));
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
    }

    @RequestMapping(path = "/loginPage", method = RequestMethod.GET)
    public String loginPage(Model model,
                            @RequestParam(name = "next", required = false) String next) {
        model.addAttribute("next", next);
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String register(Model model,String password, String userName,
                           @RequestParam(name = "next", required = false) String next,
                           @RequestParam(defaultValue = "false") boolean rememberMe,
                           HttpServletResponse response) {
        Map<String, String> map = userService.login(userName, password);

        logger.info("userName: " + userName + ", password: " + password); //TODO

        //登陆成功
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket"));
            cookie.setPath("/");
            response.addCookie(cookie);
            if (!StringUtils.isEmpty(next)) {
                return "redirect:" + next;
            }
            return "redirect:/";
        }
        model.addAttribute("msg", map.get("msg"));
        return "login";
    }


    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }


}
