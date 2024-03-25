package com.assessment.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Output {

    private String[][] matrix;

    private float reward;

    @JsonAlias("applied_winning_combinations")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, List<String>> appliedWinningCombinations;

    @JsonAlias("applied_bonus_symbol")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String appliedBonusSymbol;
}
