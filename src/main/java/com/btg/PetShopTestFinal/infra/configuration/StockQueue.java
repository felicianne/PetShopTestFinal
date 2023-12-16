package com.btg.PetShopTestFinal.infra.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockQueue { @Value("${config.stock.fila.reservation.out}")
private String fila;
    @Bean
    public Queue queue(){
        return new Queue(fila, true);
    }
}
