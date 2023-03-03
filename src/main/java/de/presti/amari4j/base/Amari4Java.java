package de.presti.amari4j.base;

import de.presti.amari4j.entities.Leaderboard;
import de.presti.amari4j.entities.Member;
import de.presti.amari4j.entities.Rewards;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Amari4Java {

    @Setter
    String apiKey;

    public Leaderboard getLeaderboard(String guildId) {
        return getLeaderboard(guildId, 1, 50);
    }

    public Leaderboard getLeaderboard(String guildId, int page, int limit) {
        return null;
    }

    public Leaderboard getRawLeaderboard(String guildId, int limit) {
        return null;
    }

    public Leaderboard getWeeklyLeaderboard(String guildId) {
        return getWeeklyLeaderboard(guildId, 1, 50);
    }

    public Leaderboard getWeeklyLeaderboard(String guildId, int page, int limit) {
        return null;
    }

    public Leaderboard getRawWeeklyLeaderboard(String guildId, int limit) {
        return null;
    }

    public Rewards getRewards(String guildId) {
        return getRewards(guildId, 1, 50);
    }

    public Rewards getRewards(String guildId, int page, int limit) {
        return null;
    }

    public Member getMember(String guildId, String userId) {
        return null;
    }

    public List<Member> getMembers(String guildId) {
        return new ArrayList<>();
    }
}
