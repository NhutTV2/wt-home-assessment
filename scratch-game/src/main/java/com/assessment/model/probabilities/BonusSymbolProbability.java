package com.assessment.model.probabilities;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class BonusSymbolProbability {

    @JsonAlias("10x")
    private int x10;

    @JsonAlias("5x")
    private int x5;

    @JsonAlias("+1000")
    private int plus1000;

    @JsonAlias("+500")
    private int plus500;

    @JsonAlias("MISS")
    private int miss;
}
