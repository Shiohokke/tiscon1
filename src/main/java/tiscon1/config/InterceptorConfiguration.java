package tiscon1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import tiscon1.interceptor.AuthnInterceptor;
import tiscon1.interceptor.AuthzInterceptor;
import tiscon1.interceptor.LayoutInterceptor;
import tiscon1.interceptor.PrincipalInterceptor;
import tiscon1.repository.GenreRepository;
import tiscon1.repository.impl.CachedGenreRepository;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;

import java.util.Arrays;

/**
 * @author kawasima
 */
@Configuration
@EnableCaching
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    protected LayoutInterceptor layoutInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PrincipalInterceptor());
        registry.addInterceptor(new AuthnInterceptor())
                .addPathPatterns("/my/*");
        registry.addInterceptor(new AuthzInterceptor())
                .addPathPatterns("/my/*");
        registry.addInterceptor(layoutInterceptor);
    }

    @Bean
    CacheManager cacheManager() { // (2)
        SimpleCacheManager cacheManager = new SimpleCacheManager(); // (3)
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("model")));
        return cacheManager;
    }
}
