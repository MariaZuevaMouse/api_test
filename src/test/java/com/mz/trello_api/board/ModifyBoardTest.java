package com.mz.trello_api.board;

import com.mz.trello_api.constants.Endpoints;
import com.mz.trello_api.dto.BoardInfoResponse;
import com.mz.trello_api.trello_service.TrelloServiceObj;
import com.mz.trello_api.utils.TestProperties;
import io.restassured.http.Method;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ModifyBoardTest extends BaseTest{

    @BeforeMethod
    public void setUp() {
        BoardInfoResponse newBoardResponse =
                TrelloServiceObj.getNewBoardResponse(
                        TrelloServiceObj.requestBuilder()
                                .setRequestMethod(Method.POST)
                                .setBoardName("name")
                                .buildRequest()
                                .sendRequest(Endpoints.BOARD_URI));
        boardId = newBoardResponse.getId();
    }

    @Test
    public void testModifyBoardName() {
        String updatedName = TestProperties.getTestDataProps().getProperty("updated-board-name");
        BoardInfoResponse modifyBoardResponse = TrelloServiceObj.getModifyBoardResponse(TrelloServiceObj
                .requestBuilder().setRequestMethod(Method.PUT)
                .setBoardName(updatedName)
                .buildRequest()
                .sendRequest(Endpoints.BOARD_URI + boardId));

        assertThat(modifyBoardResponse.getName(), equalToIgnoringCase(updatedName));

    }

    @AfterMethod
    public void tearDown() {
        TrelloServiceObj.requestBuilder().setRequestMethod(Method.DELETE)
                .buildRequest().sendRequest(Endpoints.BOARD_URI+boardId)
                .then().assertThat().statusCode(HttpStatus.SC_OK);
    }
}
