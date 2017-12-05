package com.internship.dataone.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(RestaurantMapper.class)
public interface RestaurantDao {

    @SqlQuery("select * from orders;")
    public List<RestaurantOrder> getAllOrders();

    @SqlQuery("select * from orders where id = :id")
    public RestaurantOrder getOrder(@Bind("id") int id);

    @SqlUpdate("insert into orders(customer_name, item_ordered) values(:customer_name, :item_ordered)")
    void putOrder(@BindBean RestaurantOrder restaurantOrder);

    @SqlUpdate("update orders set customer_name = coalesce(:customer_name, customer_name), item_ordered = coalesce(:item_ordered, item_ordered) where id = :id")
    void editOrder(@BindBean RestaurantOrder restaurantOrder);

    @SqlQuery("select last_insert_id();")
    public int lastInsertId();
}
