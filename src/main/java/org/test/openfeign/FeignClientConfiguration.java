package org.test.openfeign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class FeignClientConfiguration
{

    @Bean
    public OkHttpClient client()
    {
        return new OkHttpClient();
    }

}