package com.assessment.model.combination;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class SameSymbol {

    @JsonAlias("reward_multiplier")
    private float rewardMultiplier;

    private String when;

    private int count;

    private String group;

    @JsonAlias("covered_areas")
    private Object[] coveredAreas;
}
