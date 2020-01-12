import api.BoardApi;
import api.MemberApi;
import beans.Board;
import io.restassured.http.Method;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.util.List;

import static constants.TrelloConstants.*;
import static io.restassured.http.Method.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TrelloBoardTests {

    @Test
    public void createBoardTest() {
        Board board = BoardApi.getBoardAnswer(BoardApi
                .with()
                .method(POST)
                .name(BOARD_NAME)
                .desc(BOARD_DESC)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response());
        assertThat(board.name, equalTo(BOARD_NAME));
        assertThat(board.desc, equalTo(BOARD_DESC));

    }

    @Test
    public void getBoardTest() {

        Board board = BoardApi.getBoardAnswer(BoardApi
                .with()
                .method(POST)
                .name(BOARD_NAME)
                .desc(BOARD_DESC)
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
                .name(BOARD_NAME)
                .desc(BOARD_DESC)
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
                .name(BOARD_NAME_FOR_DELETE)
                .desc(BOARD_DESC)
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

    @Test
    public void updateBoardGreenLabel() {
        String boardId = BoardApi.getBoardAnswer(BoardApi
                .with()
                .method(POST)
                .name(BOARD_NAME_GET_PARAM_CASE)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response()).id;

        Board boardUpd = BoardApi.getBoardAnswer(BoardApi.with()
                .method(PUT)
                .id(boardId)
                .labelName(BOARD_LABEL_GREEN)
                .callApi().then()
                .extract().response());

        Board boardFromGET = BoardApi.getBoardAnswer(BoardApi
                .with()
                .method(GET)
                .id(boardId)
                .callApi().then()
                .specification(BoardApi.successResponse()).extract().response());
        assertThat(boardFromGET.labelNames.green, equalTo(boardUpd.labelNames.green));


    }

    @AfterSuite
    public void tearDown() {

        List<Board> boardsList = MemberApi.getMemberBoardsListAnswer(
                MemberApi.with()
                        .getMemberBoards().then()
                        .specification(MemberApi.successResponse())
                        .extract().response());

        for (Board board : boardsList) {
            BoardApi
                    .with()
                    .method(Method.DELETE)
                    .id(board.id)
                    .callApi().then()
                    .specification(BoardApi.successResponse()).extract().response();

        }

    }
}
