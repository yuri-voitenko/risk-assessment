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

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
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
    public void getChartRateOfGroups(HttpServletResponse response) throws IOException {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        evaluationStrategy.getRateOfGroup()
                .forEach((k, v) -> dataSet.setValue(v, k, k));
        JFreeChart chart = ChartFactory.createBarChart("Rates of groups",
                "Risk groups", "Value of rate", dataSet, PlotOrientation.VERTICAL,
                false, true, false);
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setItemMargin(-4);
        ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 560, 400);
        response.getOutputStream().close();
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/impact_of_groups", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void getChartImpactOfGroups(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<FilledRisk> filledRisks = (List<FilledRisk>) request.getSession().getAttribute("filledRisks");
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        evaluationStrategy.getImpactOfGroups(filledRisks)
                .forEach((k, v) -> dataSet.setValue(v, k, k));
        JFreeChart chart = ChartFactory.createBarChart("Impacts of groups",
                "Risk groups", "Value of impact", dataSet, PlotOrientation.VERTICAL,
                false, true, false);
        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
        renderer.setItemMargin(-4);
        ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart, 560, 400);
        response.getOutputStream().close();
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

        JFreeChart jFreeChart = ChartFactory.createPieChart3D("Number of risks by group", pieDataSet, true, true, false);
        PiePlot3D plot = (PiePlot3D) jFreeChart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        ChartUtilities.writeChartAsPNG(response.getOutputStream(), jFreeChart, 560, 400);
        response.getOutputStream().close();
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/impact_of_groups_in_percentage", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void getChartImpactOfGroupsInPercentage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final List<FilledRisk> filledRisks = (List<FilledRisk>) request.getSession().getAttribute("filledRisks");
        DefaultPieDataset pieDataSet = new DefaultPieDataset();

        evaluationStrategy.getImpactOfGroups(filledRisks)
                .forEach(pieDataSet::setValue);

        JFreeChart jFreeChart = ChartFactory.createPieChart3D("Impact of groups in percentage", pieDataSet, true, true, false);
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