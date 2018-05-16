package ua.khpi.voitenko.riskassessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.khpi.voitenko.riskassessment.model.Risk;
import ua.khpi.voitenko.riskassessment.service.RiskService;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class EvaluateController {
    @Resource
    private RiskService riskService;

    @RequestMapping("/evaluate")
    public String evaluate(ModelMap map) {
        List<Risk> allRisks = riskService.findAllRisks();
        map.put("risks", allRisks);
        map.put("message", "DELETED");
        allRisks.forEach(System.out::println);
        return "evaluate";
    }

}