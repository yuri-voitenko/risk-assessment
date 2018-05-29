package ua.khpi.voitenko.riskassessment.controller;

import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import ua.khpi.voitenko.riskassessment.assessment.strategy.EvaluationStrategy;
import ua.khpi.voitenko.riskassessment.model.FilledRisk;
import ua.khpi.voitenko.riskassessment.model.Risk;
import ua.khpi.voitenko.riskassessment.service.RiskService;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/evaluate")
public class EvaluateController {
    @Resource
    private RiskService riskService;
    @Resource
    private List<EvaluationStrategy> evaluationStrategies;
    @Resource
    private ServletContext context;

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
    public void getChartRateOfGroups(HttpServletResponse response) throws IOException {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        getPlugEvaluationStrategy().getRiskGroupRates()
                .forEach(rgp -> {
                    final String groupName = rgp.getRiskGroup().getName();
                    dataSet.setValue(rgp.getRate(), groupName, groupName);
                });
        createBarChart(response, dataSet, "Rates of groups", "Risk groups", "Value of rate");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/impact_of_groups", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void getChartImpactOfGroups(@SessionAttribute List<FilledRisk> filledRisks, HttpServletResponse response) throws IOException {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        getPlugEvaluationStrategy().getImpactOfGroups(filledRisks)
                .forEach((k, v) -> dataSet.setValue(v, k, k));
        createBarChart(response, dataSet, "Impacts of groups", "Risk groups", "Value of impact");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/number_of_risks_by_group", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void getChartNumberOfRisksByGroup(@SessionAttribute List<FilledRisk> filledRisks, HttpServletResponse response) throws IOException {
        DefaultPieDataset pieDataSet = new DefaultPieDataset();

        filledRisks
                .stream()
                .collect(groupingBy(risk -> risk.getRisk().getGroup().getName(), counting()))
                .forEach(pieDataSet::setValue);

        createPieChart3D(response, pieDataSet, "Number of risks by group");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/impact_of_groups_in_percentage", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void getChartImpactOfGroupsInPercentage(@SessionAttribute List<FilledRisk> filledRisks, HttpServletResponse response) throws IOException {
        DefaultPieDataset pieDataSet = new DefaultPieDataset();

        getPlugEvaluationStrategy().getImpactOfGroups(filledRisks)
                .forEach(pieDataSet::setValue);

        createPieChart3D(response, pieDataSet, "Impact of groups in percentage");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/assessment_result", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void getChartAssessmentResult(@RequestParam String strategy, @SessionAttribute List<FilledRisk> filledRisks, HttpServletResponse response) throws IOException {
        int strategyIndex = Integer.parseInt(strategy);
        EvaluationStrategy evaluationStrategy = evaluationStrategies.get(strategyIndex);

        final BigDecimal commonImpact = evaluationStrategy.getOverAllImpact(filledRisks);
        final BigDecimal maxImpact = evaluationStrategy.getOverMaxAllImpact(filledRisks);

        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        dataSet.setValue(getLimitInPercent(maxImpact, getAssessmentLimitValue("assessmentLimit_excellent"), 0), "excellent", "scale");
        dataSet.setValue(getLimitInPercent(maxImpact, getAssessmentLimitValue("assessmentLimit_good"), getAssessmentLimitValue("assessmentLimit_excellent")), "good", "scale");
        dataSet.setValue(getLimitInPercent(maxImpact, getAssessmentLimitValue("assessmentLimit_fine"), getAssessmentLimitValue("assessmentLimit_good")), "fine", "scale");
        dataSet.setValue(getLimitInPercent(maxImpact, getAssessmentLimitValue("assessmentLimit_warn"), getAssessmentLimitValue("assessmentLimit_fine")), "warn", "scale");
        dataSet.setValue(getLimitInPercent(maxImpact, getAssessmentLimitValue("assessmentLimit_critical"), getAssessmentLimitValue("assessmentLimit_warn")), "critical", "scale");
        dataSet.setValue(getLimitInPercent(maxImpact, 100, getAssessmentLimitValue("assessmentLimit_critical")), "fail", "scale");

        dataSet.setValue(commonImpact, "current result", "current result");

        JFreeChart chart = ChartFactory.createStackedBarChart(
                "Assessment result", StringUtils.EMPTY, StringUtils.EMPTY,
                dataSet, PlotOrientation.HORIZONTAL, true, true, false);

        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 34, 2));
        renderer.setSeriesPaint(1, new Color(0, 54, 4));
        renderer.setSeriesPaint(2, new Color(137, 255, 77));
        renderer.setSeriesPaint(3, new Color(255, 255, 0));
        renderer.setSeriesPaint(4, new Color(255, 165, 0));
        renderer.setSeriesPaint(5, Color.red);

        ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 1120, 200);
        response.getOutputStream().close();
    }

    private BigDecimal getLimitInPercent(BigDecimal maxImpact, int currentLimit, int previousLimit) {
        int delta = currentLimit - previousLimit;
        return maxImpact.multiply(BigDecimal.valueOf(delta)).divide(BigDecimal.valueOf(100), 3, BigDecimal.ROUND_HALF_UP);
    }

    private int getAssessmentLimitValue(String key) {
        return Integer.parseInt(context.getInitParameter(key));
    }

    private EvaluationStrategy getPlugEvaluationStrategy() {
        return evaluationStrategies.get(0);
    }

    private void createBarChart(HttpServletResponse response, DefaultCategoryDataset dataSet, String title, String x, String y) throws IOException {
        JFreeChart chart = ChartFactory.createBarChart(title,
                x, y, dataSet, PlotOrientation.VERTICAL,
                false, true, false);
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setItemMargin(-4);
        ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 560, 400);
        response.getOutputStream().close();
    }

    private void createPieChart3D(HttpServletResponse response, DefaultPieDataset pieDataSet, String title) throws IOException {
        JFreeChart jFreeChart = ChartFactory.createPieChart3D(title, pieDataSet, true, true, false);
        PiePlot3D plot = (PiePlot3D) jFreeChart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        ChartUtilities.writeChartAsPNG(response.getOutputStream(), jFreeChart, 560, 400);
        response.getOutputStream().close();
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