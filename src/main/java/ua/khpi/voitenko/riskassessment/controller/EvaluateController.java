package ua.khpi.voitenko.riskassessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.khpi.voitenko.riskassessment.model.FilledRisk;
import ua.khpi.voitenko.riskassessment.model.Risk;
import ua.khpi.voitenko.riskassessment.service.RiskService;

import javax.annotation.Resource;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
public class EvaluateController {
    @Resource
    private RiskService riskService;

    @RequestMapping("/evaluate")
    public String evaluate(ModelMap map) {
        List<Risk> allRisks = riskService.findAllRisks();
        map.put("allRisks", allRisks);
        return "evaluate";
    }

}