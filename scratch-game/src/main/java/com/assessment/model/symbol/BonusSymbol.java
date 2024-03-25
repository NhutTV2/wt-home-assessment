package com.assessment.model.symbol;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class BonusSymbol extends StandardSymbol {

    @JsonAlias("extra")
    private int extra;

    private String impact;
}
