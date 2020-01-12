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

import java.util.List;

import static constants.TrelloConstants.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.lessThan;

public class MemberApi {

    private Method method = Method.GET;

    private MemberApi() {
    }

    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(JSON)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectResponseTime(lessThan(6000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static List<Board> getMemberBoardsListAnswer(Response response) {
        return new Gson().
                fromJson(response.asString().
                                trim(),
                        new TypeToken<List<Board>>() {
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

    public static RequestBuilder with() {
        MemberApi api = new MemberApi();
        return new RequestBuilder(api);
    }

    public static class RequestBuilder {
        MemberApi memberApi;

        public RequestBuilder(MemberApi memberApi) {
            this.memberApi = memberApi;
        }

        public RequestBuilder method(Method method) {
            memberApi.method = method;
            return this;
        }

        public Response getMemberBoards() {
            String username = ApiProperties.getApiProperties().getProperty(USERNAME);
            return RestAssured.with()
                    .spec(baseRequestConfiguration())
                    .log().all()
                    .basePath(MEMBER_PATH.concat(username).concat(BOARD_PATH))
                    .request(memberApi.method)
                    .prettyPeek();
        }

    }
}
