package com.mz.trello_api.trello_service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mz.trello_api.dto.CreateNewBoardResponse;
import com.mz.trello_api.dto.GetBoardInfoResonse;
import com.mz.trello_api.dto.ModifyBoardResponse;
import com.mz.trello_api.utils.TestProperties;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.lessThan;

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
        String createPath = TRELLO_BASE_URL + refillURI
                + "?key=" + parameters.get("key")
                + "&token=" + parameters.get("token");
        if(parameters.get("name") != null)
            createPath += "&name=" + parameters.get("name");

        return RestAssured
                .given(requestSpecification())
                // .params(parameters)  (doesn't work with parameters/ work with themm in path)
                .log().all()
                .request(requestMethod, createPath)
                .prettyPeek();
    }


    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static CreateNewBoardResponse getNewBoardResponse(Response response){
        return new Gson().fromJson(response.asString().trim(),
                new TypeToken<CreateNewBoardResponse>(){}.getType());
    }

    public static GetBoardInfoResonse getBoardInfoResponse(Response response) {
        return new Gson().fromJson(response.asString().trim(),
                new TypeToken<GetBoardInfoResonse>(){}.getType());
    }

    public static ModifyBoardResponse getModifyBoardResponse(Response response) {
        return new Gson().fromJson(response.asString().trim(),
                new TypeToken<ModifyBoardResponse>(){}.getType());
    }

    public static ResponseSpecification goodResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectCookie("preAuthProps")
                .expectCookie("dsc")
                .expectResponseTime(lessThan(1000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification notFoundResponseSpecification(){
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .expectContentType(ContentType.TEXT)
                .build();
    }

    public static ResponseSpecification badRequestResponseSpecification(){
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .build();
    }


}
