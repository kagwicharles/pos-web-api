package com.kagwi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("/allSales")
public class Sales {
	private Statement st;
	private ResultSet rs;
	private String query = "SELECT product_name, quantity, amount FROM sale, product WHERE product.product_code = sale.product_code";

	public Sales() {
	}

	@SuppressWarnings("unchecked")
	@GET
	public String getAllSales() throws SQLException, ClassNotFoundException {

		// JSON object to put all records
		JSONObject jobsObject = new JSONObject();

		// JSON array to put jobs records
		JSONArray jobsArray = new JSONArray();

		// Create java statement
		st = new MysqlConnect().getConnection().createStatement();

		// Execute query and get result set
		rs = st.executeQuery(query);

		// Iterate through result set
		while (rs.next()) {
			JSONObject record = new JSONObject(); // New JSON object for every record in result set
			record.put("Product Name", rs.getString("product_name"));
			record.put("Product Quantity", rs.getString("quantity"));
			record.put("Product Amount", rs.getDouble("amount"));
			jobsArray.add(record);
		}

		jobsObject.put("Sales", jobsArray); // Put array in object

		return jobsObject.toString();
	}
}
