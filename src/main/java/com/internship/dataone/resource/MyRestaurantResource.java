package com.internship.dataone.resource;

import com.internship.dataone.dao.Representation;
import com.internship.dataone.dao.RestaurantDao;
import com.internship.dataone.dao.RestaurantOrder;
import org.eclipse.jetty.http.HttpStatus;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.internship.dataone.Constants.DB_ERROR;
import static com.internship.dataone.Constants.NOT_FOUND;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)

public abstract class MyRestaurantResource {

    @CreateSqlObject
    abstract RestaurantDao restaurantDao();



    @GET
    @Path("/getAllOrders")
    public Representation<List<RestaurantOrder>> getAllOrders() {

        List<RestaurantOrder> orders = restaurantDao().getAllOrders();
        Representation<List<RestaurantOrder>> ordersToReturn = new Representation<>(HttpStatus.OK_200, orders);
        return ordersToReturn;

    }



    @GET
    @Path("/getOrder/{id}")
    public Representation<RestaurantOrder> getOrder(@PathParam("id") int id) {
        RestaurantOrder restaurantOrder = restaurantDao().getOrder(id);

        Representation<RestaurantOrder> ordersToReturn = new Representation<>(HttpStatus.OK_200, restaurantOrder);
        return ordersToReturn;
    }



    @POST
    @Path("/putOrder")
    public Representation<RestaurantOrder> putOrder(@NotNull RestaurantOrder restaurantOrder) {

        // first create order - return nothing (void)
        restaurantDao().putOrder(restaurantOrder);

        RestaurantOrder lastPlacedOrder = restaurantDao().getOrder(restaurantDao().lastInsertId());
        Representation<RestaurantOrder> ordersToReturn = new Representation<>(HttpStatus.OK_200, lastPlacedOrder);
        return ordersToReturn;
    }



    @PUT
    @Path("/editOrder/{id}")
    public Representation<RestaurantOrder> editOrder(@NotNull RestaurantOrder orderRecievedForEditing, @PathParam("id") int id) {

        orderRecievedForEditing.setId(id);

        // first check if order exists
        RestaurantOrder orderFetchedFromDB = restaurantDao().getOrder(orderRecievedForEditing.getId());

        if (orderFetchedFromDB == null) {
            throw new WebApplicationException(String.format(NOT_FOUND, orderRecievedForEditing.getId()),
                    Response.Status.NOT_FOUND);
        }

        // now that order does exist, edit the same
        restaurantDao().editOrder(orderRecievedForEditing);
        RestaurantOrder editedSuccessfullyOrder = restaurantDao().getOrder(orderRecievedForEditing.getId());

        Representation<RestaurantOrder> orderToReturn = new Representation<>(HttpStatus.OK_200, editedSuccessfullyOrder);
        return orderToReturn;
    }

    public String performHealthCheck() {
        try {
            restaurantDao().getAllOrders();
        } catch (Exception ex) {
            return DB_ERROR + ex.getCause().getLocalizedMessage();
        }
        return null;
    }

}
