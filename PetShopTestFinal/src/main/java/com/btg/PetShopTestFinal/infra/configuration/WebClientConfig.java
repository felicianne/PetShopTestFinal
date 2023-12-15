package com.btg.PetShopTestFinal.infra.configuration;

import com.btg.PetShopTest.infra.client.StockClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {
    @Value("${config.stock.url}")
    private String stockUrl;

    @Bean
    WebClient webClient(){
        return WebClient.builder().baseUrl(stockUrl)
                .build();
    }

    @Bean
    StockClient stockClient (WebClient webClient){
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter
                        .forClient(webClient)).build();
        return httpServiceProxyFactory.createClient(StockClient.class);
    }
}
