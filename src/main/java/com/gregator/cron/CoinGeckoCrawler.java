package com.gregator.cron;

import ch.qos.logback.core.CoreConstants;
import com.gregator.model.Ticker;
import com.gregator.repository.TickerDao;
import com.litesoftwares.coingecko.CoinGeckoApiClient;
import com.litesoftwares.coingecko.constant.Currency;
import com.litesoftwares.coingecko.domain.Coins.CoinFullData;
import com.litesoftwares.coingecko.domain.Coins.CoinList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CoinGeckoCrawler {

    private final CoinGeckoApiClient coinGecko;
    private final TickerDao tickerDao;

    @Autowired
    public CoinGeckoCrawler(CoinGeckoApiClient coinGecko, TickerDao tickerDao) {
        this.coinGecko = coinGecko;
        this.tickerDao = tickerDao;
    }

    @PostConstruct
    public void postConstruct() {

        /**
         * get / update list of coins tickers
         */
        getCoinsTicker();

//        updateCoinsList();
//        crawlCoinGecko();
    }

    private void getCoinsTicker() {
        List<CoinList> coinList = coinGecko.getCoinList();
        List<Ticker> tickers = coinList.stream()
                .map(coin -> {
                    Ticker existingTicker = tickerDao.findTickerByNameAndSymbolIgnoreCase(coin.getName(), coin.getSymbol());

                    if (existingTicker == null) {
                        Ticker ticker = new Ticker();
                        ticker.setName(coin.getName());
                        ticker.setSymbol(coin.getSymbol());
                        return ticker;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        tickerDao.saveAll(tickers);
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
