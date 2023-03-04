package de.presti.amari4j.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing the Leaderboard API-Object.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Leaderboard {

    /**
     * A list of all {@link Member}.
     */
    List<Member> members;

    /**
     * The received count of members.
     */
    int count;

    /**
     * The total amount of members.
     */
    int totalCount;
}
