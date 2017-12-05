package com.internship.dataone.dao;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantMapper implements ResultSetMapper<RestaurantOrder> {
    private static final String id = "id";
    private static final String customer_name = "customer_name";
    private static final String item_ordered = "item_ordered";

    public RestaurantOrder map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new RestaurantOrder(resultSet.getInt(id), resultSet.getString(customer_name), resultSet.getString(item_ordered));
    }
}
