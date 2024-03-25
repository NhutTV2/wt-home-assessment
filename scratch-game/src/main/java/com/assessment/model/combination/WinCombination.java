package com.assessment.model.combination;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class WinCombination {

    @JsonAlias("same_symbol_3_times")
    private SameSymbol sameSymbol3Times;

    @JsonAlias("same_symbol_4_times")
    private SameSymbol sameSymbol4Times;

    @JsonAlias("same_symbol_5_times")
    private SameSymbol sameSymbol5Times;

    @JsonAlias("same_symbol_6_times")
    private SameSymbol sameSymbol6Times;

    @JsonAlias("same_symbol_7_times")
    private SameSymbol sameSymbol7Times;

    @JsonAlias("same_symbol_8_times")
    private SameSymbol sameSymbol8Times;

    @JsonAlias("same_symbol_9_times")
    private SameSymbol sameSymbol9Times;

    @JsonAlias("same_symbols_horizontally")
    private SameSymbol sameSymbolsHorizontally;

    @JsonAlias("same_symbols_vertically")
    private SameSymbol sameSymbolsVertically;

    @JsonAlias("same_symbols_diagonally_left_to_right")
    private SameSymbol sameSymbolsDiagonallyLeftToRight;

    @JsonAlias("same_symbols_diagonally_right_to_left")
    private SameSymbol sameSymbolsDiagonallyRightToLeft;
}
