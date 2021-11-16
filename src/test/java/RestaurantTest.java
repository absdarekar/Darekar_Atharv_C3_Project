import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest
{
    Restaurant restaurant;
    Restaurant mockRestaurant;
    @BeforeEach
    void setup_before_each_test()
    {
        String name = "Amelie's cafe";
        String location = "Chennai";
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant(name,location,openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        mockRestaurant = Mockito.spy(restaurant);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time()
    {
        LocalTime currentTime = LocalTime.parse("13:17:21");
        Mockito.doReturn(currentTime).when(mockRestaurant).getCurrentTime();

        assertTrue(mockRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time()
    {
        LocalTime currentTime = LocalTime.parse("23:47:41");
        Mockito.doReturn(currentTime).when(mockRestaurant).getCurrentTime();
        assertFalse(mockRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1()
    {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException
    {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception()
    {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER TOTAL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void return_price_of_the_given_item_name()
    {
        String itemName = "Sweet corn soup";
        int actualPrice = 119;

        int price = restaurant.getPrice(itemName);

        assertEquals(price,actualPrice);
    }

    @Test
    public void calculate_the_total_cost_of_the_order()
    {
        int orderTotalActual = 388;
        List<String> order = new ArrayList<String>();
        order.add("Sweet corn soup");
        order.add("Vegetable lasagne");

        int orderTotal = restaurant.calculateOrderTotal(order);

        assertEquals(orderTotal,orderTotalActual);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<ORDER TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}