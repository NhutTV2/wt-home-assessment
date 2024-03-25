package com.assessment.model.probabilities;

import lombok.Data;

@Data
public class StandardProbability {

    private int column;
    private int row;
    private StandardSymbolProbability symbols;
}
