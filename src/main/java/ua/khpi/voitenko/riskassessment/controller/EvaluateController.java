package ua.khpi.voitenko.riskassessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.khpi.voitenko.riskassessment.service.RiskService;

import javax.annotation.Resource;

@Controller
public class EvaluateController {
    @Resource
    private RiskService riskService;

    @RequestMapping("/evaluate")
    public String index() {
        riskService.findAllRisks().forEach(System.out::println);
        return "evaluate";
    }

}