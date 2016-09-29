package me.Jack;

import java.sql.SQLException;
import java.lang.String;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;

public class SellPart extends Main {
	
	protected static Shell shell;
	private Text keywordText;
	public static String table = "warehouse";
	public String column = "all";
	public String column2 = "all";
	public static String keyWord = ";;;";
	public String keyWord2 = ";;;";
	public static String search = all(keyWord);
	public static Button sellButton;
	private Text keyword2Text;
	private Table outputTable;
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SellPart window = new SellPart();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		//Connects to Database
		try {
			getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		shell = new Shell();
		shell.setSize(1800, 900);
		shell.setText("Sell Part");
		shell.setLocation(30, 30);
		shell.setLayout(null);

		
		//Select Table
		Label tableLabel = new Label(shell, SWT.NONE);
		tableLabel.setBounds(5, 11, 61, 25);
		tableLabel.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		
		tableLabel.setText("Table:");
				
			ToolBar tableBar = new ToolBar(shell, SWT.BORDER);
			tableBar.setBounds(118, 5, 240, 37);
			tableBar.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
			
			final ToolItem warehouseRadio = new ToolItem(tableBar, SWT.RADIO);
			warehouseRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						table = "warehouse";
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			warehouseRadio.setSelection(true);
			warehouseRadio.setText("Warehouse");
			
			final ToolItem soldRadio = new ToolItem(tableBar, SWT.RADIO);
			soldRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						table = "sold_parts";
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			soldRadio.setText("Sold Parts");
						
			tableBar.pack();
			
			//Select Column
			Label columnLabel = new Label(shell, SWT.NONE);
			columnLabel.setBounds(5, 51, 107, 25);
			columnLabel.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
			columnLabel.setText("Column:   ");
		
			ToolBar columnBar = new ToolBar(shell, SWT.BORDER);
			columnBar.setBounds(118, 47, 1556, 34);
			columnBar.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
			
			final ToolItem allRadio = new ToolItem(columnBar, SWT.RADIO);
			allRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						search = all(keyWord) + search.substring(search.indexOf("   "));
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			allRadio.setSelection(true);
			allRadio.setText("All ");
			
			final ToolItem brandRadio = new ToolItem(columnBar, SWT.RADIO);
			brandRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						column = "brand";
						search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			brandRadio.setText("Brand ");
			
			final ToolItem descriptionRadio = new ToolItem(columnBar, SWT.RADIO);
			descriptionRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						column = "description";
						search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			descriptionRadio.setText("Description ");
			
			final ToolItem makeRadio = new ToolItem(columnBar, SWT.RADIO);
			makeRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						column = "make";
						search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			makeRadio.setText("Make ");
			
			final ToolItem modelRadio = new ToolItem(columnBar, SWT.RADIO);
			modelRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						column = "model";
						search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			modelRadio.setText("Model ");
			
			final ToolItem manufacturer_part_numberRadio = new ToolItem(columnBar, SWT.RADIO);
			manufacturer_part_numberRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						column = "manufacturer_part_number";
						search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			manufacturer_part_numberRadio.setText("Part Number ");
			
			final ToolItem tmedic_skuRadio = new ToolItem(columnBar, SWT.RADIO);
			tmedic_skuRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						column = "tmedic_sku";
						search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			tmedic_skuRadio.setText("SKU ");
			
			final ToolItem storage_locationRadio = new ToolItem(columnBar, SWT.RADIO);
			storage_locationRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (table.equals("sold_parts")) {
						allRadio.setSelection(true);
						storage_locationRadio.setSelection(false);
						column = "all";
					} else {
						try {
							column = "storage_location";
							search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
							outputTable.removeAll();
							outputTable = viewTable(table, outputTable,  search);
							onlyOne();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			storage_locationRadio.setText("Location ");
			
			final ToolItem new_or_usedRadio = new ToolItem(columnBar, SWT.RADIO);
			new_or_usedRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (table.equals("sold_parts")) {
						allRadio.setSelection(true);
						new_or_usedRadio.setSelection(false);
						column = "all";
					} else {
						try {
							column = "new_or_used";
							search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
							outputTable.removeAll();
							outputTable = viewTable(table, outputTable,  search);
							onlyOne();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			new_or_usedRadio.setText("New or Used ");
			
			final ToolItem part_listedRadio = new ToolItem(columnBar, SWT.RADIO);
			part_listedRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (table.equals("sold_parts")) {
						allRadio.setSelection(true);
						part_listedRadio.setSelection(false);
						column = "all";
					} else {
						try {
							column = "part_listed";
							search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
							outputTable.removeAll();
							outputTable = viewTable(table, outputTable,  search);
							onlyOne();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			part_listedRadio.setText("Listed ");
			
			final ToolItem tmedic_costRadio = new ToolItem(columnBar, SWT.RADIO);
			tmedic_costRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						column = "tmedic_cost";
						search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			tmedic_costRadio.setText("Tmedic Cost ");
			
			final ToolItem retail_priceRadio = new ToolItem(columnBar, SWT.RADIO);
			retail_priceRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						column = "retail_price";
						search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			retail_priceRadio.setText("Retail Price ");
			
			final ToolItem ebay_priceRadio = new ToolItem(columnBar, SWT.RADIO);
			ebay_priceRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						column = "ebay_price";
						search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			ebay_priceRadio.setText("Ebay Price ");
			
			final ToolItem quantityRadio = new ToolItem(columnBar, SWT.RADIO);
			quantityRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (table.equals("sold_parts")) {
						allRadio.setSelection(true);
						quantityRadio.setSelection(false);
						column = "all";
					} else {
						try {
							column = "quantity";
							search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
							outputTable.removeAll();
							outputTable = viewTable(table, outputTable,  search);
							onlyOne();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			quantityRadio.setText("Quantity ");
			
			final ToolItem in_stockRadio = new ToolItem(columnBar, SWT.RADIO);
			in_stockRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (table.equals("sold_parts")) {
						allRadio.setSelection(true);
						in_stockRadio.setSelection(false);
						column = "all";
					} else {					
						try {
							column = "in_stock";
							search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
							outputTable.removeAll();
							outputTable = viewTable(table, outputTable,  search);
							onlyOne();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			in_stockRadio.setText("In Stock ");
			
			final ToolItem amt_sold_forRadio = new ToolItem(columnBar, SWT.RADIO);
			amt_sold_forRadio.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (table.equals("warehouse")) {
						allRadio.setSelection(true);
						amt_sold_forRadio.setSelection(false);
						column = "all";
					} else {
						try {
							column = "amt_sold_for";
							search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
							outputTable.removeAll();
							outputTable = viewTable(table, outputTable,  search);
							onlyOne();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			amt_sold_forRadio.setText("Amount Sold For ");
			
			columnBar.pack();
		
		//Keyword
		Label keywordLabel = new Label(shell, SWT.NONE);
		keywordLabel.setBounds(5, 89, 95, 25);
		keywordLabel.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		keywordLabel.setText("Keyword:");

		keywordText = new Text(shell, SWT.BORDER | SWT.WRAP);
		keywordText.setBounds(118, 86, 368, 31);
		
		//Second search enabler button
		final Button secondButton = new Button(shell, SWT.NONE);
		secondButton.setBounds(118, 122, 200, 28);
		secondButton.setFont(SWTResourceManager.getFont("Verdana", 9, SWT.NORMAL));
		secondButton.setText("Toggle Second Search");
		
		//Select column2
		final Label column2Label = new Label(shell, SWT.NONE);
		column2Label.setBounds(5, 158, 106, 25);
		column2Label.setText("Column2: ");
		column2Label.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		column2Label.setVisible(false);
		
		final ToolBar column2Bar = new ToolBar(shell, SWT.BORDER);
		column2Bar.setBounds(118, 155, 1524, 32);
		column2Bar.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
		column2Bar.setVisible(false);
		
		final ToolItem all2Radio = new ToolItem(column2Bar, SWT.RADIO);
		all2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					search = search.substring(0, search.indexOf("   ")) + "   AND " + all(keyWord2);
					outputTable.removeAll();
					outputTable = viewTable(table, outputTable,  search);
					onlyOne();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		all2Radio.setSelection(true);
		all2Radio.setText("All  ");
		
		final ToolItem brand2Radio = new ToolItem(column2Bar, SWT.RADIO);
		brand2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					column2 = "brand";
					search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
					outputTable.removeAll();
					outputTable = viewTable(table, outputTable,  search);
					onlyOne();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		brand2Radio.setText("Brand ");
		
		final ToolItem description2Radio = new ToolItem(column2Bar, SWT.RADIO);
		description2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					column2 = "description";
					search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
					outputTable.removeAll();
					outputTable = viewTable(table, outputTable,  search);
					onlyOne();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		description2Radio.setText("Description ");
		
		final ToolItem make2Radio = new ToolItem(column2Bar, SWT.RADIO);
		make2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					column2 = "make";					
					search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
					outputTable.removeAll();
					outputTable = viewTable(table, outputTable,  search);
					onlyOne();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		make2Radio.setText("Make ");
		
		final ToolItem model2Radio = new ToolItem(column2Bar, SWT.RADIO);
		model2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					column2 = "model";
					search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
					outputTable.removeAll();
					outputTable = viewTable(table, outputTable,  search);
					onlyOne();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		model2Radio.setText("Model ");
		
		final ToolItem manufacturer_part_number2Radio = new ToolItem(column2Bar, SWT.RADIO);
		manufacturer_part_number2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					column2 = "manufacturer_part_number";
					search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
					outputTable.removeAll();
					outputTable = viewTable(table, outputTable,  search);
					onlyOne();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		manufacturer_part_number2Radio.setText("Part Number ");
		
		final ToolItem tmedic_sku2Radio = new ToolItem(column2Bar, SWT.RADIO);
		tmedic_sku2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					column2 = "tmedic_sku";
					search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
					outputTable.removeAll();
					outputTable = viewTable(table, outputTable,  search);
					onlyOne();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		tmedic_sku2Radio.setText("SKU ");
		
		final ToolItem storage_location2Radio = new ToolItem(column2Bar, SWT.RADIO);
		storage_location2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					all2Radio.setSelection(true);
					storage_location2Radio.setSelection(false);
					column2 = "all";
				} else {
					try {
						column2 = "storage_location";
						search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		storage_location2Radio.setText("Location ");
		
		final ToolItem new_or_used2Radio = new ToolItem(column2Bar, SWT.RADIO);
		new_or_used2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					all2Radio.setSelection(true);
					new_or_used2Radio.setSelection(false);
					column2 = "all";
				} else {
					try {
						column2 = "new_or_used";
						search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		new_or_used2Radio.setText("New or Used ");
		
		final ToolItem part_listed2Radio = new ToolItem(column2Bar, SWT.RADIO);
		part_listed2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					all2Radio.setSelection(true);
					part_listed2Radio.setSelection(false);
					column2 = "all";
				} else {
					try {
						column2 = "part_listed";
						search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		part_listed2Radio.setText("Listed ");
		
		final ToolItem tmedic_cost2Radio = new ToolItem(column2Bar, SWT.RADIO);
		tmedic_cost2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					column2 = "tmedic_cost";
					search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
					outputTable.removeAll();
					outputTable = viewTable(table, outputTable,  search);
					onlyOne();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		tmedic_cost2Radio.setText("Tmedic Cost ");
		
		final ToolItem retail_price2Radio = new ToolItem(column2Bar, SWT.RADIO);
		retail_price2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					column2 = "retail_price";
					search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
					outputTable.removeAll();
					outputTable = viewTable(table, outputTable,  search);
					onlyOne();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		retail_price2Radio.setText("Retail Price ");
		
		final ToolItem ebay_price2Radio = new ToolItem(column2Bar, SWT.RADIO);
		ebay_price2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					column2 = "ebay_price";
					search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
					outputTable.removeAll();
					outputTable = viewTable(table, outputTable,  search);
					onlyOne();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		ebay_price2Radio.setText("Ebay Price ");
		
		final ToolItem quantity2Radio = new ToolItem(column2Bar, SWT.RADIO);
		quantity2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					all2Radio.setSelection(true);
					quantity2Radio.setSelection(false);
					column2 = "all";
				} else {
					try {
						column2 = "quantity";
						search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		quantity2Radio.setText("Quantity ");
		
		final ToolItem in_stock2Radio = new ToolItem(column2Bar, SWT.RADIO);
		in_stock2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					all2Radio.setSelection(true);
					in_stock2Radio.setSelection(false);
					column2 = "all";
				} else {					
					try {
						column2 = "in_stock";
						search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		in_stock2Radio.setText("In Stock ");
		
		final ToolItem amt_sold_for2Radio = new ToolItem(column2Bar, SWT.RADIO);
		amt_sold_for2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("warehouse")) {
					all2Radio.setSelection(true);
					amt_sold_for2Radio.setSelection(false);
					column2 = "all";
				} else {
					try {
						column2 = "amt_sold_for";
						search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		amt_sold_for2Radio.setText("Amount Sold For ");
		
		column2Bar.pack();
		
		//Keyword2
		final Label keyword2Label = new Label(shell, SWT.NONE);
		keyword2Label.setBounds(5, 195, 108, 25);
		keyword2Label.setText("Keyword2:");
		keyword2Label.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		keyword2Label.setVisible(false);
		
		keyword2Text = new Text(shell, SWT.BORDER);
		keyword2Text.setBounds(118, 192, 368, 31);
		keyword2Text.setVisible(false);
		
		//Listener for second search enabler button
		secondButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (column2Label.isVisible()) {			
					column2Label.setVisible(false);
					column2Bar.setVisible(false);
					keyword2Label.setVisible(false);
					keyword2Text.setVisible(false);

					search = search.substring(0,  3 + (search.indexOf("   ")));		//removes the second search term
					outputTable.removeAll();
					try {
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				} else {
					column2Label.setVisible(true);
					column2Bar.setVisible(true);
					keyword2Label.setVisible(true);
					keyword2Text.setVisible(true);
					
					search = search.substring(0, search.indexOf("   "));		//adds the previously used second search
					if (!(column2.equals("all"))) {
						search += "   AND " + getSearch(column2, keyWord);
					} else {
						search += "   AND " + all(keyWord2);
					}
					outputTable.removeAll();
					try {
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}	
				}
			}
		});
		
		outputTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		outputTable.setBounds(88, 260, 1757, 493);
		outputTable.setFont(SWTResourceManager.getFont("Verdana", 9, SWT.NORMAL));
		outputTable.setHeaderVisible(true);
		outputTable.setLinesVisible(true);
		
		//Listener for keyword text box
		keywordText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try {
					keyWord = keywordText.getText();
					if (keyWord.length() > 1 && keyWord != "") {
						if (!(column.equals("all"))) {
							search = getSearch(column, keyWord) + search.substring(search.indexOf("   "));
						} else {
							search = all(keyWord) + search.substring(search.indexOf("   "));
						}
						
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} else {
						outputTable.removeAll();
					}	
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		keywordText.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		
		keyword2Text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				try {
					keyWord2 = keyword2Text.getText();
					if (keyWord2.length() > 1 && keyWord2 != "") {
						if (!(column2.equals("all"))) {
							search = search.substring(0, search.indexOf("   ")) + "   AND " + getSearch(column2, keyWord2);
						} else {
							search = search.substring(0, search.indexOf("   ")) + "   AND " + all(keyWord2);
						}
						
						outputTable.removeAll();
						outputTable = viewTable(table, outputTable,  search);
						onlyOne();
					} 		
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		keyword2Text.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		
		//Sell Button
		sellButton = new Button(shell, SWT.BORDER | SWT.CENTER);
		sellButton.setBounds(800, 770, 200, 60);
		sellButton.setEnabled(false);
		sellButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});
		sellButton.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		sellButton.setText("Sell!");
		
		
		//TEST BUTTON
		Button btnSearch = new Button(shell, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("\nBUTTON \n\t" + search);
			}
		});
		btnSearch.setBounds(247, 788, 90, 30);
		btnSearch.setText("Search");
		
	}
	
	
	public static void onlyOne() throws SQLException {
		
		int numRows = getRowsAffected(table, search);

		if (numRows == 1){
				
			sellButton.setEnabled(true);
			
		} else {
			
			sellButton.setEnabled(false);
			
		}	
	}
	
	public static String all(String word) {
		
		String s = " (`brand` LIKE '%" + word + "%'"
				+ " OR `description` LIKE '%" + word + "%'"
				+ " OR `make` LIKE '%" + word + "%'"
				+ " OR `model` LIKE '%" + word + "%'"
				+ " OR `manufacturer_part_number` LIKE '%" + word + "%'"
				+ " OR `tmedic_sku` LIKE '%" + word + "%'"
				+ " OR `tmedic_cost` LIKE '%" + word + "%'"
				+ " OR `retail_price` LIKE '%" + word + "%'"
				+ " OR `ebay_price` LIKE '%" + word + "%'";
		 	
				if (table.equals("warehouse")) {
					
					s += " OR `storage_location` LIKE '%" + word + "%'"
						+ " OR `new_or_used` LIKE '%" + word + "%'"
						+ " OR `part_listed` LIKE '%" + word + "%'"
						+ " OR `in_stock` LIKE '%" + word + "%'"
						+ " OR `quantity` LIKE '%" + word + "%')    ";
					
				} else {
					
					s += " OR `amt_sold_for` LIKE '%" + word + "%')    ";
					
				}
				
		return (s);
	}
}
