package com.mz.trello_api.board;

import com.mz.trello_api.constants.Endpoints;
import com.mz.trello_api.dto.CreateNewBoardResponse;
import com.mz.trello_api.trello_service.TrelloServiceObj;
import io.restassured.http.Method;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static com.mz.trello_api.trello_service.TrelloServiceObj.*;

public class DeleteBoardTest  extends BaseTest {

    @Test
    public void testDeleteExistingBoard() {
        CreateNewBoardResponse newBoardResponse =
                TrelloServiceObj.getNewBoardResponse(
                        TrelloServiceObj.requestBuilder()
                                .setRequestMethod(Method.POST)
                                .setBoardName("name")
                                .buildRequest()
                                .sendRequest(Endpoints.BOARD_URI));
        boardId = newBoardResponse.getId();

        TrelloServiceObj.requestBuilder().setRequestMethod(Method.DELETE)
                .buildRequest().sendRequest(Endpoints.BOARD_URI+boardId)
                .then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test(description = "different responses notFound 404 OR badRequest 400")
    public void testDeleteUnExistingBoard() {
        TrelloServiceObj.requestBuilder().setRequestMethod(Method.DELETE)
                .buildRequest().sendRequest(Endpoints.BOARD_URI+boardId)
                .then().assertThat()
                .spec(notFoundResponseSpecification());
    }

    @Test(description = "different responses notFound 404 OR badRequest 400")
    public void testDeleteUnExistingBoard2() {
        TrelloServiceObj.requestBuilder().setRequestMethod(Method.DELETE)
                .buildRequest().sendRequest(Endpoints.BOARD_URI + (int)(Math.random()*1000))
                .then().assertThat()
                .spec(badRequestResponseSpecification());
    }
}
