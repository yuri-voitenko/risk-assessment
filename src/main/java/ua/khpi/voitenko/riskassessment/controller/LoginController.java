package ua.khpi.voitenko.riskassessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.khpi.voitenko.riskassessment.assessment.strategy.EvaluationStrategy;
import ua.khpi.voitenko.riskassessment.model.User;
import ua.khpi.voitenko.riskassessment.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Resource
    private UserService userService;
    @Resource
    private List<EvaluationStrategy> evaluationStrategies;

    @RequestMapping("/")
    public String viewLoginPage(Model model) {
        model.addAttribute("userDTO", new User());
        return "login";
    }

    @RequestMapping(value = "/process/", method = RequestMethod.POST)
    public String login(@ModelAttribute("userDTO") User userDTO, ModelMap modelMap, HttpServletRequest req) {
        final User user = userService.login(userDTO.getEmail(), userDTO.getPassword());
        if (nonNull(user)) {
            evaluationStrategies
                    .forEach(strategy ->
                            req.getSession().setAttribute("isInvalidatedCacheFor" + strategy.getClass().getSimpleName(), true));
            req.getSession().setAttribute("currentUser", user);
            return "index";
        } else {
            modelMap.put("errorMessages", Collections.singletonList("Invalid credentials"));
            return "login";
        }
    }
}