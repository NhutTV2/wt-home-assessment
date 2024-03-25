package com.assessment.model.symbol;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Symbol {

    @JsonAlias("A")
    private StandardSymbol aSymbol;

    @JsonAlias("B")
    private StandardSymbol bSymbol;

    @JsonAlias("C")
    private StandardSymbol cSymbol;

    @JsonAlias("D")
    private StandardSymbol dSymbol;

    @JsonAlias("E")
    private StandardSymbol eSymbol;

    @JsonAlias("F")
    private StandardSymbol fSymbol;

    @JsonAlias("10x")
    private BonusSymbol xTen;

    @JsonAlias("5x")
    private BonusSymbol xFive;

    @JsonAlias("+1000")
    private BonusSymbol plusOneThousand;

    @JsonAlias("+500")
    private BonusSymbol plusFiveHundred;

    @JsonAlias("MISS")
    private BonusSymbol miss;
}
