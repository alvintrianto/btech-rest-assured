package apiTestRunner;

import payload.ListAPI;
import payload.createUser;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.Random;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReqresTestApi {
    ListAPI baseURL = new ListAPI();
    Response response;
    createUser createUser = new createUser();
    public String user_id;
    Random  rand = new Random(); //Randomize number 1-10 for get single api list reqres user

    @BeforeMethod
    public  void  SetUp(){
        RestAssured.baseURI = baseURL.getReqresAPI();
    }

    @Test(priority = 0)
    public void getListUserPageAPI(){
        response = given()
                .when()
                .get("api/users?page=" + rand.nextInt(10))
                .then()
                .log().body()
                .statusCode(200)
                .extract().response();
    }

    @Test(priority = 1)
    public void postCreateUserAPI(){
        response = given()
                .header("Contect-Type","application/json")
                .body(createUser.createUsers().toJSONString())
                .when()
                .post("api/users")
                .then()
                .log().body()
                .statusCode(201)
                .extract().response();
        user_id = response.getBody().path("id");
    }

    @Test(priority = 2)
    public void getListByIDAPI(){
        response = given()
                .when()
                .get("api/user/"+user_id)
                .then()
                .statusCode(404)
                .extract().response();
        System.out.print(RestAssured.baseURI + "api/users/" +user_id);
    }
}
