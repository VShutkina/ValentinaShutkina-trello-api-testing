package api;

import beans.Board;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import utils.ApiProperties;

import java.util.HashMap;

import static constants.TrelloConstants.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.TEXT;
import static io.restassured.http.Method.POST;
import static org.hamcrest.Matchers.lessThan;

public class BoardApi {

    private HashMap<String, String> params = new HashMap<>();
    private Method method = Method.GET;
    private String boardId = "";

    private BoardApi() {
    }

    public static RequestBuilder with() {
        BoardApi api = new BoardApi();
        return new RequestBuilder(api);
    }

    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(JSON)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectResponseTime(lessThan(2000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification notFoundResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectResponseTime(lessThan(2000L))
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .build();
    }

    public static Board getBoardAnswer(Response response) {
        return new Gson().
                fromJson(response.asString().
                                trim(),
                        new TypeToken<Board>() {
                        }.getType());
    }

    public static RequestSpecification baseRequestConfiguration() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setAccept(JSON)
                .setBaseUri(ApiProperties.getApiProperties().getProperty(ROOT_URL))
                .addQueryParam(PROPERTY_KEY, ApiProperties.getApiProperties().getProperty(PROPERTY_KEY))
                .addQueryParam(PROPERTY_TOKEN, ApiProperties.getApiProperties().getProperty(PROPERTY_TOKEN))
                .build();

    }

    public static class RequestBuilder {
        BoardApi boardApi;

        public RequestBuilder(BoardApi boardApi) {
            this.boardApi = boardApi;
        }

        public RequestBuilder name(String name) {
            boardApi.params.put(PARAM_NAME, name);
            return this;
        }

        //trelloAPI doesn't describe this "id" param for board's creation
        public RequestBuilder id(String id) {
            //boardApi.params.put(PARAM_ID, id);
            boardApi.boardId = id;
            return this;
        }

        public RequestBuilder desc(String desc) {
            boardApi.params.put(PARAM_DESC, desc);
            return this;
        }

        public RequestBuilder method(Method method) {
            boardApi.method = method;
            return this;
        }

        public Response callApi() {
            String basePath = (boardApi.method == POST) ? BOARD_PATH : BOARD_PATH.concat(boardApi.boardId);
            return RestAssured.with()
                    .queryParams(boardApi.params)
                    .spec(baseRequestConfiguration())
                    .log().all()
                    .basePath(basePath)
                    .request(boardApi.method)
                    .prettyPeek();
        }
    }

}
