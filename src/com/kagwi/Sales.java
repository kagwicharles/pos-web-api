package com.kagwi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("/Sales")
public class Sales {
	private Statement st;
	private ResultSet rs;
	private PreparedStatement pst;
	private String query = "SELECT * FROM sale";
	private String query_1 = "INSERT INTO sale (no_of_items, total_paid, served_by) VALUES (?, ?, ?)";

	public Sales() {
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/allSales")
	public String getAllSales() throws SQLException, ClassNotFoundException {

		JSONObject jobsObject = new JSONObject();
		JSONArray jobsArray = new JSONArray();

		st = new MysqlConnect().getConnection().createStatement(); // Obtain db connection
		rs = st.executeQuery(query); // execute select query to get all products

		// Iterate through result set
		while (rs.next()) {
			JSONObject record = new JSONObject();
			record.put("No. of items", rs.getString("no_of_items"));
			record.put("Total paid", rs.getString("total_paid"));
			record.put("Served by", rs.getString("served_by"));
			jobsArray.add(record);
		}

		jobsObject.put("Sales", jobsArray); // Put array in object

		return jobsObject.toString();
	}

	@GET
	@Path("/addSale/{noOfItems}/{totalPaid}/{servedBy}")
	@Produces(MediaType.TEXT_PLAIN)
	public String recordSale(@PathParam("noOfItems") int noOfItems, @PathParam("totalPaid") Double totalPaid,
			@PathParam("servedBy") String servedBy) {

		int i = 0;
		try {
			pst = new MysqlConnect().getConnection().prepareStatement(query_1);
			pst.setInt(1, noOfItems);
			pst.setDouble(2, totalPaid);
			pst.setString(3, servedBy);

			i = pst.executeUpdate();
			new MysqlConnect().closeConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return servedBy + " inserted " + String.valueOf(i) + " rows";
	}
}
