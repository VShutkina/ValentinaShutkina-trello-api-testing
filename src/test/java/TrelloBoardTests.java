import api.BoardApi;
import beans.Board;
import constants.TrelloConstants;
import io.restassured.http.Method;
import org.testng.annotations.Test;

import static constants.TrelloConstants.BOARD_DESC_UPD;
import static constants.TrelloConstants.BOARD_NAME_FOR_UPDATE;
import static io.restassured.http.Method.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TrelloBoardTests {

    @Test
    public void createBoardTest() {
        Board board = BoardApi.getBoardAnswer(BoardApi
                .with()
                .method(POST)
                .name(TrelloConstants.BOARD_NAME)
                .desc(TrelloConstants.BOARD_DESC)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response());
        assertThat(board.name, equalTo(TrelloConstants.BOARD_NAME));
        assertThat(board.desc, equalTo(TrelloConstants.BOARD_DESC));

    }

    @Test
    public void getBoardTest() {

        Board board = BoardApi.getBoardAnswer(BoardApi
                .with()
                .method(POST)
                .name(TrelloConstants.BOARD_NAME)
                .desc(TrelloConstants.BOARD_DESC)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response());

        Board boardFromGET = BoardApi.getBoardAnswer(BoardApi
                .with()
                .method(GET)
                .id(board.id)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response());
        assertThat(boardFromGET.name, equalTo(board.name));
        assertThat(boardFromGET.desc, equalTo(board.desc));
    }

    @Test
    public void updateBoardTest() {
        String boardId = BoardApi.getBoardAnswer(BoardApi
                .with()
                .method(POST)
                .name(TrelloConstants.BOARD_NAME)
                .desc(TrelloConstants.BOARD_DESC)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response()).id;

        Board boardUpd = BoardApi.getBoardAnswer(BoardApi.with()
                .method(PUT)
                .id(boardId)
                .name(BOARD_NAME_FOR_UPDATE)
                .desc(BOARD_DESC_UPD)
                .callApi().then()
                .extract().response());

        assertThat(boardUpd.name, equalTo(BOARD_NAME_FOR_UPDATE));
        assertThat(boardUpd.desc, equalTo(BOARD_DESC_UPD));
    }

    @Test
    public void deleteBoardTest() {
        String boardId = BoardApi.getBoardAnswer(BoardApi
                .with()
                .method(POST)
                .name(TrelloConstants.BOARD_NAME_FOR_DELETE)
                .desc(TrelloConstants.BOARD_DESC)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response()).id;

        BoardApi
                .with()
                .method(Method.DELETE)
                .id(boardId)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response();
        BoardApi
                .with()
                .method(GET)
                .id(boardId)
                .callApi().then()
                .specification(BoardApi.notFoundResponse()).extract().response();

    }
}
