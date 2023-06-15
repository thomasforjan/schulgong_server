package at.schulgong.util;

import java.io.IOException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * Single Page Application Configuration
 *
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @since June 2023
 */
@Configuration
public class SpaConfiguration implements WebMvcConfigurer {

    /**
     * Configures resource handlers for serving static resources
     *
     * @param registry the ResourceHandlerRegistry to register handlers to.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/public/")
                .resourceChain(false)
                .addResolver(
                        new PathResourceResolver() {
                            /**
                             * Resolve the requested resource. If the resource does not exist or is
                             * not readable, fall back to serving the index.html file.
                             *
                             * @param resourcePath the path to the requested resource.
                             * @param location a Resource representing the base directory.
                             * @return the requested resource if it exists and is readable,
                             *     otherwise the index.html file.
                             * @throws IOException if there is an error reading the resource.
                             */
                            @Override
                            protected Resource getResource(String resourcePath, Resource location)
                                    throws IOException {
                                Resource requestedResource = location.createRelative(resourcePath);

                                return requestedResource.exists() && requestedResource.isReadable()
                                        ? requestedResource
                                        : new ClassPathResource("/public/index.html");
                            }
                        });
    }
}
