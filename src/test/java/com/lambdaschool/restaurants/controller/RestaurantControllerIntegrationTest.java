package com.lambdaschool.restaurants.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.number.OrderingComparison.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestaurantControllerIntegrationTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp()
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    @Test
    public void whenMeasuredResponseTime()
    {
        given().when()
               .get("/restaurants/restaurants")
               .then()
               .time(lessThan(5000L));
    }

    //    GET /restaurants/restaurant/{restaurantId}
    @Test
    public void givenFoundRestaurantId() throws Exception
    {
        long aRestaurant = 10L;

        given().when()
               .get("/restaurants/restaurant/" + aRestaurant)
               .then()
               .statusCode(200)
               .and()
               .body(containsString("Bird"));
    }
}
