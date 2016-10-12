package me.home.configuration;

import me.home.banner.Banner;
import me.home.banner.VitaminBannerPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

/**
 * Created by before30 on 2016. 10. 12..
 */
@Configuration
@Order(0)
public class BannerConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public Banner printBanner() {
        VitaminBannerPrinter vitaminBannerPrinter = new VitaminBannerPrinter(resourceLoader);
        return vitaminBannerPrinter.print(environment, System.out);
    }
}
