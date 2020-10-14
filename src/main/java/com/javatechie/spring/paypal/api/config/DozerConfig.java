package com.javatechie.spring.paypal.api.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.awt.SunHints;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DozerConfig {

    @Bean(name = "org.dozer.Mapper")
    public DozerBeanMapper dozerBean(){
        List<String> mappingFiles = Arrays.asList(
                "dozer-bean-mappings.xml"
        );
        DozerBeanMapper dozerBean = new DozerBeanMapper();
        dozerBean.setMappingFiles(mappingFiles);
        return dozerBean;
    }
}
