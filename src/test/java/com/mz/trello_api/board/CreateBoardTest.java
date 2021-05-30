package com.mz.trello_api.board;

import com.mz.trello_api.constants.Endpoints;
import com.mz.trello_api.dto.CreateNewBoardResponse;
import com.mz.trello_api.trello_service.DataProviders;
import com.mz.trello_api.trello_service.TrelloServiceObj;
import io.restassured.http.Method;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class CreateBoardTest extends BaseTest {

    @Test(dataProviderClass = DataProviders.class,
            dataProvider = "boardNames")
    public void testCreateBoard(String name) {
        CreateNewBoardResponse newBoardResponse =
                TrelloServiceObj.getNewBoardResponse(
                        TrelloServiceObj.requestBuilder()
                .setRequestMethod(Method.POST)
                .setBoardName(name)
                .buildRequest()
                .sendRequest(Endpoints.BOARD_URI));


        boardId = newBoardResponse.getId();

        assertThat(newBoardResponse.getName(), CoreMatchers.equalTo(name));
    }

    @AfterMethod
    public void tearDown() {
        TrelloServiceObj.requestBuilder()
                .setRequestMethod(Method.DELETE)
                .buildRequest().sendRequest(Endpoints.BOARD_URI + boardId);
    }
}

//    @Test
//    public void testCreateBoardEx() {
////        CreateNewBoardResponse board =
////        ValidatableResponse validatableResponse =
//        RestAssured.given()
////                .param("key", "f48409dc2bf4433abeed098aa6e7ab68")
////                .param("token", "ba0090186f3dff521ad9d527f10fdd8246a80e6c9d9d37b0bca966f98b2c0d36")
////                .param("name", "name1")
//                .log().all()
////                .header("Content-Type", "application/json")
//                .contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//                .when()
//                .post("https://api.trello.com/1/boards/?key=f48409dc2bf4433abeed098aa6e7ab68&token="
//                        + "ba0090186f3dff521ad9d527f10fdd8246a80e6c9d9d37b0bca966f98b2c0d36&name=name77")
//                .prettyPeek();
////                .then()
////                .statusCode(200);
////        System.out.println(validatableResponse);
//
////        System.out.println(board);
//    }

