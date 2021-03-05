package com.gregator.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document
public class Coin {

    @Id
    private String id;
    private String name;
    private String symbol;
    private String website;
    private String tickerId;
    private String whitepaper;
    private String community;
    private String sourceCode;
    private BigDecimal marketCap;
    private BigDecimal volume24h;
    private BigDecimal circulatingSupply;
    private BigDecimal maxSupply;
    private BigDecimal totalSupply;


}
