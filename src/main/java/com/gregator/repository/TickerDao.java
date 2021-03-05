package com.gregator.repository;

import com.gregator.model.Ticker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerDao extends MongoRepository<Ticker, String> {

    Ticker findTickerByNameAndSymbolIgnoreCase(String name, String symbol);

    Ticker findByNameOrSymbolIgnoreCase(String name, String symbol);
}
