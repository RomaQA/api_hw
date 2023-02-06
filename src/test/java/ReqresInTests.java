
import lombok.CreateUser;
import lombok.UserData;
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
}

