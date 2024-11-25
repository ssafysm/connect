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

    public String getSummaryFromChatGpt(String prompt) throws IOException {
        // HTTP 클라이언트 생성
        OkHttpClient client = new OkHttpClient();

        // 요청 바디 생성
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("model", "gpt-4o-mini"); // 사용하고자 하는 모델명
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);
        messages.put(message);
        jsonBody.put("messages", messages);

        RequestBody body = RequestBody.create(mediaType, jsonBody.toString());

        // 요청 생성
        Request request = new Request.Builder()
                .url(OPENAI_API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + openaiApiKey)
                .build();

        // 요청 보내기
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        // 응답 파싱
        String responseBody = response.body().string();
        JSONObject responseJson = new JSONObject(responseBody);
        JSONArray choices = responseJson.getJSONArray("choices");
        String assistantMessage = choices.getJSONObject(0).getJSONObject("message").getString("content");

        return assistantMessage;
    }
}
