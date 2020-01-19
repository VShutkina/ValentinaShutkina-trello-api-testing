import api.BoardApi;
import beans.Board;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.util.List;

import static constants.TrelloConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static services.BoardService.*;

public class TrelloBoardTest {

    @Test
    public void createBoardTest() {
        Board board = createBoard(BOARD_NAME, BOARD_DESC);
        assertThat(board.name, equalTo(BOARD_NAME));
        assertThat(board.desc, equalTo(BOARD_DESC));

    }

    @Test
    public void getBoardTest() {
        Board board = createBoard(BOARD_NAME, BOARD_DESC);
        Board boardFromGET = getBoardSuccess(board.id);
        assertThat(boardFromGET.name, equalTo(board.name));
        assertThat(boardFromGET.desc, equalTo(board.desc));
    }

    @Test
    public void updateBoardTest() {
        String boardId = createBoard(BOARD_NAME, BOARD_DESC).id;
        Board boardUpd = updateBoard(boardId, BOARD_NAME_FOR_UPDATE, BOARD_DESC_UPD);
        assertThat(boardUpd.name, equalTo(BOARD_NAME_FOR_UPDATE));
        assertThat(boardUpd.desc, equalTo(BOARD_DESC_UPD));
    }

    @Test
    public void deleteBoardTest() {
        String boardId = createBoard(BOARD_NAME_FOR_DELETE, BOARD_DESC).id;
        deleteBoard(boardId);
        getBoardNotFound(boardId);
    }

    @Test
    public void updateBoardGreenLabel() {
        String boardId = createBoard(BOARD_NAME_GET_PARAM_CASE, BOARD_DESC).id;
        Board boardUpd = updateBoardLabel(boardId, BOARD_LABEL_GREEN);
        Board boardFromGET = getBoardSuccess(boardId);
        assertThat(boardFromGET.labelNames.green, equalTo(boardUpd.labelNames.green));
    }

    @AfterSuite
    public void tearDown() {

        List<Board> boardsList = BoardApi.getMemberBoardsListAnswer(
                BoardApi.with()
                        .getMemberBoards().then()
                        .specification(BoardApi.successResponse())
                        .extract().response());

        for (Board board : boardsList) {
            deleteBoard(board.id);
        }

    }
}
