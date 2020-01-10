package api;

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
import static org.hamcrest.Matchers.lessThan;

public class BoardApi {

    private HashMap<String, String> params = new HashMap<>();
    private Method method = Method.GET;

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

        public RequestBuilder id(String id) {
            boardApi.params.put(PARAM_ID, id);
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
            return RestAssured.with()
                    .queryParams(boardApi.params)
                    .spec(baseRequestConfiguration())
                    .log().all()
                    .basePath(BOARD_PATH)
                    .request(boardApi.method)
                    .prettyPeek();
        }
    }

}
