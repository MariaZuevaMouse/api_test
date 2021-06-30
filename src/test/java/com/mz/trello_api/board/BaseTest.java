package com.mz.trello_api.board;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    String boardId;

    @BeforeClass
    public void beforeClass() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
