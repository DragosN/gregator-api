package com.gregator.auth;

import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.impl.CoinGeckoApiClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoinGeckoConfig {

    @Bean
    public CoinGeckoApiClient coinGecko() {
        CoinGeckoApiClient client = new CoinGeckoApiClientImpl();
        client.ping();
        return client;
    }
}
