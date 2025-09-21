package com.esther.fluxsync.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 异步与定时任务配置类。
 * <p>
 * 该配置类启用了 Spring 的异步执行（@EnableAsync）与定时任务（@EnableScheduling），
 * 并定义了线程池执行器和任务调度器的 Bean。
 * </p>
 */
@Configuration
@EnableAsync
@EnableScheduling
class AsyncConfig {

    /**
     * 定义一个通用的线程池执行器，用于异步任务处理。
     * <p>
     * - 核心线程数：10
     * - 最大线程数：50
     * - 队列容量：100
     * - 线程名前缀："async-"
     * </p>
     *
     * @return 配置好的 {@link ThreadPoolTaskExecutor}
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);   // 设置核心线程数
        executor.setMaxPoolSize(50);    // 设置最大线程数
        executor.setQueueCapacity(100); // 设置队列容量
        executor.setThreadNamePrefix("async-"); // 设置线程名的前缀
        executor.initialize();
        return executor;
    }

    /**
     * 定义任务调度器，用于定时任务调度。
     * <p>
     * - 线程池大小：10
     * - 线程名前缀："task-"
     * </p>
     *
     * @return 配置好的 {@link TaskScheduler}
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("task-");
        scheduler.initialize();
        return scheduler;
    }

}
