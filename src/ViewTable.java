package me.Jack;

import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class ViewTable extends Main {
	
	protected static Shell shell;
	private static Label tableLabel;
	private static ToolBar tableBar;
	private static Label columnLabel;
	private static ToolBar columnBar;
	ToolItem allRadio = null, all2Radio = null;
	ToolItem storage_locationRadio = null, storage_location2Radio = null;
	ToolItem new_or_usedRadio = null, new_or_used2Radio = null;
	ToolItem part_listedRadio = null, part_listed2Radio = null;
	ToolItem quantityRadio = null, quantity2Radio = null;
	ToolItem in_stockRadio = null, in_stock2Radio = null;
	ToolItem amt_sold_forRadio = null, amt_sold_for2Radio = null;
	private static Label keywordLabel;
	private static Text keywordText;
	private static Button secondButton;
	private static Label column2Label;
	private static ToolBar column2Bar;
	private static Label keyword2Label;
	private static Text keyword2Text;
	private static Label hintLabel;
	public static String table = "warehouse";
	public String column = "all";
	public String column2 = "all";
	public static String keyWord = ";;;";
	public String keyWord2 = ";;;";
	public static String[] search = {"", ""};
	private TableCursor outputTableCursor;
	private ControlEditor outputTableEditor;
	private static int columnChanged = 0;
	private static String searchUpdate, columnName;
	private static Button soldButton, addPartButton, addNewPartButton, removePartButton;
	private static Button insertSQLButton, cancelButton, goSoldButton, goAddButton;
	private static Button goAddNewButton, goRemoveButton, goInsertSQLButton;
	private static Table outputWarehouseTable;
	private static Table addNewPartTable;
	private static Label soldLabel;
	private static Text amt_sold_forText;
	private static Label addLabel;
	private static Label removeLabel;
	private static Label insertSQLLabel;
	private static Text insertSQLText;
	private static Text sqlResultsText;
	private static Label messageLabel;
	private static Label resultsLabel;
	private static Label addNewPartLabel;
	private static Text addNewPartText;
	

	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ViewTable window = new ViewTable();
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
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		
		//Connects to Database
		try {
			getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		shell = new Shell();
		shell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {				
				System.out.println("\n" + table);
				System.out.println("\ns1\t" + search[0]);
				System.out.println("\ns2\t" + search[1]);
			}
		});
		shell.setSize(1800, 900);
		shell.setText("Parts Inventory");
		shell.setLocation(30, 30);
		shell.setLayout(null);
	
		search[0] = all(keyWord);
		
		//Select Table
		tableLabel = new Label(shell, SWT.NONE);
		tableLabel.setBounds(5, 11, 61, 25);
		tableLabel.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		
		tableLabel.setText("Table:");
				
		tableBar = new ToolBar(shell, SWT.BORDER);
		tableBar.setBounds(120, 5, 240, 37);
		tableBar.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		
		final ToolItem warehouseRadio = new ToolItem(tableBar, SWT.RADIO);
		warehouseRadio.setToolTipText("");
		warehouseRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				table = "warehouse";
				//search = all(keyWord) + search.substring(search.indexOf("   "));
					
				if (amt_sold_forRadio.getSelection() || amt_sold_for2Radio.getSelection()) {
						
					allRadio.setSelection(true);
					all2Radio.setSelection(true);
					search[0] = all(keyWord);							
					search[1] = " AND " + all(keyWord2);
						
				}
				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {	
						
					updateTable();
					
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
				
				table = "sold_parts";
	
				//if column is not present in this table, switch selection to all
				if (storage_locationRadio.getSelection() || storage_location2Radio.getSelection()
					|| new_or_usedRadio.getSelection() || new_or_used2Radio.getSelection()
					|| part_listedRadio.getSelection() || part_listed2Radio.getSelection()
					|| quantityRadio.getSelection() || quantity2Radio.getSelection()
					|| in_stockRadio.getSelection() || in_stock2Radio.getSelection()) {
						
					allRadio.setSelection(true);
					all2Radio.setSelection(true);
					search[0] = all(keyWord);							
					search[1] = " AND " + all(keyWord2);
						
				}
					
				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
								
				try {	
					
					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		soldRadio.setText("Sold Parts");
					
		tableBar.pack();
		
		//Select Column
		columnLabel = new Label(shell, SWT.NONE);
		columnLabel.setBounds(5, 51, 107, 25);
		columnLabel.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		columnLabel.setText("Column:   ");
	
		columnBar = new ToolBar(shell, SWT.BORDER);
		columnBar.setBounds(120, 47, 1556, 34);
		columnBar.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
		
		allRadio = new ToolItem(columnBar, SWT.RADIO);
		allRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				search[0] = all(keyWord);
				
				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
				
				try {
					
					updateTable();
					
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
				
				column = "brand";
				search[0] = getSearch(column, keyWord);
				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {
						
					updateTable();
						
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
				
				column = "description";
				search[0] = getSearch(column, keyWord);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
						
				try {
							
					updateTable();
							
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
				
				column = "make";
				search[0] = getSearch(column, keyWord);
				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
						
				try {
							
					updateTable();
							
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
				
				column = "model";
				search[0] = getSearch(column, keyWord);
				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
						
				try {
							
					updateTable();
						
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
				
				column = "manufacturer_part_number";
				search[0] = getSearch(column, keyWord);
					soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {
							
						updateTable();
							
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
				
				column = "tmedic_sku";
				search[0] = getSearch(column, keyWord);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {
						
					updateTable();
						
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		tmedic_skuRadio.setText("SKU ");
		
		storage_locationRadio = new ToolItem(columnBar, SWT.RADIO);
		storage_locationRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					
					allRadio.setSelection(true);
					storage_locationRadio.setSelection(false);
					search[0] = all(keyWord);
					
				} else {
						
					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);
					column = "storage_location";
					search[0] = getSearch(column, keyWord);	
					
				}
				
				try {
					
					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		storage_locationRadio.setText("Location ");
		
		new_or_usedRadio = new ToolItem(columnBar, SWT.RADIO);
		new_or_usedRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					
					allRadio.setSelection(true);
					new_or_usedRadio.setSelection(false);
					search[0] = all(keyWord);
					
				} else {
					
					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);
					column = "new_or_used";
					search[0] = getSearch(column, keyWord);	
					
				}	
				
				try {
					
					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		new_or_usedRadio.setText("New or Used ");
		
		part_listedRadio = new ToolItem(columnBar, SWT.RADIO);
		part_listedRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					
					allRadio.setSelection(true);
					part_listedRadio.setSelection(false);
					search[0] = all(keyWord);
					
				} else {
					
					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);
					column = "part_listed";
					search[0] = getSearch(column, keyWord);	
					
				}
				
				try {
					
					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		part_listedRadio.setText("Listed ");
		
		final ToolItem tmedic_costRadio = new ToolItem(columnBar, SWT.RADIO);
		tmedic_costRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				column = "tmedic_cost";
				search[0] = getSearch(column, keyWord);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {
						
					updateTable();
						
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
				
				column = "retail_price";
				search[0] = getSearch(column, keyWord);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
						
				try {
						
					updateTable();
						
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
			
				column = "ebay_price";
				search[0] = getSearch(column, keyWord);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
						
				try {
						
					updateTable();
						
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		ebay_priceRadio.setText("Ebay Price ");
		
		quantityRadio = new ToolItem(columnBar, SWT.RADIO);
		quantityRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					
					allRadio.setSelection(true);
					quantityRadio.setSelection(false);
					search[0] = all(keyWord);
						
				} else {
					
					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);
					column = "quantity";
					search[0] = getSearch(column, keyWord);	
					
				}
				
				try {
					
					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		quantityRadio.setText("Quantity ");
		
		in_stockRadio = new ToolItem(columnBar, SWT.RADIO);
		in_stockRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					
					allRadio.setSelection(true);
					in_stockRadio.setSelection(false);
					search[0] = all(keyWord);
					
				} else {	
					
					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);
					column = "in_stock";
					search[0] = getSearch(column, keyWord);	
					
				}
				
				try {
					
						updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		in_stockRadio.setText("In Stock ");
		
		amt_sold_forRadio = new ToolItem(columnBar, SWT.RADIO);
		amt_sold_forRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("warehouse")) {
					
					allRadio.setSelection(true);
					amt_sold_forRadio.setSelection(false);
					search[0] = all(keyWord);
					
				} else {
					
					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);
					column = "amt_sold_for";
					search[0] = getSearch(column, keyWord);
					
				}
				
				try {
					
					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		amt_sold_forRadio.setText("Amount Sold For ");
		
		columnBar.pack();
	
		//Keyword
		keywordLabel = new Label(shell, SWT.NONE);
		keywordLabel.setBounds(5, 89, 95, 25);
		keywordLabel.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		keywordLabel.setText("Keyword:");
		keywordText = new Text(shell, SWT.BORDER | SWT.WRAP);
		keywordText.setBounds(120, 86, 368, 31);
		
		//Second search enabler button
		secondButton = new Button(shell, SWT.NONE);
		secondButton.setBounds(120, 122, 200, 28);
		secondButton.setFont(SWTResourceManager.getFont("Verdana", 9, SWT.NORMAL));
		secondButton.setText("Toggle Second Search");
		
		//Select column2
		column2Label = new Label(shell, SWT.NONE);
		column2Label.setBounds(5, 158, 106, 25);
		column2Label.setText("Column2: ");
		column2Label.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		column2Label.setVisible(false);
		
		column2Bar = new ToolBar(shell, SWT.BORDER);
		column2Bar.setBounds(120, 155, 1524, 32);
		column2Bar.setFont(SWTResourceManager.getFont("Verdana", 11, SWT.NORMAL));
		column2Bar.setVisible(false);
		
		final ToolItem all2Radio = new ToolItem(column2Bar, SWT.RADIO);
		all2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				search[1] = " AND " + all(keyWord2);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
						
				try {
							
					updateTable();
						
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
				
				column2 = "brand";
				search[1] = " AND " + getSearch(column2, keyWord2);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {
						
					updateTable();
						
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
				
				column2 = "description";
				search[1] = " AND " + getSearch(column2, keyWord2);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {
						
					updateTable();
						
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
				
				column2 = "make";					
				search[1] = " AND " + getSearch(column2, keyWord2);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {
						
					updateTable();
						
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
				
				column2 = "model";
				search[1] = " AND " + getSearch(column2, keyWord2);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {
						
					updateTable();
						
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
				
				column2 = "manufacturer_part_number";
				search[1] = " AND " + getSearch(column2, keyWord2);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {
						
					updateTable();
						
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
				
				column2 = "tmedic_sku";
				search[1] = " AND " + getSearch(column2, keyWord2);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {
						
					updateTable();
						
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		tmedic_sku2Radio.setText("SKU ");
		
		storage_location2Radio = new ToolItem(column2Bar, SWT.RADIO);
		storage_location2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					
					all2Radio.setSelection(true);
					storage_location2Radio.setSelection(false);
					column2 = "all";
					
				} else {
					
					column2 = "storage_location";
					search[1] = " AND " + getSearch(column2, keyWord2);

					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);
				}
				
				try {
						
					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		storage_location2Radio.setText("Location ");
		
		new_or_used2Radio = new ToolItem(column2Bar, SWT.RADIO);
		new_or_used2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					
					all2Radio.setSelection(true);
					new_or_used2Radio.setSelection(false);
					column2 = "all";
					
				} else {
					
					column2 = "new_or_used";
					search[1] = " AND " + getSearch(column2, keyWord2);

					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);
				}
				
				try {
							
					updateTable();
						
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		new_or_used2Radio.setText("New or Used ");
		
		part_listed2Radio = new ToolItem(column2Bar, SWT.RADIO);
		part_listed2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					
					all2Radio.setSelection(true);
					part_listed2Radio.setSelection(false);
					column2 = "all";
					
				} else {
				
					column2 = "listed";
					search[1] = " AND " + getSearch(column2, keyWord2);

					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);
				}
				
				try {
								
					updateTable();
							
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		part_listed2Radio.setText("Listed ");
		
		final ToolItem tmedic_cost2Radio = new ToolItem(column2Bar, SWT.RADIO);
		tmedic_cost2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				column2 = "tmedic_cost";
				search[1] = " AND " + getSearch(column2, keyWord2);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
						
				try {
							
					updateTable();
						
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
				
				column2 = "retail_price";
				search[1] = " AND " + getSearch(column2, keyWord2);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
						
				try {
							
					updateTable();
							
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
			
				column2 = "ebay_price";
				search[1] = " AND " + getSearch(column2, keyWord2);

				soldButton.setEnabled(false);
				addPartButton.setEnabled(false);
				removePartButton.setEnabled(false);
					
				try {
							
					updateTable();
							
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		ebay_price2Radio.setText("Ebay Price ");
		
		quantity2Radio = new ToolItem(column2Bar, SWT.RADIO);
		quantity2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					
					all2Radio.setSelection(true);
					quantity2Radio.setSelection(false);
					column2 = "all";
					
				} else {
					
					column2 = "quantity";
					search[1] = " AND " + getSearch(column2, keyWord2);
					
					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);
				}
				
				try {
								
					updateTable();
							
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		quantity2Radio.setText("Quantity ");
		
		in_stock2Radio = new ToolItem(column2Bar, SWT.RADIO);
		in_stock2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("sold_parts")) {
					
					all2Radio.setSelection(true);
					in_stock2Radio.setSelection(false);
					column2 = "all";
					
				} else {	
					
					column2 = "in_stock";
					search[1] = " AND " + getSearch(column2, keyWord2);

					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);
				}
				
				try {
								
					updateTable();
							
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		in_stock2Radio.setText("In Stock ");
		
		amt_sold_for2Radio = new ToolItem(column2Bar, SWT.RADIO);
		amt_sold_for2Radio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (table.equals("warehouse")) {
					
					all2Radio.setSelection(true);
					amt_sold_for2Radio.setSelection(false);
					search[1] = " AND " + all(keyWord2);
					
				} else {
					
					column2 = "amt_sold_for";
					search[1] = " AND " + getSearch(column2, keyWord2);
					
					soldButton.setEnabled(false);
					addPartButton.setEnabled(false);
					removePartButton.setEnabled(false);					
					
				}
				
				try {
					
					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}				
			}
		});
		amt_sold_for2Radio.setText("Amount Sold For ");
		
		column2Bar.pack();
		
		//Keyword2
		keyword2Label = new Label(shell, SWT.NONE);
		keyword2Label.setBounds(5, 195, 108, 25);
		keyword2Label.setText("Keyword2:");
		keyword2Label.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		keyword2Label.setVisible(false);
		
		keyword2Text = new Text(shell, SWT.BORDER);
		keyword2Text.setBounds(120, 192, 368, 31);
		keyword2Text.setVisible(false);
		
		//Listener for second search enabler button
		secondButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//if the button is enabled, disable buttons and remove search[1]
				if (column2Label.isVisible()) {			
					
					column2Label.setVisible(false);
					column2Bar.setVisible(false);
					keyword2Label.setVisible(false);
					keyword2Text.setVisible(false);

					search[1] = "";
					
					try {
						
						updateTable();
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				//if the button is disabled, enable buttons and add search[1]
				} else {
					column2Label.setVisible(true);
					column2Bar.setVisible(true);
					keyword2Label.setVisible(true);
					keyword2Text.setVisible(true);
					
					if (!(column2.equals("all"))) {
						search[1] = " AND " + getSearch(column2, keyWord);
					} else {
						search[1] = " AND " + all(keyWord2);
					}
					
					try {
						
						updateTable();
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}	
				}
			}
		});
		
		//Simple label
		hintLabel = new Label(shell, SWT.NONE);
		hintLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BORDER));
		hintLabel.setFont(SWTResourceManager.getFont("Verdana", 8, SWT.NORMAL));
		hintLabel.setBounds(1610, 234, 160, 20);
		hintLabel.setText("Press 'Enter' to edit cell");
		
		//Main viewing table
		outputWarehouseTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		outputWarehouseTable.setBounds(10, 260, 1765, 480);
		outputWarehouseTable.setFont(SWTResourceManager.getFont("Verdana", 9, SWT.NORMAL));
		outputWarehouseTable.setHeaderVisible(true);
		outputWarehouseTable.setLinesVisible(true);
		
		//
		//outputTable.setVisible(false);
		//
		
		outputWarehouseTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		outputWarehouseTable.setBounds(10, 260, 1765, 480);
		outputWarehouseTable.setFont(SWTResourceManager.getFont("Verdana", 9, SWT.NORMAL));
		outputWarehouseTable.setHeaderVisible(true);
		outputWarehouseTable.setLinesVisible(true);
		
		//Table for adding multiple new parts
		addNewPartTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		addNewPartTable.setBounds(60, 320, 1660, 380);
		addNewPartTable.setFont(SWTResourceManager.getFont("Verdana", 9, SWT.NORMAL));
		addNewPartTable.setHeaderVisible(true);
		addNewPartTable.setLinesVisible(true);
		
		//
		addNewPartTable.setVisible(false);
		//
		
		//Selecting Rows (help.Eclipse.org)
		outputTableCursor = new TableCursor(outputWarehouseTable, SWT.NONE);
		outputTableEditor = new ControlEditor(outputTableCursor);
		outputTableEditor.grabHorizontal = true;
		outputTableEditor.grabVertical = true;
		
		outputTableCursor.addSelectionListener(new SelectionAdapter() {
            // when the TableEditor is over a cell, select the corresponding row in 
            // the table
            public void widgetSelected(SelectionEvent e) {
            	outputWarehouseTable.setSelection(new TableItem[] {outputTableCursor.getRow()});

				soldButton.setEnabled(true);
				addPartButton.setEnabled(true);
				removePartButton.setEnabled(true);
				
            }
            // when the user hits "ENTER" in the TableCursor, pop up a text editor so that 
            // they can change the text of the cell
            public void widgetDefaultSelected(SelectionEvent e){
            	final Text text = new Text(outputTableCursor, SWT.NONE);
                TableItem row = outputTableCursor.getRow();
                columnChanged = outputTableCursor.getColumn();

                text.setText(row.getText(columnChanged));
                text.setSelection((row.getText(columnChanged)).length());
                text.addKeyListener(new KeyListener() {

                	@Override
                	public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
                		// close the text editor and copy the data over 
                		// when the user hits "ENTER"
                		if (e.character == SWT.CR) {
                			
                			TableItem row = outputTableCursor.getRow();
                			int column = outputTableCursor.getColumn();
                			
                			searchUpdate = getSearch(outputTableCursor);
                        	
                        	columnName = ((row.getParent()).getColumn(columnChanged)).toString();
                        	columnName = columnName.substring(columnName.indexOf("{") + 1, columnName.lastIndexOf("}"));
                        			
                        	String sql = update(table, columnName, text.getText(), searchUpdate);

                        	try {
                        		
								insertSQL(sql);

							} catch (SQLException e1) {
								e1.printStackTrace();
							}                     	                           
                              
                            row.setText(column, text.getText());
                                
                			text.dispose();
                			
                		}
                		
                		// close the text editor when the user hits "ESC"
                		if (e.character == SWT.ESC) {
                			text.dispose();
                		}							
					}

					@Override
					public void keyReleased(org.eclipse.swt.events.KeyEvent e) {															
					}
                }); 
                
                outputTableEditor.setEditor(text);
                text.setFocus();
                
            }
    });
						
		//Listener for keyword text box
		keywordText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				
				keyWord = keywordText.getText();
				if (keyWord.length() > 1 && keyWord != "") {
					if (!(column.equals("all"))) {
						search[0]= getSearch(column, keyWord);
					} else {
						search[0] = all(keyWord);
					}								
				}
					
				try {
					
					updateTable();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		keywordText.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		
		keyword2Text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				
				keyWord2 = keyword2Text.getText();
				if (keyWord2.length() > 1 && keyWord2 != "") {
					if (!(column2.equals("all"))) {
						search[1] = " AND " + getSearch(column2, keyWord2);
					} else {
						search[1] = " AND " + all(keyWord2);
					}	
				} 
					
				try {
					
					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		keyword2Text.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		
		//Modifier Buttons at bottom
		soldButton = new Button(shell, SWT.BORDER | SWT.CENTER);
		soldButton.setBounds(120, 770, 180, 60);
		soldButton.setEnabled(false);
		soldButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					
				toggleTopButtons();

				cancelButton.setVisible(true);
				soldButton.setVisible(false);
				addPartButton.setVisible(false);
				addNewPartButton.setVisible(false);
				removePartButton.setVisible(false);
				insertSQLButton.setVisible(false);
				messageLabel.setVisible(false);

				goSoldButton.setVisible(true);	
				amt_sold_forText.setText("");
				amt_sold_forText.setVisible(true);
				soldLabel.setVisible(true);
			}
		});
		soldButton.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		soldButton.setText("Part Sold");
		
		addPartButton = new Button(shell, SWT.NONE);
		addPartButton.setEnabled(false);
		addPartButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				toggleTopButtons();

				cancelButton.setVisible(true);
				soldButton.setVisible(false);
				addPartButton.setVisible(false);
				addNewPartButton.setVisible(false);
				removePartButton.setVisible(false);
				insertSQLButton.setVisible(false);
				messageLabel.setVisible(false);

				goAddButton.setVisible(true);
				addLabel.setVisible(true);

			}
		});
		addPartButton.setBounds(706, 770, 180, 60);
		addPartButton.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		addPartButton.setText("Add Part");	
		
		//
		//addPartButton.setVisible(false);
		//
		
		addNewPartButton = new Button(shell, SWT.NONE);
		addNewPartButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				toggleTopButtons();

				cancelButton.setVisible(true);
				soldButton.setVisible(false);
				outputWarehouseTable.setVisible(false);
				addPartButton.setVisible(false);
				addNewPartButton.setVisible(false);
				removePartButton.setVisible(false);
				insertSQLButton.setVisible(false);
				resultsLabel.setVisible(false);
				hintLabel.setVisible(false);
				messageLabel.setVisible(false);

				addNewPartLabel.setVisible(true);
				addNewPartText.setVisible(true);
				addNewPartButton.setVisible(true);
				goAddNewButton.setVisible(true);
				
				
						
			}
		});
		addNewPartButton.setBounds(412, 770, 180, 60);
		
		//
		//addNewPartButton.setVisible(false);	
		//
		
		addNewPartButton.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		addNewPartButton.setText("Add New Part");
		
		removePartButton = new Button(shell, SWT.NONE);
		removePartButton.setEnabled(false);
		removePartButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				toggleTopButtons();

				cancelButton.setVisible(true);
				soldButton.setVisible(false);
				addPartButton.setVisible(false);
				addNewPartButton.setVisible(false);
				removePartButton.setVisible(false);
				insertSQLButton.setVisible(false);
				messageLabel.setVisible(false);
				
				goRemoveButton.setVisible(true);
				removeLabel.setVisible(true);
			}
		});
		removePartButton.setBounds(1000, 770, 180, 60);
		
		//
		//removePartButton.setVisible(false);
		//
		
		removePartButton.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		removePartButton.setText("Remove Part");

		insertSQLButton = new Button(shell, SWT.NONE);
		insertSQLButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				toggleTopButtons();

				cancelButton.setVisible(true);
				soldButton.setVisible(false);
				addPartButton.setVisible(false);
				addNewPartButton.setVisible(false);
				removePartButton.setVisible(false);
				insertSQLButton.setVisible(false);
				messageLabel.setVisible(false);
				
				goInsertSQLButton.setVisible(true);				
				insertSQLLabel.setVisible(true);
				insertSQLText.setVisible(true);
				sqlResultsText.setVisible(true);

				
			}
		});
		insertSQLButton.setBounds(1652, 810, 120, 30);
		insertSQLButton.setFont(SWTResourceManager.getFont("Verdana", 10, SWT.NORMAL));
		insertSQLButton.setText("Insert SQL");
		
		//Cancel
		cancelButton = new Button(shell, SWT.BORDER | SWT.CENTER);
		cancelButton.setBounds(80, 770, 180, 60);
		cancelButton.setVisible(false);
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			
				cancelButton.setText("Cancel");	
					
				toggleTopButtons();
				resetButtons();
				messageLabel.setText("Canceled");
				
			}
		});
		cancelButton.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		cancelButton.setText("Cancel");
		
		//Completes the action
		goSoldButton = new Button(shell, SWT.NONE);
		goSoldButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				String amt_sold_for = amt_sold_forText.getText();
				String searchSold = getSearch(outputTableCursor);

				try {
					
					sellPart(amt_sold_for, searchSold);
					
					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				toggleTopButtons();
				resetButtons();
				messageLabel.setText("Part Sold");
				
			}
		});
		goSoldButton.setBounds(1520, 770, 180, 60);
		goSoldButton.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		goSoldButton.setText("GO!");
		goSoldButton.setVisible(false);
		
		goAddButton = new Button(shell, SWT.NONE);
		goAddButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String searchAdd = getSearch(outputTableCursor);
				try {
					addPart(searchAdd);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				toggleTopButtons();
				resetButtons();
				messageLabel.setText("Part Added?");
				
			}
		});
		goAddButton.setBounds(1520, 770, 180, 60);
		goAddButton.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		goAddButton.setText("GO!");
		goAddButton.setVisible(false);
		
		goAddNewButton = new Button(shell, SWT.NONE);
		goAddNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
								
				
				toggleTopButtons();
				resetButtons();
				messageLabel.setText("New Part Added?");

			}
		});
		goAddNewButton.setBounds(1520, 770, 180, 60);
		goAddNewButton.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		goAddNewButton.setText("GO!");
		goAddNewButton.setVisible(false);
		
		goRemoveButton = new Button(shell, SWT.NONE);
		goRemoveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				toggleTopButtons();
				resetButtons();
				messageLabel.setText("Part Removed?");

			}
		});
		goRemoveButton.setBounds(1520, 770, 180, 60);
		goRemoveButton.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		goRemoveButton.setText("GO!");
		goRemoveButton.setVisible(false);
			
		goInsertSQLButton = new Button(shell, SWT.NONE);
		goInsertSQLButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				String sql = insertSQLText.getText();
				String[] result = {"",""};
				
				try {
					
					result = insertSQL(sql);
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				sqlResultsText.setText("Rows Affected: " + result[0] + "  Message:" + result[1]);
				
				try {

					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				cancelButton.setText("Back");
				
			}
		});
		goInsertSQLButton.setBounds(1520, 770, 180, 60);
		goInsertSQLButton.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		goInsertSQLButton.setText("GO!");
		goInsertSQLButton.setVisible(false);	
		
		//Add Sold Part Objects
		soldLabel = new Label(shell, SWT.NONE);
		soldLabel.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		soldLabel.setBounds(600, 780, 500, 60);
		soldLabel.setText("How much did the part sell for?\t            $");
		soldLabel.setVisible(false);
		
		amt_sold_forText = new Text(shell, SWT.BORDER);
		amt_sold_forText.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		amt_sold_forText.setBounds(1100, 777, 100, 40);
		amt_sold_forText.setVisible(false);
		
		//Add Part Objects
		addLabel = new Label(shell, SWT.CENTER);
		addLabel.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		addLabel.setBounds(740, 780, 300, 40);
		addLabel.setText("Add another of this part?");
		addLabel.setVisible(false);
				
		//Add New Part Objects
		//Table is by outputTable
		
		addNewPartLabel = new Label(shell, SWT.NONE);
		addNewPartLabel.setFont(SWTResourceManager.getFont("Verdana", 12, SWT.NORMAL));
		addNewPartLabel.setBounds(120, 275, 350, 30);
		addNewPartLabel.setText("How many parts are you adding?");
		
		addNewPartText = new Text(shell, SWT.BORDER);
		addNewPartText.setFont(SWTResourceManager.getFont("Verdana", 10, SWT.NORMAL));
		addNewPartText.setBounds(480, 275, 50, 30);
		
		Button addNewPartTableButton = new Button(shell, SWT.NONE);
		addNewPartTableButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				int parts = Integer.parseInt(addNewPartText.getText());
				
				
				
			}
		});
		addNewPartTableButton.setFont(SWTResourceManager.getFont("Verdana", 10, SWT.NORMAL));
		addNewPartTableButton.setBounds(620, 275, 140, 30);
		addNewPartTableButton.setText("Create Table");
	
		//Add Remove Part Objects
		removeLabel = new Label(shell, SWT.NONE);
		removeLabel.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		removeLabel.setBounds(580, 780, 520, 40);
		removeLabel.setText("Are you sure you want to remove this part?");
		removeLabel.setVisible(false);
		
		//Add Insert SQL Objects
		insertSQLLabel = new Label(shell, SWT.NONE);
		insertSQLLabel.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		insertSQLLabel.setBounds(330, 780, 200, 60);
		insertSQLLabel.setText("SQL Statement:");
		insertSQLLabel.setVisible(false);
		
		insertSQLText = new Text(shell, SWT.BORDER);
		insertSQLText.setFont(SWTResourceManager.getFont("Verdana", 10, SWT.NORMAL));
		insertSQLText.setBounds(540, 750, 900, 35);
		insertSQLText.setVisible(false);

		sqlResultsText = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.CANCEL);
		sqlResultsText.setEditable(false);
		sqlResultsText.setDoubleClickEnabled(false);
		sqlResultsText.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		sqlResultsText.setFont(SWTResourceManager.getFont("Verdana", 9, SWT.NORMAL));
		sqlResultsText.setBounds(540, 795, 900, 50);
		sqlResultsText.setVisible(false);
	
		//Labels for the table
		messageLabel = new Label(shell, SWT.NONE);
		messageLabel.setVisible(false);
		messageLabel.setFont(SWTResourceManager.getFont("Verdana", 14, SWT.NORMAL));
		messageLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_SELECTION));
		messageLabel.setBounds(1294, 780, 280, 40);
		
		resultsLabel = new Label(shell, SWT.NONE);
		resultsLabel.setAlignment(SWT.CENTER);
		resultsLabel.setFont(SWTResourceManager.getFont("Verdana", 10, SWT.NORMAL));
		resultsLabel.setBounds(780, 220, 120, 30);
		resultsLabel.setText("0  Results");
		
		//
		resultsLabel.setVisible(false);
		hintLabel.setVisible(false);
		//

	}
		
	public static void resetButtons() {
		
		outputWarehouseTable.setVisible(true);
		
		resultsLabel.setVisible(true);
		hintLabel.setVisible(true);
		
		cancelButton.setVisible(false);
		soldButton.setVisible(true);
		addPartButton.setVisible(true);
		addNewPartButton.setVisible(true);
		removePartButton.setVisible(true);
		insertSQLButton.setVisible(true);
		
		goSoldButton.setVisible(false);
		goAddButton.setVisible(false);
		goAddNewButton.setVisible(false);
		goRemoveButton.setVisible(false);
		goInsertSQLButton.setVisible(false);
		
		soldLabel.setVisible(false);
		amt_sold_forText.setVisible(false);
		
		addLabel.setVisible(false);
		
		addNewPartLabel.setVisible(false);
		addNewPartText.setVisible(false);
		addNewPartButton.setVisible(false);
		addNewPartTable.setVisible(false);
		
		removeLabel.setVisible(false);
		
		insertSQLLabel.setVisible(false);
		insertSQLText.setVisible(false);
		sqlResultsText.setVisible(false);
		
		resultsLabel.setVisible(true);
		hintLabel.setVisible(true);
		
		messageLabel.setVisible(true);

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
	
	public static void toggleTopButtons() {
		
		boolean toggle = !tableLabel.isEnabled();
	
		tableLabel.setEnabled(toggle);
		tableBar.setEnabled(toggle);
		columnLabel.setEnabled(toggle);
		columnBar.setEnabled(toggle);
		keywordLabel.setEnabled(toggle);
		keywordText.setEnabled(toggle);
		secondButton.setEnabled(toggle);
		column2Label.setEnabled(toggle);
		column2Bar.setEnabled(toggle);
		keyword2Label.setEnabled(toggle);
		keyword2Text.setEnabled(toggle);
		hintLabel.setEnabled(toggle);
		
	}
	
	public static void updateTable() throws SQLException {
		
		//Removes the message of the previous action
		messageLabel.setText("");
		
		//Disabled buttons that rely on a single row selected
		soldButton.setEnabled(false);
		addPartButton.setEnabled(false);
		removePartButton.setEnabled(false);	

		//Showing amount of results
		int results = getRowsAffected(table, search);
		
		//Grammar fix
		if (results == 1) {
			
			resultsLabel.setText(results + "  Result");
			
		} else {
			
			resultsLabel.setText(results + "  Results");
			
		}
		
		try {
						
		outputWarehouseTable.removeAll();					
		outputWarehouseTable = viewTable(table, outputWarehouseTable,  search);													
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
}
