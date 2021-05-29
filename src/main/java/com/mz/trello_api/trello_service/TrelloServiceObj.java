package com.mz.trello_api.trello_service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mz.trello_api.dto.CreateNewBoardResponse;
import com.mz.trello_api.dto.NewBoardResponse;
import com.mz.trello_api.utils.TestProperties;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.internal.mapping.GsonMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class TrelloServiceObj {
    private static final String TRELLO_BASE_URL = TestProperties
            .getTestDataProps().getProperty("host");
    private Method requestMethod;
    private Map<String, String> parameters;

    public TrelloServiceObj(Method requestMethod, Map<String, String> parameters) {
        this.requestMethod = requestMethod;
        this.parameters = parameters;
    }

    public static TrelloApiRequestBuilder requestBuilder() {
        return new TrelloApiRequestBuilder();
    }

    public static class TrelloApiRequestBuilder{
        private Map<String, String> parameters = new HashMap<>();
        private Method requestMethod = Method.GET;

        public TrelloApiRequestBuilder() {
            parameters.put("key", TestProperties.getTestDataProps().getProperty("api-key"));
            parameters.put("token", TestProperties.getTestDataProps().getProperty("token"));
        }

        public TrelloApiRequestBuilder setRequestMethod(Method requestMethod) {
            this.requestMethod = requestMethod;
            return this;
        }

        public TrelloApiRequestBuilder setBoardName(String name) {
            parameters.put("name", name);
            return this;
        }

        public TrelloServiceObj buildRequest(){
            return new TrelloServiceObj(requestMethod, parameters);
        }
    }

    public Response sendRequest(String refillURI){
        return RestAssured
                .given(requestSpecification())

                .params(parameters)
                .log().all()
                .request(requestMethod, TRELLO_BASE_URL + refillURI)
                .prettyPeek();
    }


    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Content-Length", "0")
                .setContentType(ContentType.JSON)
                .setAccept("*/*")
//                .setBaseUri(TRELLO_BASE_URL)
                .build();


    }

    public static CreateNewBoardResponse getNewBoardResponse(Response response){
//        return response.extract().body().as(NewBoardResponse.class);
//        GsonMapper gsonMapper = new GsonMapper()
//        return response.prettyPeek()
//                .
////                .then()
//                .contentType(ContentType.JSON)
//                .extract()
//                .body().as(CreateNewBoardResponse.class);
        return new Gson().fromJson(response.asString().trim(),
                new TypeToken<CreateNewBoardResponse>(){}.getType());
    }


}
