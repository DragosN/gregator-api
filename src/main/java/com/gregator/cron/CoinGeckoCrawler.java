package com.gregator.cron;

import ch.qos.logback.core.CoreConstants;
import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.constant.Currency;
import com.litesoftwares.coingecko.domain.Coins.CoinFullData;
import com.litesoftwares.coingecko.domain.Coins.CoinList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CoinGeckoCrawler {

    private final CoinGeckoApiClient coinGecko;

    @Autowired
    public CoinGeckoCrawler(CoinGeckoApiClient coinGecko) {
        this.coinGecko = coinGecko;
    }

    @PostConstruct
    public void postConstruct() {
        updateCoinsList();
        crawlCoinGecko();
    }

    private void updateCoinsList() {
//        https://api.coingecko.com/api/v3/search?locale=en
        List<CoinList> coinList = coinGecko.getCoinList();
        AtomicInteger i = new AtomicInteger();
        coinList.forEach(coin -> {

            System.out.println(i.get());
            System.out.println(coin.toString());
            i.getAndIncrement();
//            Map<String, Map<String, Double>> prices = coinGecko.getPrice(coin.getId(), Currency.USD, true, true, true, true);
//            System.out.println(prices);
        });

        System.out.println("==========================================================================");
        CoinFullData fullData = coinGecko.getCoinById(coinList.get(763).getId());
        System.out.println(fullData.getGenesisDate());
        System.out.println(fullData.getCoingeckoScore());
        System.out.println(fullData.getCoingeckoRank());
        System.out.println(fullData.getImage());
        System.out.println(fullData.getLastUpdated());
        System.out.println(fullData.getPublicInterestStats());
        System.out.println(fullData.getCommunityData());
        System.out.println(fullData.getTickers());
    }


    @Scheduled(fixedRate = CoreConstants.MILLIS_IN_ONE_MINUTE)
    public void crawlCoinGecko() {
        System.out.println("import prices");

        coinGecko.getPrice("bitcoin", Currency.USD);

    }
}
