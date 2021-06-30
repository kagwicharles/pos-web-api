package com.kagwi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("/allProducts")
public class Products {

	private Statement st;
	private ResultSet rs;
	private String query = "SELECT * FROM product";

	public Products() {
	}

	@SuppressWarnings("unchecked")
	@GET
	public String getAllProducts() throws SQLException, ClassNotFoundException {
		JSONObject jobsObject = new JSONObject();
		JSONArray jobsArray = new JSONArray();

		st = new MysqlConnect().getConnection().createStatement(); // create db connection
		rs = st.executeQuery(query);

		while (rs.next()) {
			JSONObject record = new JSONObject();
			record.put("Product Code", rs.getString("product_code"));
			record.put("Product Name", rs.getString("product_name"));
			record.put("Product Price", rs.getDouble("price"));
			jobsArray.add(record);
		}

		jobsObject.put("Stock", jobsArray); // Put array in object

		return jobsObject.toString();
	}
}