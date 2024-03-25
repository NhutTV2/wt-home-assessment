package com.assessment.model.probabilities;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Probability {

    @JsonAlias("standard_symbols")
    private StandardProbability[] standardProbabilities;

    @JsonAlias("bonus_symbols")
    private BonusProbability bonusProbability;
}
