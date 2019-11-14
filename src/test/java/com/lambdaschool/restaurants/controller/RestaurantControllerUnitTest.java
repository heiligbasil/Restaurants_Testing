package com.lambdaschool.restaurants.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.restaurants.model.Menu;
import com.lambdaschool.restaurants.model.Payment;
import com.lambdaschool.restaurants.model.Restaurant;
import com.lambdaschool.restaurants.model.RestaurantPayments;
import com.lambdaschool.restaurants.service.RestaurantService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RestaurantController.class)
public class RestaurantControllerUnitTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    private List<Restaurant> restaurantList;

    @Before
    public void setUp() throws Exception
    {
        restaurantList = new ArrayList<>();

        Payment payType1 = new Payment("Credit Card");
        payType1.setPaymentid(1);
        Payment payType2 = new Payment("Cash");
        payType2.setPaymentid(2);
        Payment payType3 = new Payment ("Mobile Pay");
        payType3.setPaymentid(3);

        ArrayList<RestaurantPayments> allPay = new ArrayList<>();
        allPay.add(new RestaurantPayments(new Restaurant(), payType1));
        allPay.add(new RestaurantPayments(new Restaurant(), payType2));
        allPay.add(new RestaurantPayments(new Restaurant(), payType3));

        ArrayList<RestaurantPayments> cashPay = new ArrayList<>();
        cashPay.add(new RestaurantPayments(new Restaurant(), payType2));

        ArrayList<RestaurantPayments> noCashPay = new ArrayList<>();
        noCashPay.add(new RestaurantPayments(new Restaurant(), payType1));
        noCashPay.add(new RestaurantPayments(new Restaurant(), payType3));

        // Restaurant String name, String address, String city, String state, String telephone
        String rest1Name = "Apple Test";
        Restaurant r1 = new Restaurant(rest1Name,
                "123 Main Street",
                "City", "ST", "555-555-1234",
                allPay);
        r1.setRestaurantid(11);
        r1.getMenus().add(new Menu("Mac and Cheese", 6.95, r1));
        r1.getMenus().get(0).setMenuId(20);
        r1.getMenus().add(new Menu("Lasagna", 8.50, r1));
        r1.getMenus().get(1).setMenuId(21);
        r1.getMenus().add(new Menu("Meatloaf", 7.77, r1));
        r1.getMenus().get(2).setMenuId(22);
        r1.getMenus().add(new Menu("Tacos", 8.49, r1));
        r1.getMenus().get(3).setMenuId(23);
        r1.getMenus().add(new Menu("Chef Salad", 12.50, r1));
        r1.getMenus().get(4).setMenuId(24);

        restaurantList.add(r1);

        String rest2Name = "Eagle Cafe Test";
        Restaurant r2 = new Restaurant(rest2Name,
                "321 Uptown Drive",
                "Town", "ST", "555-555-5555",
                cashPay);
        r2.setRestaurantid(12);
        r2.getMenus().add(new Menu("Tacos", 10.49, r2));
        r2.getMenus().get(0).setMenuId(30);
        r2.getMenus().add(new Menu("Barbacoa", 12.75, r2));
        r2.getMenus().get(1).setMenuId(31);

        restaurantList.add(r2);

        String rest3Name = "Number 1 Eats Test";
        Restaurant r3 = new Restaurant(rest3Name,
                "565 Side Avenue",
                "Village", "ST", "555-123-1555",
                noCashPay);
        r3.setRestaurantid(13);
        r3.getMenus().add(new Menu("Pizza", 15.15, r3));
        r3.getMenus().get(0).setMenuId(40);

        restaurantList.add(r3);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllRestaurants() throws Exception
    {
        String apiUrl = "/restaurants/restaurants";

        Mockito.when(restaurantService.findAll()).thenReturn(restaurantList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(restaurantList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API Returns List", er, tr);
    }

    @Test
    public void listRestaurantNameCity()
    {
    }

    @Test
    public void listRestaurantNameLike()
    {
    }

    @Test
    public void getRestaurantById() throws Exception
    {
        String apiUrl = "/restaurants/restaurant/12";

        Mockito.when(restaurantService.findRestaurantById(12)).thenReturn(restaurantList.get(1));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);

        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(restaurantList.get(1));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("REST API Returns List", er, tr);

    }

    @Test
    public void getRestaurantByName()
    {
    }

    @Test
    public void addNewRestaurant() throws Exception
    {
        String apiUrl = "/restaurants/restaurant";

        // build a restaurant
        ArrayList<RestaurantPayments> thisPay = new ArrayList<>();
        String rest3Name = "Number 1 Test Eats";
        Restaurant r3 = new Restaurant(rest3Name,
                "565 Side Test Avenue",
                "Village", "ST", "555-123-1555",
                thisPay);
        r3.setRestaurantid(100);

        ObjectMapper mapper = new ObjectMapper();
        String restaurantString = mapper.writeValueAsString(r3);

        Mockito.when(restaurantService.save(any(Restaurant.class))).thenReturn(r3);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(restaurantString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateRestaurant()
    {
    }

    @Test
    public void deleteRestaurantById()
    {
    }
}