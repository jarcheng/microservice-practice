package io.qifan.microservice.order.infrastructure.uitls;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class JacksonObject implements ApplicationContextAware {
    public static ObjectMapper objectMapper;

    // bean的生命周期拓展接口
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 因为objectMapper是static，无法使用@Autowire。在bean创建后自己手动赋值。
        objectMapper = applicationContext.getBean(ObjectMapper.class);
    }
}
