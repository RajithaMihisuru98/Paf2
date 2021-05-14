package com;

import java.sql.*;

public class Product
{ //A common method to connect to the DB
	
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			//Provide the correct details: DBServer/DBName, username, password
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf","root", "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return con;

	}
	
	
	public String insertProduct( String product_name, String owner, String description, String price , String email)
	{
		String output = "";
		try
		{
			Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for inserting."; } 
			 
			 
			 // create a prepared statement
			 String query = " insert into product (`product_id`, `product_name`, `owner`, `description`,`price`,`email`)"+ " values (?, ?, ?, ?, ? ,?)"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 
			 
			 // binding values 
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, product_name);
			 preparedStmt.setString(3,owner );
			 preparedStmt.setString(4, description );
			 preparedStmt.setDouble(5, Double.parseDouble(price));
			 preparedStmt.setString(6, email);
			// execute the statement

			preparedStmt.execute();
			
			con.close();
			
			String newItems = readProducts();
			output = "{\"status\":\"success\", \"data\": \"" +
			        newItems + "\"}";
			
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
				System.err.println(e.getMessage());
		}
			
		return output;
	}


public String readProducts()
{
		String output = "";
		try
		{
			Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for reading."; } 
			 
			 
			 // Prepare the html table to be displayed
			 
			 
			 output = "<table border='1'><tr><th>product_name</th><th>owner</th>" +
					 "<th>Product Description</th>" + 
					 "<th>price</th>"+
					 "<th>email</th>"+
			 "<th>Update</th><th>Remove</th></tr>"; 
			 
			 String query = "select * from product"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 
			 
			 // iterate through the rows in the result set
			 
			 
			 while (rs.next()) 
			
			 { 
				 
			 String product_id = Integer.toString(rs.getInt("product_id")); 
			 String product_name= rs.getString("product_name"); 
			 String owner = rs.getString("owner"); 
			 String description = rs.getString("description"); 
			 String price = Double.toString(rs.getDouble("price")); 
			 String email = rs.getString("email"); 
			 
			 // Add into the html table
			 
			 output += "<tr><td>" + product_name + "</td>"; 
			 output += "<td>" + owner + "</td>"; 
			 output += "<td>" + description + "</td>"; 
			 output += "<td>" + price+ "</td>"; 
			 output += "<td>" + email+ "</td>"; 
           
              
      //buttons
            
            output += "<td><input name='btnUpdate' type='button' value='Update' class=' btnUpdate btn btn-secondary' data-itemid='" + product_id + "'></td>"
            		+ "<td><input name = 'btnRemove' type='button' value = 'Remove' "
            		+ "class = 'btnRemove btn btn-danger' data-itemid='" + product_id + "'>"
            		+"</td></tr>";
            		
		}
		
		con.close();
		
		// Complete the html table
		
		output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}


	public String updateProduct(String id, String product_name, String owner,String description, String price,String email)
	{
			String output = "";
			try
			{
				Connection con = connect(); 
				 if (con == null) 
				 {return "Error while connecting to the database for updating."; } 
				 
				 // create a prepared statement
				 
				 String query = "UPDATE product SET  product_name=? , owner=?, description = ?, price=?, email=? WHERE  product_id=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 
				 
				 preparedStmt.setString(1,product_name); 
				 preparedStmt.setString(2,owner); 
				 preparedStmt.setString(3, description); 
				 preparedStmt.setDouble(4, Double.parseDouble(price)); 
				 preparedStmt.setString(5,email); 
				 preparedStmt.setInt(6, Integer.parseInt(id)); 
					// execute the statement
					preparedStmt.execute();
					
					con.close();
					
					String newItems = readProducts();
					output = "{\"status\":\"success\", \"data\": \"" +
					newItems + "\"}";
			}
			catch (Exception e)
			{
				output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
				System.err.println(e.getMessage());
			}
			return output;
	}
	
	
public String deleteProduct(String product_id)
{
	String output = "";
	try
	{
		Connection con = connect(); 
		 if (con == null) 
		 {return "Error while connecting to the database for deleting."; } 
		 
		 // create a prepared statement
		 
		 String query = "delete from product where product_id=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 // binding values
		 
		 preparedStmt.setInt(1, Integer.parseInt(product_id));
			// execute the statement
			preparedStmt.execute();
			
			con.close();
			
			String newItems = readProducts();
			output = "{\"status\":\"success\", \"data\": \"" +
			newItems + "\"}";
	}
	catch (Exception e)
	{
		output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
		System.err.println(e.getMessage());
	}
	
	return output;
	}

}