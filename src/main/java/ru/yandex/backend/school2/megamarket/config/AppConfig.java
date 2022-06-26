package ru.yandex.backend.school2.megamarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import ru.yandex.backend.school2.megamarket.mapper.ShopUnitMapper;

import java.time.format.DateTimeFormatter;

@Configuration
@EnableWebMvc
public class AppConfig extends WebMvcConfigurationSupport {

    @Bean
    @Override
    public FormattingConversionService mvcConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

        DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
        dateTimeRegistrar.setDateTimeFormatter(DateTimeFormatter.ISO_LOCAL_DATE);
        dateTimeRegistrar.registerFormatters(conversionService);

        return conversionService;
    }

    @Bean
    public ShopUnitMapper goodMapper() {
        return new ShopUnitMapper();
    }

}
