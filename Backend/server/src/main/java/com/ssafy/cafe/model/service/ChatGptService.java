package com.ssafy.cafe.model.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class ChatGptService {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api.key}")
    private String openaiApiKey;

    public String getSummaryFromChatGpt(String text, String base64Image) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("model", "gpt-4o-mini");

        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");

        // 콘텐츠 배열 생성
        JSONArray contentArray = new JSONArray();

        if (text != null && !text.isEmpty()) {
            JSONObject textObject = new JSONObject();
            textObject.put("type", "text");
            textObject.put("text", text);
            contentArray.put(textObject);
        }

        if (base64Image != null && !base64Image.isEmpty()) {
            JSONObject imageObject = new JSONObject();
            imageObject.put("type", "image_url");
            JSONObject imageUrlObject = new JSONObject();
            imageUrlObject.put("url", base64Image);
            imageObject.put("image_url", imageUrlObject);
            contentArray.put(imageObject);
        }

        message.put("content", contentArray);
        messages.put(message);

        jsonBody.put("messages", messages);
        jsonBody.put("max_tokens", 10000);

        RequestBody body = RequestBody.create(mediaType, jsonBody.toString());

        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + openaiApiKey)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            switch (response.code()) {
                case 429:
                    throw new IOException("API 한도 초과입니다.");
                default:
                    throw new IOException("오류가 있습니다. 코드: " + response.code());
            }
        }

        // 응답 파싱
        String responseBody = response.body().string();
        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray choices = responseJson.getJSONArray("choices");
        String assistantMessage = choices.getJSONObject(0).getJSONObject("message").getString("content");

        return assistantMessage;
    }
}
