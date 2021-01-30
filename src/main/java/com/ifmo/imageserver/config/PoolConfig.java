package com.ifmo.imageserver.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Class for define multitreading for receive to DB
 */

@Configuration
@ConfigurationProperties(prefix = "pool.config")
public class PoolConfig {

    @Setter
    private int corePoolSize;

    @Setter
    private int maxPoolSize;

    @Setter
    private String threadNamePrefix;

    @Bean
    @Qualifier("executor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}
