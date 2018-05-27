package ua.khpi.voitenko.riskassessment.controller;

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
import ua.khpi.voitenko.riskassessment.assessment.strategy.EvaluationStrategy;
import ua.khpi.voitenko.riskassessment.model.FilledRisk;
import ua.khpi.voitenko.riskassessment.model.Risk;
import ua.khpi.voitenko.riskassessment.service.RiskService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        getPlugEvaluationStrategy().getRateOfGroup()
                .forEach((k, v) -> dataSet.setValue(v, k, k));
        createBarChart(response, dataSet, "Rates of groups", "Risk groups", "Value of rate");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/impact_of_groups", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void getChartImpactOfGroups(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<FilledRisk> filledRisks = (List<FilledRisk>) request.getSession().getAttribute("filledRisks");
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        getPlugEvaluationStrategy().getImpactOfGroups(filledRisks)
                .forEach((k, v) -> dataSet.setValue(v, k, k));
        createBarChart(response, dataSet, "Impacts of groups", "Risk groups", "Value of impact");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/number_of_risks_by_group", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void getChartNumberOfRisksByGroup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<FilledRisk> filledRisks = (List<FilledRisk>) request.getSession().getAttribute("filledRisks");
        DefaultPieDataset pieDataSet = new DefaultPieDataset();

        filledRisks
                .stream()
                .collect(groupingBy(risk -> risk.getRisk().getGroup().getName(), counting()))
                .forEach(pieDataSet::setValue);

        createPieChart3D(response, pieDataSet, "Number of risks by group");
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/impact_of_groups_in_percentage", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void getChartImpactOfGroupsInPercentage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<FilledRisk> filledRisks = (List<FilledRisk>) request.getSession().getAttribute("filledRisks");
        DefaultPieDataset pieDataSet = new DefaultPieDataset();

        getPlugEvaluationStrategy().getImpactOfGroups(filledRisks)
                .forEach(pieDataSet::setValue);

        createPieChart3D(response, pieDataSet, "Impact of groups in percentage");
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