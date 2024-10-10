package restBasicTest;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class ItemCRUDTest {
    @Test
    @DisplayName("Verify Create Read Update Delete Item - Todo.ly")
    public void verifyCRUDProject(){
        JSONObject body = new JSONObject();
        body.put("Content","RESTValeriaItem");

        // create
        Response response = given()
                .auth()
                .preemptive()
                .basic("api2024@2024.com","12345")
                .body(body.toString())
                .log().all()
                .when()
                .post("https://todo.ly/api/items.json");
        response.then()
                .statusCode(200)
                .body("Content",equalTo(body.get("Content")))
                .log().all();

        int id = response.then().extract().path("Id");
        // read
        response = given()
                .auth()
                .preemptive()
                .basic("api2024@2024.com","12345")
                .log().all()
                .when()
                .get("https://todo.ly/api/items/"+id+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo(body.get("Content")))
                .body("Icon",equalTo(7))
                .log().all();
        // update
        body.put("Content","RESTUPDATEVALERIA");
        body.put("Checked",true);
        response = given()
                .auth()
                .preemptive()
                .basic("api2024@2024.com","12345")
                .body(body.toString())
                .log().all()
                .when()
                .put("https://todo.ly/api/items/"+id+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("RESTUPDATEVALERIA"))
                .body("Checked",equalTo(true))
                .log().all();

        // delete
        response = given()
                .auth()
                .preemptive()
                .basic("api2024@2024.com","12345")
                .log().all()
                .when()
                .delete("https://todo.ly/api/items/"+id+".json");
        response.then()
                .statusCode(200)
                .body("Content",equalTo("RESTUPDATEVALERIA"))
                .body("Checked",equalTo(true))
                .body("Deleted",equalTo(true))
                .log().all();
    }
}
