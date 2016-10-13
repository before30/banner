package me.home.banner;

import lombok.AllArgsConstructor;
import org.apache.commons.logging.Log;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by before30 on 2016. 10. 13..
 */
//@AllArgsConstructor
public class VitaminBannerPrinter {
    private static final String BANNER_LOCATION_PROPERTY = "banner.location";
    private static final String DEFAULT_BANNER_LOCATION = "classpath:_banner.txt";
    private static final Banner DEFAULT_BANNER = new DefaultBanner();

    private final ResourceLoader resourceLoader;

    public VitaminBannerPrinter(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private Banner getTextBanner(Environment environment) {
        String location = environment.getProperty(BANNER_LOCATION_PROPERTY, DEFAULT_BANNER_LOCATION);
        Resource resource = resourceLoader.getResource(location);
        if (resource.exists()) {
            return new TextResourceBanner(resource);
        }
        return null;
    }

    private Banner getBanner(Environment environment) {
        Banners banners = new Banners();
        banners.addIfNotNull(getTextBanner(environment));
        if (banners.hasAtLeastOneBanner()) {
            return banners;
        }

        return DEFAULT_BANNER;
    }

    private String createStringFromBanner(Banner banner, Environment environment) throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        banner.printBanner(environment, new PrintStream(baos));
        String charset = environment.getProperty("banner.charset", "UTF-8");
        return baos.toString(charset);
    }

    public Banner print(Environment environment, PrintStream out) {
        Banner banner = getBanner(environment);
        banner.printBanner(environment, out);

        return new PrintedBanner(banner);
    }

    public Banner print(Environment environment, Log logger) {
        Banner banner = getBanner(environment);
        try {
            logger.info(createStringFromBanner(banner, environment));
        } catch (UnsupportedEncodingException ex) {
            logger.warn("Failed to create String for banner", ex);
        }

        return new PrintedBanner(banner);
    }

    private static class Banners implements Banner {
        private final List<Banner> banners = new ArrayList<>();
        public void addIfNotNull(Banner banner) {
            if (banner != null) {
                this.banners.add(banner);
            }
        }

        public boolean hasAtLeastOneBanner() {
            return !this.banners.isEmpty();
        }


        @Override
        public void printBanner(Environment environment, PrintStream out) {
            for (Banner banner : this.banners) {
                banner.printBanner(environment, out);
            }
        }
    }

    /**
     * Decorator that allows a {@link Banner} to be printed again without needing to
     * specify the source class.
     */
    private static class PrintedBanner implements Banner {
        private final Banner banner;

        PrintedBanner(Banner banner) {
            this.banner = banner;
        }

        @Override
        public void printBanner(Environment environment, PrintStream out) {
            banner.printBanner(environment, out);
        }
    }
}
