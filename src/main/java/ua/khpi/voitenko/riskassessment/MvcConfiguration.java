package ua.khpi.voitenko.riskassessment;

import org.springframework.boot.context.embedded.InitParameterConfiguringServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import ua.khpi.voitenko.riskassessment.service.RiskGroupService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {
    @Resource
    private RiskGroupService riskGroupService;

    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public View jsonViewResolver() {
        return new MappingJackson2JsonView();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InitParameterConfiguringServletContextInitializer initParamsInitializer() {
        Map<String, String> contextParams = new HashMap<>();
        riskGroupService.findAllRiskGroups()
                .stream().map(group -> "riskGroupRate_" + group.getName())
                .forEach(key -> contextParams.put(key, "1"));
        contextParams.put("assessmentLimit_excellent", "15");
        contextParams.put("assessmentLimit_good", "30");
        contextParams.put("assessmentLimit_fine", "50");
        contextParams.put("assessmentLimit_warn", "65");
        contextParams.put("assessmentLimit_critical", "80");
        contextParams.put("assessmentLimit_fail", "95");
        return new InitParameterConfiguringServletContextInitializer(contextParams);
    }
}