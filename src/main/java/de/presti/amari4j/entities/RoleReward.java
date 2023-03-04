package de.presti.amari4j.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing the Reward, related to a Role, API-Object.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleReward {

    /**
     * The ID of the Guild Role.
     */
    long roleId;

    /**
     * The required level for the Role.
     */
    long level;
}
