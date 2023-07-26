package com.example.recruitmenttask.Configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.recruitmenttask.Utils.PageRequestDTOToPageRequestConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new PageRequestDTOToPageRequestConverter());
    }
}
