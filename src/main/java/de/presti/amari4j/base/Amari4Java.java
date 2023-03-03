package de.presti.amari4j.base;

import com.google.gson.*;
import de.presti.amari4j.entities.Leaderboard;
import de.presti.amari4j.entities.Member;
import de.presti.amari4j.entities.Rewards;
import de.presti.amari4j.exception.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Amari4Java {

    @Setter
    public String apiKey;

    private static String BASE_URL = "https://amaribot.com/api/v1";
    private OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

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

    private JsonElement send(String path) throws RateLimitException, IOException, InvalidAPIKeyException {
        Request request = new Request.Builder()
                .addHeader("Authorization", apiKey)
                .addHeader("User-Agent", "Amari4J/1.0")
                .url(BASE_URL + path).get().build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return JsonParser.parseString(response.body().byteString().string(StandardCharsets.UTF_8));
            } else {
                if (response.code() == 404) {
                    if (path.contains("member")) {
                        throw new InvalidMemberException();
                    } else {
                        throw new InvalidGuildException();
                    }
                } else if (response.code() == 429) {
                    throw new RateLimitException();
                } else if (response.code() == 403) {
                    throw new InvalidAPIKeyException();
                } else {
                    throw new InvalidServerResponseException();
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
