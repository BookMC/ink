package org.bookmc.ink.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GithubUtils {
    private static final JsonParser parser = new JsonParser();

    public static String getLatestRelease(String repo, boolean bleedingEdge) {
        if (bleedingEdge) {
            String json = get(String.format("https://api.github.com/repos/%s/commits", repo));
            JsonObject commit = parser.parse(json).getAsJsonArray().get(0).getAsJsonObject();

            return commit.get("sha").getAsString().substring(0, 10);
        } else {
            String json = get(String.format("https://api.github.com/repos/%s/releases", repo));
            JsonArray release = parser.parse(json).getAsJsonArray();
            if (release.size() > 0) {
                return release.get(0).getAsJsonObject().get("tag_name").getAsString();
            } else {
                String commitJson = get(String.format("https://api.github.com/repos/%s/commits", repo));
                JsonObject commit = parser.parse(commitJson).getAsJsonArray().get(0).getAsJsonObject();

                return commit.get("sha").getAsString().substring(0, 10);
            }
        }
    }

    public static String get(String url) {
        StringBuilder builder = new StringBuilder();
        try (InputStream in = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String inputLine;
            while ((inputLine = rd.readLine()) != null) {
                builder.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
