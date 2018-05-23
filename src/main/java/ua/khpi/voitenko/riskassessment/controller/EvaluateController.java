package ua.khpi.voitenko.riskassessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.khpi.voitenko.riskassessment.model.FilledRisk;
import ua.khpi.voitenko.riskassessment.model.Risk;
import ua.khpi.voitenko.riskassessment.service.RiskService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/evaluate")
public class EvaluateController {
    @Resource
    private RiskService riskService;

    @RequestMapping("/")
    public String evaluate(ModelMap map) {
        List<Risk> allRisks = riskService.findAllRisks();
        map.put("allRisks", allRisks);
        return "evaluate";
    }

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public String processEvaluate(HttpServletRequest request) {
        final List<FilledRisk> filledRisks = getFilledRisks();

        final Map<String, String[]> parameterMap = request.getParameterMap();
        filledRisks.forEach(filledRisk -> {
            final int id = filledRisk.getRisk().getId();
            filledRisk.setProbability(getValueByParameterKey(parameterMap, "probability", id));
            filledRisk.setDamage(getValueByParameterKey(parameterMap, "damage", id));
        });

        return "assessment";
    }

    private List<FilledRisk> getFilledRisks() {
        List<Risk> allRisks = riskService.findAllRisks();
        return allRisks.stream().map(risk -> {
            final FilledRisk filledRisk = new FilledRisk();
            filledRisk.setRisk(risk);
            return filledRisk;
        }).collect(toList());
    }

    private int getValueByParameterKey(Map<String, String[]> parameterMap, String key, int id) {
        return Integer.parseInt(parameterMap.get(key + id)[0]);
    }
}