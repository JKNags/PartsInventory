package me.Jack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class Main {

	/*	Inventory Database Program
	 * 	Jack Nagel
	 *  Created: 9 July 2014
	 *  Last Edited: 5 November 2014
	 */
	
	static Connection conn = null;
	
	static String userName = "root";
	static String password = "kiva";
	
	static String schema = "partsinv";
	
	static Scanner input = new Scanner(System.in);
		
	static String search = "";
	
	public static void main(String[] args) throws SQLException {
		
		getConnection();
		ViewTable.main(args);
	}
					
	public static Connection getConnection() throws SQLException {
		
		try {
			
			Properties connectionProps = new Properties();
			connectionProps.put("user", userName);
			connectionProps.put("password", password);
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + schema, connectionProps);
			
		} catch(Exception e){			
			System.err.print("\nError:\n\t" + e);
		} finally {			
			//if(conn!= null) conn.close();			
		}
		
		return conn;
		
	}
		
	public static String getSearch(TableCursor outputTableCursor) {
		
		TableItem row = outputTableCursor.getRow();
		
		String search = " (`brand` LIKE '" + row.getText(0) + "'"
    			+ " AND `description` LIKE '" + row.getText(1) + "'"
    			+ " AND `make` LIKE '" + row.getText(2) + "'"
    			+ " AND `model` LIKE '" + row.getText(3) + "'"
    			+ " AND `manufacturer_part_number` LIKE '" + row.getText(4) + "'"                        			
    			+ " AND `tmedic_sku` LIKE '" + row.getText(5) + "')";
		
		return (search);
	
	}
	
	public static String getSimilar(TableCursor outputTableCursor) {
		
		TableItem row = outputTableCursor.getRow();
		
		String search = " (`brand` LIKE '" + row.getText(0) + "'"
    			+ " AND `make` LIKE '" + row.getText(2) + "'"
    			+ " AND `manufacturer_part_number` LIKE '" + row.getText(4) + "'";
		
				if (!row.getText(5).equals("")) {
					search += " AND `tmedic_sku` LIKE '" + row.getText(5) + "')";
				}
				
		return (search);
	
	}
	
	public static String getSearch(String column, String value) {
		
		String search = " (`" + column + "` LIKE '%" + value + "%')   ";
		return (search);
	
	}
		
	public static String update(String table, String column, String value, String search) {
		
		String update = "UPDATE " + schema + "." + table
		+ " SET `" + column + "` = '" + value + "'"
		+ " WHERE " + search;
		
		return (update);
		
	}
	
	public static String getColumnName(String table, int pos) throws SQLException {
		
		Statement stmt = null; 
		String columnName = "";
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(" SELECT * FROM " + schema + "." + table);
			
			ResultSetMetaData metaData = rs.getMetaData();
		 	columnName = metaData.getColumnLabel(pos); 
				
		} catch(Exception e){
			System.err.print("\nError:\n\t" + e);
		} finally {
			if(stmt!= null) stmt.close();
		}
		
		return (columnName);
		
	}
		
	public static int getRowsAffected(String table, String[] search) throws SQLException {
		
		Statement stmt = null; 
		int number = 0;
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(" SELECT COUNT(*) FROM " + schema + "." + table + " WHERE " + search[0] + search[1]);
			
			while (rs.next()) {
				
				number = rs.getInt(1);
			
			}
			
		} catch(Exception e){
			System.err.print("\nError:\n\t" + e);
		} finally {
			if(stmt!= null) stmt.close();
		}
		
		return (number);
		
	}
	
	public static int getColumnCount(String table) throws SQLException {
		
		Statement stmt = null; 
		int count = 0;
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(" SELECT * FROM " + schema + "." + table);
			
			ResultSetMetaData metaData = rs.getMetaData();
		 	count = metaData.getColumnCount(); 
				
		} catch(Exception e){
			System.err.print("\nError:\n\t" + e);
		} finally {
			if(stmt!= null) stmt.close();
		}
		
		return (count);
		
	}
	
	public static void sellPart(String amt_sold_for, String search) throws SQLException {

		Statement stmt = null;
		String table = "warehouse";
		int quantity = 0;
		int y = 0;
		String sql = "";
		String[] value = new String[9];
		
		try {
			
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(" SELECT `brand`,`description`,`make`,`model`,`manufacturer_part_number`, "
					+ "`tmedic_sku`, `tmedic_cost`, `retail_price`, `ebay_price`, `quantity`"
					+ " FROM " + schema + "." + table + " WHERE " + search);
			
			
			while (rs.next()) {
				
				y = 0;
				value[y++] = rs.getString(y);
				value[y++] = rs.getString(y);
				value[y++] = rs.getString(y);
				value[y++] = rs.getString(y);
				value[y++] = rs.getString(y);
				value[y++] = rs.getString(y);
				value[y++] = rs.getString(y);
				value[y++] = rs.getString(y);
				value[y++] = rs.getString(y);
				quantity = Integer.parseInt(rs.getString(++y));
				
			}


			
			//Change table Warehouse
			quantity -= 1;
			
			sql = " UPDATE `" + schema + "`.`" + table + "` SET `quantity` = '" + quantity + "' WHERE " + search; 
			insertSQL(sql);
			
			if (quantity == 0) {
				
				sql = " UPDATE `" + schema + "`.`" + table + "` SET `in_stock` = 'N' WHERE " + search; 
				insertSQL(sql);
				
			}
			
			//Insert into Parts_Sold
			y = 0;
			table = "sold_parts";
			sql = "INSERT INTO `" + schema + "`.`" + table + "` VALUES ('" + value[y++] + "', '"+ value[y++] + "', '" + value[y++] + "', '" + value[y++] + "', '"
					+ value[y++] + "', '" + value[y++] + "', '" + value[y++] + "', '" + value[y++] + "', '" + value[y++] + "', '" + amt_sold_for + "')";
			insertSQL(sql);
			
		} catch(Exception e){			
			System.err.print("\nError:\n\t" + e);
		} finally {			
			if(stmt!= null) stmt.close();	
		} 
	}
	
	public static void addPart(String search) throws SQLException {

		Statement stmt = null;
		String table = "warehouse";
		int quantity = 0;
		
		try {
			
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("SELECT `quantity` FROM " + schema + "." + table + " WHERE " + search);
			
			while (rs.next()) {
				quantity = rs.getInt(quantity);
			}
			
			String sql = " UPDATE " + schema + "." + table + " SET `quantity` = '" + quantity + "' WHERE " + search;
			stmt.executeUpdate(sql);		
			
		} catch(Exception e){			
			System.err.print("\nError:\n\t" + e);
		} finally {			
			if(stmt!= null) stmt.close();	
		} 
	}
	
	public static void addNewPart() {
		
		Statement stmt = null;
		String table = "warehouse";

		String brand, description, make, model, part, sku, location, used, listed, cost, retail, ebay, quantity, stock;
		/*
		int option = 2;
		String search = " `manufacturer_part_number` LIKE '" + part 
				+ "' AND `description` LIKE '" + description + "' AND `model` LIKE '" + model + "'";
				
		try {
			
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
			
				String sql = "INSERT INTO " + schema + "." + table + " VALUES ('" + brand + "', '" + description + "', '"
						+ make + "', '" + model + "', '" + part + "', '" + sku + "', '" + location + "', '" + used + "', '" 
						+ listed + "', '" + cost + "', '" + retail + "', '" + ebay + "', '" + quantity + "', '" + stock + "')";  
				stmt.executeUpdate(sql);		
			
		} catch(Exception e){			
			System.err.print("\nError:\n\t" + e);
		} finally {			
			if(stmt!= null) stmt.close();	
			input.close();
		} */
	}

	public static int removePart() throws SQLException {

		Statement stmt = null;
		int loop  = 0;
		
		try {
			
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			//
				
			
			
		} catch (Exception e){			
			System.err.print("\nError:\n\t" + e);
		} finally {			
			if(stmt!= null) stmt.close();			
		}
		
		return loop;
		
	}
	
	public static String[] insertSQL(String sql) throws SQLException {

		Statement stmt = null;
		String[] result = {"0","None"};
		
		try {
			
			stmt = conn.createStatement();
			
			String sqlSafe = "SET SQL_SAFE_UPDATES = 0";
        	
			stmt.addBatch(sqlSafe);
			stmt.addBatch(sql);
			int[] r = stmt.executeBatch();
			result[0] = Integer.toString(r[1]);
			
		} catch(Exception e){
			//System.err.print("\nError:\n\t" + e);
			result[1] = e.toString();
		} finally {
			if(stmt!= null) stmt.close();
		}	
		
		return(result);
	}
	
	public static Table viewTable(String tableString, Table table, String[] search) throws SQLException {
		
		Statement stmt = null;

		try {

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(" SELECT * FROM " + schema + "." + tableString + " WHERE " + search[0] + search[1]);
			
		    for (int x = 1; x <= getColumnCount(tableString); x++) {
		    	
				TableColumn columns = new TableColumn(table, SWT.NULL);
				columns.setText(getColumnName(tableString, x));					
				
		    }
		    
		    if (tableString.equals("warehouse")) {
		    	
		    	while (rs.next()) {
			    	
			    	int y = 0;
			    	
			    	TableItem items = new TableItem(table, SWT.NULL);
			    	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
			    	items.setText(y++, rs.getString(y));
			    	items.setText(y++, rs.getString(y));
			    	items.setText(y++, rs.getString(y));
			    	items.setText(y++, rs.getString(y));
				   	items.setText(y, rs.getString(14));
					    
			    }
		    	
		    } else {
		    	
		    	while (rs.next()) {
			    	
			    	int y = 0;
			    	
			    	TableItem items = new TableItem(table, SWT.NULL);
			    	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
				   	items.setText(y++, rs.getString(y));
		    	
		    	}
		    }
		    		   
		    for (int z = 0; z < getColumnCount(tableString); z++) {
		    	
		      table.getColumn(z).pack();
		      
		    }
			
		} catch (Exception e) {		
			System.err.print("\nError:\n\t" + e);		
		} finally {			
			if(stmt!= null) stmt.close();
		}
		
		return (table);
	}
	
	public static String testMe(String table, String search) {
		return(" SELECT * FROM " + schema + "." + table + " WHERE " + search);
	}
}
