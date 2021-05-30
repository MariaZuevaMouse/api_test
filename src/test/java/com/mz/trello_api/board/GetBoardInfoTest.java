package com.mz.trello_api.board;

import com.mz.trello_api.constants.Endpoints;
import com.mz.trello_api.dto.CreateNewBoardResponse;
import com.mz.trello_api.dto.GetBoardInfoResonse;
import com.mz.trello_api.trello_service.TrelloServiceObj;
import io.restassured.http.Method;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static com.mz.trello_api.trello_service.TrelloServiceObj.badRequestResponseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetBoardInfoTest extends BaseTest {

    @Test
    public void testGetExistingBoard() {
        CreateNewBoardResponse newBoardResponse =
                TrelloServiceObj.getNewBoardResponse(
                        TrelloServiceObj.requestBuilder()
                                .setRequestMethod(Method.POST)
                                .setBoardName("name")
                                .buildRequest()
                                .sendRequest(Endpoints.BOARD_URI));
        boardId = newBoardResponse.getId();

        GetBoardInfoResonse boardInfoResponse = TrelloServiceObj.getBoardInfoResponse(TrelloServiceObj.requestBuilder()
                .buildRequest()
                .sendRequest(Endpoints.BOARD_URI + boardId));

        assertThat(newBoardResponse.getName(), equalTo(boardInfoResponse.getName()));
        assertThat(newBoardResponse.getId(), equalTo(boardInfoResponse.getId()));
        assertThat(newBoardResponse.getUrl(), equalTo(boardInfoResponse.getUrl()));

        boardId = newBoardResponse.getId();

        TrelloServiceObj.requestBuilder().setRequestMethod(Method.DELETE)
                .buildRequest().sendRequest(Endpoints.BOARD_URI+boardId)
                .then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testGetUnExistingBoard() {
        TrelloServiceObj.requestBuilder().buildRequest()
                .sendRequest(Endpoints.BOARD_URI +String.valueOf((int)(Math.random()+100000)))
                .then().assertThat().spec(badRequestResponseSpecification());
    }
}
