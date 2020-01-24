package services;

import api.BoardApi;
import beans.Board;
import io.restassured.http.Method;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.http.Method.POST;

public class BoardService {

    public BoardService() {
    }

    public static Board createDefaultBoard() {
        return BoardApi.getBoard(BoardApi
                .with()
                .method(POST)
                .name(RandomStringUtils.randomAlphabetic(5))
                .desc(RandomStringUtils.randomAlphabetic(5))
                .callApi().then()
                .specification(BoardApi.successResponse()));
    }

    public static void deleteBoard(String boardId) {
        BoardApi
                .with()
                .method(Method.DELETE)
                .id(boardId)
                .callApi().then()
                .specification(BoardApi.successResponse());
    }
}
