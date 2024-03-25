package com.assessment.model.symbol;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class StandardSymbol {

    @JsonAlias("reward_multiplier")
    private float rewardMultiplier;

    private String type;
}
