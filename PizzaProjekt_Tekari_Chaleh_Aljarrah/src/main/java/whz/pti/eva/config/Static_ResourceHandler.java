package whz.pti.eva.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

@SuppressWarnings("deprecation")
@Configuration @EnableWebMvc
public class Static_ResourceHandler implements WebMvcConfigurer {

    /**
     * Adds the resource handlers.
     *
     * @param registry the registry
     */
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // When overriding default behavior, you need to add default(/) as well as added static paths(/webapp).

        // src/main/resources/static/...
        registry
            //.addResourceHandler("/**") // � /css/myStatic.css
            .addResourceHandler("/static/**") // � /static/css/myStatic.css
            .addResourceLocations("classpath:/static/") // Default Static Loaction
            .setCachePeriod( 3600 )
            .resourceChain(true) // 4.1
            .addResolver(new GzipResourceResolver()) // 4.1
            .addResolver(new PathResourceResolver()); //4.1

        // src/main/resources/templates/static/...
        registry
            .addResourceHandler("/templates/**") // � /templates/style.css
            .addResourceLocations("classpath:/templates/static/");


        // File located on disk
        registry
            .addResourceHandler("/system/files/**")
            .addResourceLocations("file:///D:/");
    }
}