import api.BoardApi;
import beans.Board;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.util.List;

import static constants.TrelloConstants.*;
import static io.restassured.http.Method.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static services.BoardService.createDefaultBoard;
import static services.BoardService.deleteBoard;

public class TrelloBoardTest {

    @Test
    public void createBoardTest() {
        Board board = BoardApi.getBoard(BoardApi
                .with()
                .method(POST)
                .name(BOARD_NAME)
                .desc(BOARD_DESC)
                .callApi().then()
                .specification(BoardApi.successResponse()));
        assertThat(board.name, equalTo(BOARD_NAME));
        assertThat(board.desc, equalTo(BOARD_DESC));

    }

    @Test
    public void getBoardTest() {
        Board board = BoardApi.getBoard(BoardApi
                .with()
                .method(POST)
                .name(BOARD_NAME)
                .desc(BOARD_DESC)
                .callApi().then()
                .specification(BoardApi.successResponse()));
        Board boardFromGET = BoardApi.getBoard(BoardApi
                .with()
                .method(GET)
                .id(board.id)
                .callApi().then()
                .specification(BoardApi.successResponse()));
        assertThat(boardFromGET.name, equalTo(board.name));
        assertThat(boardFromGET.desc, equalTo(board.desc));
    }

    @Test
    public void updateBoardTest() {
        String boardId = createDefaultBoard().id;
        Board boardUpd = BoardApi.getBoard(BoardApi.with()
                .method(PUT)
                .id(boardId)
                .name(BOARD_NAME_FOR_UPDATE)
                .desc(BOARD_DESC_UPD)
                .callApi().then().specification(BoardApi.successResponse()));
        assertThat(boardUpd.name, equalTo(BOARD_NAME_FOR_UPDATE));
        assertThat(boardUpd.desc, equalTo(BOARD_DESC_UPD));
    }

    @Test
    public void deleteBoardTest() {
        String boardId = createDefaultBoard().id;
        deleteBoard(boardId);
        BoardApi
                .with()
                .method(GET)
                .id(boardId)
                .callApi().then()
                .specification(BoardApi.notFoundResponse());
    }

    @Test
    public void updateBoardGreenLabel() {
        String boardId = createDefaultBoard().id;
        Board boardUpd = BoardApi.getBoard(BoardApi.with()
                .method(PUT)
                .id(boardId)
                .labelName(BOARD_LABEL_GREEN)
                .callApi().then()
                .specification(BoardApi.successResponse()));
        Board boardFromGET = BoardApi.getBoard(BoardApi
                .with()
                .method(GET)
                .id(boardId)
                .callApi().then()
                .specification(BoardApi.successResponse()));
        assertThat(boardFromGET.labelNames.green, equalTo(boardUpd.labelNames.green));
    }

    @AfterSuite
    public void tearDown() {

        List<Board> boardsList = BoardApi.getMemberBoardsList(
                BoardApi.with()
                        .getMemberBoards().then()
                        .specification(BoardApi.successResponse()));

        for (Board board : boardsList) {
            deleteBoard(board.id);
        }

    }
}
