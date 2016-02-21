package tiscon1.config;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;

import java.util.Arrays;

/**
 * @author kawasima
 */

@EnableHazelcastHttpSession
@EnableCaching
@Configuration
public class HazelcastHttpSessionConfig {
    @Bean
    public HazelcastInstance embeddedHazelcast() {
        Config hazelcastConfig = new Config();
        return Hazelcast.newHazelcastInstance(hazelcastConfig);
    }

    @Bean
    CacheManager cacheManager() { // (2)
        SimpleCacheManager cacheManager = new SimpleCacheManager(); // (3)
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("model")));
        return cacheManager;
    }

}
