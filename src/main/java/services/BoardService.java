package services;

import api.BoardApi;
import beans.Board;
import io.restassured.http.Method;

import static io.restassured.http.Method.*;

public class BoardService {

    public BoardService() {
    }

    public static Board createBoard(String boardName, String boardDesc) {
        return BoardApi.getBoardAnswer(BoardApi
                .with()
                .method(POST)
                .name(boardName)
                .desc(boardDesc)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response());
    }

    public static Board updateBoard(String boardId, String newBoardName, String newBoardDesc) {
        return BoardApi.getBoardAnswer(BoardApi.with()
                .method(PUT)
                .id(boardId)
                .name(newBoardName)
                .desc(newBoardDesc)
                .callApi().then()
                .extract().response());

    }

    public static Board getBoardSuccess(String boardId) {
        return BoardApi.getBoardAnswer(BoardApi
                .with()
                .method(GET)
                .id(boardId)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response());
    }

    public static void getBoardNotFound(String boardId) {
        BoardApi
                .with()
                .method(GET)
                .id(boardId)
                .callApi().then()
                .specification(BoardApi.notFoundResponse()).extract().response();
    }

    public static void deleteBoard(String boardId) {
        BoardApi
                .with()
                .method(Method.DELETE)
                .id(boardId)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response();
    }

    public static Board updateBoardLabel(String boardId, String label) {
        return BoardApi.getBoardAnswer(BoardApi.with()
                .method(PUT)
                .id(boardId)
                .labelName(label)
                .callApi().then()
                .extract().response());
    }
}
