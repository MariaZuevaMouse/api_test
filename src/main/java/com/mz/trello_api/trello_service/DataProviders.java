package com.mz.trello_api.trello_service;

import com.mz.trello_api.utils.TestProperties;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider
    public static Object[][] boardNames(){
        return new Object[][]{
                {TestProperties.getTestDataProps().getProperty("board-name-en")},
                {TestProperties.getTestDataProps().getProperty("updated-board-name")},
                {TestProperties.getTestDataProps().getProperty("board-name-ru")},
                {TestProperties.getTestDataProps().getProperty("board-name-spec-symbol-name1")},
                {TestProperties.getTestDataProps().getProperty("board-name-spec-symbol-name2")},
                {TestProperties.getTestDataProps().getProperty("board-name-spec-symbol-name3")},
                {TestProperties.getTestDataProps().getProperty("board-name-spec-symbol-name4")},
                {TestProperties.getTestDataProps().getProperty("album.empty.name")},
        };
    }


}
