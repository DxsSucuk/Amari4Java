package de.presti.amari4j.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing the Member API-Object.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    /**
     * The Discord ID of the User.
     */
    String userid;

    /**
     * The Discord username of the User.
     */
    String username;

    /**
     * The experience of the User.
     */
    long experience;

    /**
     * The weekly experience of the User.
     */
    long weeklyExperience;

    /**
     * The level of the User.
     */
    long level;
}
