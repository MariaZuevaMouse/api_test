package com.mz.trello_api.board;

import com.mz.trello_api.constants.Endpoints;
import com.mz.trello_api.dto.CreateNewBoardResponse;
import com.mz.trello_api.dto.NewBoardResponse;
import com.mz.trello_api.trello_service.TrelloServiceObj;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateBoardTest {

    @BeforeClass
    public void beforeClass() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    String boardId;

    @Test
    public void testCreateBoard() {
//        CreateNewBoardResponse newBoardResponse =
//                TrelloServiceObj.getNewBoardResponse(
                        TrelloServiceObj.requestBuilder()
                .setRequestMethod(Method.POST)
                .setBoardName("name")
                .buildRequest()
                .sendRequest(Endpoints.BOARD_URI);
//    );
//        ResponseBody name = TrelloServiceObj.requestBuilder()
//                .setRequestMethod(Method.POST)
//                .setBoardName("name")
//                .buildRequest()
//                .sendRequest(Endpoints.BOARD_URI).then()
////                .getBody
//        RestAssured.given().baseUri("").post("").then().
//
//                .get()
//                .then().extract().body()
//        boardId = newBoardResponse.getId();

    }

    @Test
    public void testCreateBoardEx() {
//        CreateNewBoardResponse board =
//        ValidatableResponse validatableResponse =
                RestAssured.given()
//                .param("key", "f48409dc2bf4433abeed098aa6e7ab68")
//                .param("token", "ba0090186f3dff521ad9d527f10fdd8246a80e6c9d9d37b0bca966f98b2c0d36")
//                .param("name", "name1")
                .log().all()
//                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post("https://api.trello.com/1/boards/?key=f48409dc2bf4433abeed098aa6e7ab68&token="
                        + "ba0090186f3dff521ad9d527f10fdd8246a80e6c9d9d37b0bca966f98b2c0d36&name=name77")
                .prettyPeek();
//                .then()
//                .statusCode(200);
//        System.out.println(validatableResponse);
//
//                .statusCode(200);
//                .contentType(ContentType.JSON)
//                .contentType("application/json; charset=utf-8")
//                .extract().body().as(CreateNewBoardResponse.class);

//        System.out.println(board);
    }

    @AfterMethod
    public void tearDown() {

    }
}
