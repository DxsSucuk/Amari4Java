package de.presti.amari4j.base;

import com.google.gson.*;
import de.presti.amari4j.entities.Leaderboard;
import de.presti.amari4j.entities.Member;
import de.presti.amari4j.entities.Rewards;
import de.presti.amari4j.entities.RoleReward;
import de.presti.amari4j.exception.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Amari4J {

    @Setter
    private String apiKey;

    private static final String BASE_URL = "https://amaribot.com/api/v1";
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    public Leaderboard getLeaderboard(String guildId) throws RateLimitException, InvalidAPIKeyException {
        return getLeaderboard(guildId, 1, 50);
    }

    public Leaderboard getLeaderboard(String guildId, int page, int limit) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/leaderboard/" + guildId + "?page=" + page + "&limit=" + limit);
        if (jsonElement.isJsonObject()) {
            return parseLeaderboard(jsonElement.getAsJsonObject());
        }

        return null;
    }

    public Leaderboard getRawLeaderboard(String guildId, int limit) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/raw/leaderboard/" + guildId + "?limit=" + limit);
        if (jsonElement.isJsonObject()) {
            return parseLeaderboard(jsonElement.getAsJsonObject());
        }

        return null;
    }

    public Leaderboard getWeeklyLeaderboard(String guildId) throws RateLimitException, InvalidAPIKeyException {
        return getWeeklyLeaderboard(guildId, 1, 50);
    }

    public Leaderboard getWeeklyLeaderboard(String guildId, int page, int limit) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/weekly/" + guildId + "?page=" + page + "&limit=" + limit);
        if (jsonElement.isJsonObject()) {
            return parseLeaderboard(jsonElement.getAsJsonObject());
        }

        return null;
    }

    public Leaderboard getRawWeeklyLeaderboard(String guildId, int limit) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/raw/weekly/" + guildId + "?limit=" + limit);
        if (jsonElement.isJsonObject()) {
            return parseLeaderboard(jsonElement.getAsJsonObject());
        }

        return null;
    }

    public Rewards getRewards(String guildId) throws RateLimitException, InvalidAPIKeyException {
        return getRewards(guildId, 1, 50);
    }

    public Rewards getRewards(String guildId, int page, int limit) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/rewards/" + guildId + "?page=" + page + "&limit=" + limit);
        if (jsonElement.isJsonObject()) {
            JsonObject rootObject = jsonElement.getAsJsonObject();

            Rewards rewards = new Rewards();

            if (rootObject.has("count") && rootObject.get("count").isJsonPrimitive()) {
                rewards.setCount(rootObject.getAsJsonPrimitive("count").getAsInt());
            }

            if (rootObject.has("data") && rootObject.get("data").isJsonArray()) {
                JsonArray dataArray = rootObject.getAsJsonArray("data");
                List<RoleReward> roleRewards = new ArrayList<>();
                for (JsonElement dataEntries : dataArray) {

                    if (dataEntries.isJsonObject()) {
                        roleRewards.add(parseRoleReward(dataEntries.getAsJsonObject()));
                    }
                }

                rewards.setRoleRewards(roleRewards);
            }

            return rewards;
        }
        return null;
    }

    public Member getMember(String guildId, String userId) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/" + guildId + "/member/" + userId);
        if (jsonElement.isJsonObject()) {
            return parseMember(jsonElement.getAsJsonObject());
        }

        return null;
    }

    public List<Member> getMembers(String guildId) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/" + guildId + "/members");

        if (jsonElement.isJsonObject()) {
            JsonObject rootObject = jsonElement.getAsJsonObject();
            if (rootObject.has("members") && rootObject.get("members").isJsonArray()) {
                JsonArray memberArray = rootObject.getAsJsonArray("members");
                List<Member> members = new ArrayList<>();
                for (JsonElement dataEntries : memberArray) {

                    if (dataEntries.isJsonObject()) {
                        members.add(parseMember(dataEntries.getAsJsonObject()));
                    }
                }
                return members;
            }
        }

        return null;
    }

    private JsonElement send(String path) throws RateLimitException, InvalidAPIKeyException {
        Request request = new Request.Builder()
                .addHeader("Authorization", apiKey)
                .addHeader("User-Agent", "Amari4J/1.0")
                .url(BASE_URL + path).get().build();

        try (Response response = okHttpClient.newCall(request).execute()) {

            String body = null;

            if (response.body() != null) {
                body = response.body().byteString().string(StandardCharsets.UTF_8);
            }

            JsonElement responseElement = body != null ? JsonParser.parseString(body) : new JsonObject();

            String message = responseElement.isJsonObject() && responseElement.getAsJsonObject().has("error") ?
                    responseElement.getAsJsonObject().getAsJsonPrimitive("error").getAsString() : "";

            if (response.isSuccessful()) {
                return responseElement;
            } else if (response.code() == 404) {
                if (path.contains("member")) {
                    throw new InvalidMemberException(message);
                } else {
                    throw new InvalidGuildException(message);
                }
            } else if (response.code() == 429) {
                throw new RateLimitException(message);
            } else if (response.code() == 403) {
                throw new InvalidAPIKeyException(message);
            } else {
                throw new InvalidServerResponseException(message);
            }
        } catch (IOException ioException) {
            throw new InvalidServerResponseException(ioException.getMessage());
        }
    }

    private Leaderboard parseLeaderboard(JsonObject jsonObject) {

        Leaderboard leaderboard = new Leaderboard();

        if (jsonObject.has("count") && jsonObject.get("count").isJsonPrimitive()) {
            leaderboard.setCount(jsonObject.getAsJsonPrimitive("count").getAsInt());
        }

        if (jsonObject.has("total_count") && jsonObject.get("total_count").isJsonPrimitive()) {
            leaderboard.setTotalCount(jsonObject.getAsJsonPrimitive("total_count").getAsInt());
        } else {
            if (leaderboard.getCount() != 0) leaderboard.setTotalCount(leaderboard.getCount());
        }

        if (jsonObject.has("data") && jsonObject.get("data").isJsonArray()) {
            JsonArray dataArray = jsonObject.getAsJsonArray("data");
            List<Member> members = new ArrayList<>();
            for (JsonElement dataEntries : dataArray) {

                if (dataEntries.isJsonObject()) {
                    members.add(parseMember(dataEntries.getAsJsonObject()));
                }
            }

            leaderboard.setMembers(members);
        }
        return leaderboard;
    }

    private Member parseMember(JsonObject memberObject) {
        Member member = new Member();

        if (memberObject.has("id") && memberObject.get("id").isJsonPrimitive()) {
            member.setUserid(memberObject.getAsJsonPrimitive("id").getAsString());
        }

        if (memberObject.has("username") && memberObject.get("username").isJsonPrimitive()) {
            member.setUsername(memberObject.getAsJsonPrimitive("username").getAsString());
        }

        if (memberObject.has("exp") && memberObject.get("exp").isJsonPrimitive()) {
            member.setExperience(memberObject.getAsJsonPrimitive("exp").getAsInt());
        }

        if (memberObject.has("level") && memberObject.get("level").isJsonPrimitive()) {
            member.setLevel(memberObject.getAsJsonPrimitive("level").getAsInt());
        }

        if (memberObject.has("weeklyExp") && memberObject.get("weeklyExp").isJsonPrimitive()) {
            member.setWeeklyExperience(memberObject.getAsJsonPrimitive("weeklyExp").getAsInt());
        }

        return member;
    }

    private RoleReward parseRoleReward(JsonObject roleObject) {
        RoleReward roleReward = new RoleReward();

        if (roleObject.has("roleID") && roleObject.get("roleID").isJsonPrimitive()) {
            roleReward.setRoleId(roleObject.getAsJsonPrimitive("roleID").getAsLong());
        }

        if (roleObject.has("level") && roleObject.get("level").isJsonPrimitive()) {
            roleReward.setLevel(roleObject.getAsJsonPrimitive("level").getAsLong());
        }

        return roleReward;
    }
}
