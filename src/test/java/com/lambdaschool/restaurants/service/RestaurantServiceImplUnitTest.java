package com.lambdaschool.restaurants.service;

import com.lambdaschool.restaurants.RestaurantsApplication;
import com.lambdaschool.restaurants.model.Menu;
import com.lambdaschool.restaurants.model.Restaurant;
import com.lambdaschool.restaurants.model.RestaurantPayments;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestaurantsApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestaurantServiceImplUnitTest
{
    @Autowired
    private RestaurantService restaurantService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void AfindRestaurantByNameLike()
    {

    }

    @Test
    public void BfindNameCity()
    {
    }

    @Test
    public void CfindAll()
    {
        assertEquals(4, restaurantService.findAll().size());
    }

    @Test
    public void DfindRestaurantById()
    {
        assertEquals("Apple Test", restaurantService.findRestaurantById(4).getName());
    }

    @Test
    public void EfindRestaurantByName()
    {
    }

    @Test (expected = EntityNotFoundException.class)
    public void FdeleteNotFound()
    {
        restaurantService.delete(1000);
        assertEquals(4, restaurantService.findAll().size());
    }

    @Test
    public void Gdeletefound()
    {
        restaurantService.delete(10);
        assertEquals(3, restaurantService.findAll().size());
    }

    @Test
    public void Hsave()
    {
        ArrayList<RestaurantPayments> noCashPay = new ArrayList<>();

        String rest4Name = "Shady Grove Test";
        Restaurant r4 = new Restaurant(rest4Name,
                "565 Side Avenue",
                "Village", "ST", "555-123-1555",
                noCashPay);
        r4.getMenus().add(new Menu("Pizza", 15.15, r4));

        Restaurant addRestaurant = restaurantService.save(r4);

        assertNotNull(addRestaurant);

        Restaurant foundRestaurant = restaurantService.findRestaurantById(addRestaurant.getRestaurantid());
        assertEquals(addRestaurant.getName(), foundRestaurant.getName());
    }

    @Test
    public void Iupdate()
    {
    }
}