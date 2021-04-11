package collectioner.config;

import collectioner.service.impl.TrainingServiceInterceptor;
import collectioner.service.impl.WorkServiceInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class ProductServiceInterceptorAppConfig extends WebMvcConfigurerAdapter {

    WorkServiceInterceptor workServiceInterceptor;
    private final TrainingServiceInterceptor trainingServiceInterceptor;

    public ProductServiceInterceptorAppConfig(WorkServiceInterceptor workServiceInterceptor, TrainingServiceInterceptor trainingServiceInterceptor) {
        this.workServiceInterceptor = workServiceInterceptor;
        this.trainingServiceInterceptor = trainingServiceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(workServiceInterceptor).addPathPatterns("/work/workNow");
        registry.addInterceptor(trainingServiceInterceptor).addPathPatterns("/training-ground/trainNow");
    }
}