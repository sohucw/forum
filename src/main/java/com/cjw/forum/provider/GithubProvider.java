package com.cjw.forum.provider;

import com.alibaba.fastjson.JSON;
import com.cjw.forum.dto.AccessTokenDto;
import com.cjw.forum.dto.GithubUser;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import okhttp3.*;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * @param
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 09:31 2019-08-14
 */
@Component
public class GithubProvider {
    // public static void main(String[] args) {
    //     GithubProvider githubProvider = new GithubProvider();
    // }
    // https://api.github.com/user?access_token=284856614e375b5160ec7f6eb212c14a4d2a09a5

    public String getAccessToken(AccessTokenDto accessTokenDto)  {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (
            Response response = client.newCall(request).execute()) {
            String str = response.body().string(); // access_token=e54dd1ca876f4fec79bc7359b232f89a961924d3&scope=&token_type=bearer
            String [] arrs = str.split("&");
            String tokenStr = arrs[0];
            String token = tokenStr.split("=")[1];
            System.out.println(token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return  githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
