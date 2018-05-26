package ua.khpi.voitenko.riskassessment.controller;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.khpi.voitenko.riskassessment.assessment.strategy.impl.AbstractEvaluationStrategy;
import ua.khpi.voitenko.riskassessment.model.FilledRisk;
import ua.khpi.voitenko.riskassessment.model.Risk;
import ua.khpi.voitenko.riskassessment.service.RiskService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/evaluate")
public class EvaluateController {
    @Resource
    private RiskService riskService;
    @Resource
    private AbstractEvaluationStrategy evaluationStrategy;

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
        request.getSession().setAttribute("filledRisks", filledRisks);
        return "assessment";
    }

    @RequestMapping(value = "/rate_of_groups", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void processEvaluate(HttpServletResponse response) throws IOException {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        evaluationStrategy.getRateOfGroup()
                .forEach((k, v) -> dataSet.setValue(v, k, k));
        JFreeChart chart = ChartFactory.createBarChart("Rates of groups",
                "Risk groups", "Value of rate", dataSet, PlotOrientation.VERTICAL,
                false, true, false);
        ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 550, 400);
        response.getOutputStream().close();
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/impact_of_groups", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void processEvaluate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<FilledRisk> filledRisks = (List<FilledRisk>) request.getSession().getAttribute("filledRisks");
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        evaluationStrategy.getImpactOfGroups(filledRisks)
                .forEach((k, v) -> dataSet.setValue(v, k, k));
        JFreeChart chart = ChartFactory.createBarChart("Impacts of groups",
                "Risk groups", "Value of impact", dataSet, PlotOrientation.VERTICAL,
                false, true, false);
        ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 550, 400);
        response.getOutputStream().close();
    }

//    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
//    public void processEvaluate(HttpServletResponse response) {
//        PieDataset pieDataset = createDataset();
//        JFreeChart jFreeChart = createChart(pieDataset, "Name");
//
//        try {
//            ChartUtilities.writeChartAsPNG(response.getOutputStream(), jFreeChart,750,400);
//            response.getOutputStream().close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private JFreeChart createChart(PieDataset pieDataset, String name) {
//        JFreeChart jFreeChart = ChartFactory.createPieChart3D(name, pieDataset, true, true, false);
//        PiePlot3D plot = (PiePlot3D) jFreeChart.getPlot();
//        plot.setStartAngle(290);
//        plot.setDirection(Rotation.CLOCKWISE);
//        plot.setForegroundAlpha(0.5f);
//        return jFreeChart;
//    }
//
//    private PieDataset createDataset() {
//
//        DefaultPieDataset dataset = new DefaultPieDataset();
//        dataset.setValue("80-100", 120);
//        dataset.setValue("60-79", 80);
//        dataset.setValue("40-59", 20);
//        dataset.setValue("20-39", 7);
//        dataset.setValue("0-19", 3);
//        return dataset;
//    }

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