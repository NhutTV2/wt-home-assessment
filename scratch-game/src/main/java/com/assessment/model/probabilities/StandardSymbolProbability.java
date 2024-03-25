package com.assessment.model.probabilities;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class StandardSymbolProbability {

    @JsonAlias("A")
    private int a;

    @JsonAlias("B")
    private int b;

    @JsonAlias("C")
    private int c;

    @JsonAlias("D")
    private int d;

    @JsonAlias("E")
    private int e;

    @JsonAlias("F")
    private int f;
}
