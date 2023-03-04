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

/**
 * Wrapper class containing all functions.
 */
@NoArgsConstructor
@AllArgsConstructor
public class Amari4J {

    /**
     * The API-Key that is being used.
     */
    @Setter
    private String apiKey;

    /**
     * The base url to the API.
     */
    private static final String BASE_URL = "https://amaribot.com/api/v1";

    /**
     * The used HttpClient for the requests.
     */
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    /**
     * Retrieve the Leaderboard of the specified Guild.
     * @param guildId the ID of the Guild.
     * @return an instance of the {@link Leaderboard} class containing the information.
     * @throws RateLimitException If you the rate-limit has been hit.
     * @throws InvalidAPIKeyException If the set API-Key is invalid.
     */
    public Leaderboard getLeaderboard(String guildId) throws RateLimitException, InvalidAPIKeyException {
        return getLeaderboard(guildId, 1, 50);
    }

    /**
     * Retrieve the Leaderboard of the specified Guild.
     * @param guildId the ID of the Guild.
     * @param page the page of the leaderboard.
     * @param limit how many entries per page.
     * @return an instance of the {@link Leaderboard} class containing the information.
     * @throws RateLimitException If you the rate-limit has been hit.
     * @throws InvalidAPIKeyException If the set API-Key is invalid.
     */
    public Leaderboard getLeaderboard(String guildId, int page, int limit) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/leaderboard/" + guildId + "?page=" + page + "&limit=" + limit);
        if (jsonElement.isJsonObject()) {
            return parseLeaderboard(jsonElement.getAsJsonObject());
        }

        return null;
    }

    /**
     * Retrieve the Raw Leaderboard of the specified Guild.
     * @param guildId the ID of the Guild.
     * @param limit how many entries should be returned.
     * @return an instance of the {@link Leaderboard} class containing the information.
     * @throws RateLimitException If you the rate-limit has been hit.
     * @throws InvalidAPIKeyException If the set API-Key is invalid.
     */
    public Leaderboard getRawLeaderboard(String guildId, int limit) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/raw/leaderboard/" + guildId + "?limit=" + limit);
        if (jsonElement.isJsonObject()) {
            return parseLeaderboard(jsonElement.getAsJsonObject());
        }

        return null;
    }

    /**
     * Retrieve the Weekly Leaderboard of the specified Guild.
     * @param guildId the ID of the Guild.
     * @return an instance of the {@link Leaderboard} class containing the information.
     * @throws RateLimitException If you the rate-limit has been hit.
     * @throws InvalidAPIKeyException If the set API-Key is invalid.
     */
    public Leaderboard getWeeklyLeaderboard(String guildId) throws RateLimitException, InvalidAPIKeyException {
        return getWeeklyLeaderboard(guildId, 1, 50);
    }

    /**
     * Retrieve the Weekly Leaderboard of the specified Guild.
     * @param guildId the ID of the Guild.
     * @param page the page of the leaderboard.
     * @param limit how many entries per page.
     * @return an instance of the {@link Leaderboard} class containing the information.
     * @throws RateLimitException If you the rate-limit has been hit.
     * @throws InvalidAPIKeyException If the set API-Key is invalid.
     */
    public Leaderboard getWeeklyLeaderboard(String guildId, int page, int limit) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/weekly/" + guildId + "?page=" + page + "&limit=" + limit);
        if (jsonElement.isJsonObject()) {
            return parseLeaderboard(jsonElement.getAsJsonObject());
        }

        return null;
    }

    /**
     * Retrieve the Raw Weekly Leaderboard of the specified Guild.
     * @param guildId the ID of the Guild.
     * @param limit how many entries should be returned.
     * @return an instance of the {@link Leaderboard} class containing the information.
     * @throws RateLimitException If you the rate-limit has been hit.
     * @throws InvalidAPIKeyException If the set API-Key is invalid.
     */
    public Leaderboard getRawWeeklyLeaderboard(String guildId, int limit) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/raw/weekly/" + guildId + "?limit=" + limit);
        if (jsonElement.isJsonObject()) {
            return parseLeaderboard(jsonElement.getAsJsonObject());
        }

        return null;
    }

    /**
     * Retrieve get Rewards of the specified Guild.
     * @param guildId the ID of the Guild.
     * @return an instance of {@link Rewards} containing all rewards.
     * @throws RateLimitException If you the rate-limit has been hit.
     * @throws InvalidAPIKeyException If the set API-Key is invalid.
     */
    public Rewards getRewards(String guildId) throws RateLimitException, InvalidAPIKeyException {
        return getRewards(guildId, 1, 50);
    }

    /**
     * Retrieve get Rewards of the specified Guild.
     * @param guildId the ID of the Guild.
     * @param page the page of the rewards.
     * @param limit how many entries per page.
     * @return an instance of {@link Rewards} containing all rewards.
     * @throws RateLimitException If you the rate-limit has been hit.
     * @throws InvalidAPIKeyException If the set API-Key is invalid.
     */
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

    /**
     * Get information about a specific member of a guild.
     * @param guildId the ID of the Guild.
     * @param userId the ID of the User.
     * @return an instance of {@link Member} containing all information about that member.
     * @throws RateLimitException If you the rate-limit has been hit.
     * @throws InvalidAPIKeyException If the set API-Key is invalid.
     */
    public Member getMember(String guildId, String userId) throws RateLimitException, InvalidAPIKeyException {
        JsonElement jsonElement = send("/guild/" + guildId + "/member/" + userId);
        if (jsonElement.isJsonObject()) {
            return parseMember(jsonElement.getAsJsonObject());
        }

        return null;
    }

    /**
     * Get information about all members of a guild.
     * @param guildId the ID of the Guild.
     * @return an instance of a {@link List} containing information about all {@link Member}s
     * @throws RateLimitException If you the rate-limit has been hit.
     * @throws InvalidAPIKeyException If the set API-Key is invalid.
     */
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

    /**
     * Handles the sending and json parsing.
     * @param path the API path
     * @return returns the parsed {@link JsonElement}
     * @throws RateLimitException If you the rate-limit has been hit.
     * @throws InvalidAPIKeyException If the set API-Key is invalid.
     */
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

    /**
     * Parses a {@link Leaderboard} out of a {@link JsonObject}
     * @param jsonObject the {@link JsonObject} containing all the leaderboard information.
     * @return a parsed {@link Leaderboard}
     */
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

    /**
     * Parses a {@link Member} out of a {@link JsonObject}
     * @param memberObject the {@link JsonObject} containing all the member information.
     * @return a parsed {@link Member}
     */
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

    /**
     * Parses a {@link RoleReward} out of a {@link JsonObject}
     * @param roleObject the {@link JsonObject} containing all the role reward information.
     * @return a parsed {@link RoleReward}
     */
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
