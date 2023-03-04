package de.presti.amari4j.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing the Rewards API-Object.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rewards {

    /**
     * A list of all {@link RoleReward}
     */
    List<RoleReward> roleRewards;

    /**
     * The count of all rewards.
     */
    int count;
}
