package de.presti.amari4j.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    String userid;
    String username;
    long experience;
    long weekly_experience;
    long level;
}
