package com.gregator.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Ticker {

    private String id;
    private String name;
    private String shortName;
    private String website;
    private String whitepaper;
    private String community;
    private String sourceCode;
    private BigDecimal marketCap;
    private BigDecimal volume24h;
    private BigDecimal circulatingSupply;
    private BigDecimal maxSupply;
    private BigDecimal totalSupply;

}
