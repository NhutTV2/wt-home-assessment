package com.assessment.model;

import com.assessment.model.combination.WinCombination;
import com.assessment.model.probabilities.Probability;
import com.assessment.model.symbol.Symbol;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class InputConfig {

    private int columns;
    private int rows;
    private Symbol symbols;
    private Probability probabilities;
    @JsonAlias("win_combinations")
    private WinCombination winCombinations;
}
