
import lombok.CreateUser;
import lombok.LoginBodyLombokModel;
import lombok.LoginResponseLombokModel;
import lombok.UserData;
import model.LoginBodyModel;
import model.LoginResponseModel;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ReqresInTests {

    @Test
    public void createUserWithLombok() {
        CreateUser putData = new CreateUser();
        putData.setName("morpheus");
        putData.setJob("leader");

        given()
                .spec(Specs.request)
                .body(putData)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .spec(Specs.responseSpec201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));

    }



    @Test
    public void getUserWithLombok() {
        UserData data = Specs.request
                .when()
                .get("/users/2")
                .then()
                .spec(Specs.responseSpec)
                .log()
                .body()
                .extract().as(UserData.class);

        assertEquals(2, data.getUser().getId());

    }

    @Test
    void checkEmail() {
        given()
                .spec(Specs.request)
                .when()
                .get("/users?page=2")
                .then()
                .spec(Specs.responseSpec)
                .body("data.findAll{it.id == 8}.email", hasItem("lindsay.ferguson@reqres.in"));
    }

    @Test
    public void test(){
        given()
                    .baseUri("https://reqres.in/")
                .when()
                    .get("/api/users/2")
                .then()
                .log().status()
                .log().body()
                .body("data.first_name", is("Janet"));
    }


    @Test
    public void testList(){
        given()
                .baseUri("https://reqres.in/")
                .when()
                .get("/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                //               .body("data.first_name", hasItem("Janet"));
                .body("data.first_name", hasItem("Michael"));
    }

    @Test
    public void testLogin(){
        LoginBodyModel data = new LoginBodyModel();
        LoginResponseModel response = new LoginResponseModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");
        response.setToken("QpwL5tke4Pnpja7X4");
        given()
                .baseUri("https://reqres.in/")
                .contentType(JSON)
                .body(data)
                .when()
                .post("api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is(response.getToken()));

    }

    @Test
    public void testLombokLogin(){
        LoginBodyLombokModel data = new LoginBodyLombokModel();
        LoginResponseLombokModel response = new LoginResponseLombokModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("cityslicka");
        response.setToken("QpwL5tke4Pnpja7X4");
        given()
                .baseUri("https://reqres.in/")
                .contentType(JSON)
                .body(data)
                .when()
                .post("api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is(response.getToken()));

    }


}
