package me.home.banner;

import lombok.Getter;
import me.home.ansi.AnsiPropertySource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by before30 on 2016. 10. 13..
 */
public class TextResourceBanner implements Banner {

    private static final Log logger = LogFactory.getLog(TextResourceBanner.class);
    @Getter
    private Resource resource;

    public TextResourceBanner(Resource resource) {
        Assert.notNull(resource, "Resouce muust not be null");
        Assert.isTrue(resource.exists(), "Resouce must exist");
        this.resource = resource;
    }

    @Override
    public void printBanner(Environment environment, PrintStream out) {
        try {
            String banner = StreamUtils.copyToString(resource.getInputStream(), Charset.forName("UTF-8"));

            for (PropertyResolver resolver : getPropertyResolvers(environment)) {
                banner = resolver.resolvePlaceholders(banner);
            }
            out.println(banner);

        } catch (Exception ex) {

            logger.warn("Banner not printable: " + resource + " (" + ex.getClass() + ")", ex);
        }
    }

    protected List<PropertyResolver> getPropertyResolvers(Environment environment) {
        List<PropertyResolver> resolvers = new ArrayList<PropertyResolver>();
        resolvers.add(environment);
        resolvers.add(getAnsiResolver());
        return resolvers;
    }

    private PropertyResolver getAnsiResolver() {
        MutablePropertySources sources = new MutablePropertySources();
        sources.addFirst(new AnsiPropertySource("ansi", true));
        return new PropertySourcesPropertyResolver(sources);
    }
}
