import java.io.BufferedWriter;
import javafx.scene.text.Text;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Mainn extends Application {
	static YearsList yearList = new YearsList();
	static ElectricityList records = new ElectricityList();
	// File file = new File("C:\\Users\\hp\\Downloads\\Electricity.csv");
	static TableView<Electricity> electricityTable = new TableView<>();

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		TabPane tp = new TabPane();
		Tab tab1 = new Tab("Load File");
		BorderPane bp = new BorderPane();
		Button btn1 = new Button("Load Electricity File");
		btn1.setAlignment(Pos.TOP_CENTER);
		btn1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		// TableView<Electricity> electricityTable = new TableView<>();

		TableColumn<Electricity, LocalDate> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<Electricity, LocalDate>("date"));

		TableColumn<Electricity, Double> israeliColumn = new TableColumn<>("Israeli Lines");
		israeliColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("israeli_lines"));

		TableColumn<Electricity, Double> gazaColumn = new TableColumn<>("Gaza Power Plant");
		gazaColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("gaza_power_plant"));

		TableColumn<Electricity, Double> egyptColumn = new TableColumn<>("Egyptian Lines");
		egyptColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("egyptian_lines"));

		TableColumn<Electricity, Double> totalColumn = new TableColumn<>("Total Daily Supply");
		totalColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("total_daily_supply"));

		TableColumn<Electricity, Double> overallColumn = new TableColumn<>("Overall Demand");
		overallColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("overall_demand"));

		TableColumn<Electricity, Double> powerColumn = new TableColumn<>("Power Cuts");
		powerColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("power_cuts"));

		TableColumn<Electricity, Double> tempColumn = new TableColumn<>("Temperature");
		tempColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("temp"));

		electricityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		electricityTable.getColumns().addAll(dateColumn, israeliColumn, gazaColumn, egyptColumn, totalColumn,
				overallColumn, powerColumn, tempColumn);
		bp.setTop(btn1);
		bp.setCenter(electricityTable);
		bp.setMargin(btn1, new javafx.geometry.Insets(10));
		bp.setMargin(electricityTable, new javafx.geometry.Insets(10));
		bp.setAlignment(btn1, Pos.TOP_CENTER);
		tab1.setContent(bp);
		btn1.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			File file = fc.showOpenDialog(stage);
			try {
				if (file.exists()) {
					Scanner scan = new Scanner(file);
					if (scan.hasNextLine()) {
						scan.nextLine();
					}
					String line = ",";
					while (scan.hasNextLine()) {
						line = scan.nextLine();
						Electricity el = new Electricity(line);
						Integer fileYear = el.getDate().getYear();
						Integer fileMonth = el.getDate().getMonthValue();
						Integer fileDay = el.getDate().getDayOfMonth();

						Year year = new Year(fileYear, new MonthsList());
						yearList.insertSorted(year);
						Month month = new Month(fileMonth, new DaysList());
						year.getMonthlist().insertSorted4(month);
						Day day = new Day(fileDay, el, records);
						month.getDays().insertSorted4(day);
						day.getElectricity().insertSorted4(el);

						Year yearCheck = (Year) yearList.findYearByValue(fileYear);

						if (yearCheck == null) {
							Year yearObj = new Year(fileYear, new MonthsList());// if it doesn't exist
							yearList.insertSorted(yearObj);
							yearCheck = yearObj;
						}
						Month monthCheck = (Month) yearCheck.getMonthlist().findMonthByValue(fileMonth);
						// System.out.println(monthCheck);
						if (monthCheck == null) {
							Month monthObj = new Month(fileMonth, new DaysList());
							yearCheck.getMonthlist().insertSorted4(monthObj);
							monthCheck = monthObj;
						}
						Day dayCheck = (Day) monthCheck.getDays().findDayByValue(fileDay); // check if day exists
						if (dayCheck == null) {
							Day dayObj = new Day(fileDay, el, records);
							monthCheck.getDays().insertSorted4(dayObj);
							dayCheck = dayObj;
						} else { // if it does, add a new electricity record to it
							// dayCheck.getElectricity().insertSorted4(el);
							records.insertSorted4(el);
						}
					}
					Electricity curr = yearList.getFirst().getMonthlist().getFirst().getDays().getFirst().getElectric();
					electricityTable.getItems().add(curr);
					yearList.print();

				}

			}

			catch (FileNotFoundException exe) {
				System.out.println("File not found!!");
			}

		});

		Tab tab2 = new Tab("Management");
		GridPane gp = new GridPane();
		Button insert = new Button("Insert a New Electricity Record");
		insert.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		insert.setOnAction(e -> {
			insert();
		});
		Button delete = new Button("Delete an Electricity Record");
		delete.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		delete.setOnAction(e -> {
			delete();
		});
		Button search = new Button("Search for an Electricity Record");
		search.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		search.setOnAction(e -> {
			search();
		});
		Button update = new Button("Update an Electricity Record");
		update.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		update.setOnAction(e -> {
			update();
		});
		gp.add(insert, 0, 0);
		gp.add(delete, 0, 1);
		gp.add(search, 0, 2);
		gp.add(update, 0, 3);
		gp.setHgap(7);
		gp.setVgap(7);
		gp.setAlignment(Pos.CENTER);
		tab2.setContent(gp);

		Tab tab3 = new Tab("Statistics");
		GridPane gp1 = new GridPane();
		Button dayButton = new Button("Load Statistics for a Specific Day");
		dayButton.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		dayButton.setOnAction(e -> {
			dayStat();

		});
		Button month = new Button("Load Statistics for a Specific Month");
		month.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		month.setOnAction(e -> {
			monthStat();
		});
		Button year = new Button("Load Statistics for a Specific Year");
		year.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		year.setOnAction(e -> {
			yearStat();

		});
		Button all = new Button("Load Statistics for all Data");
		all.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		all.setOnAction(e -> {
			allStat();

		});
		gp1.add(dayButton, 0, 0);
		gp1.add(month, 0, 1);
		gp1.add(year, 0, 2);
		gp1.add(all, 0, 3);
		gp1.setHgap(7);
		gp1.setVgap(7);
		gp1.setAlignment(Pos.CENTER);
		tab3.setContent(gp1);

		Tab tab4 = new Tab("Save to a File");
		StackPane sp1 = new StackPane();
		Button btn2 = new Button("Save to a File");
		btn2.setAlignment(Pos.CENTER);
		btn2.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		sp1.getChildren().add(btn2);
		tab4.setContent(sp1);
		btn2.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog(stage);

			if (file != null) {
				saveToFile1(file);
			}
		});
		tp.getTabs().addAll(tab1, tab2, tab3, tab4);

		Scene scene = new Scene(tp, 890, 480);
		stage.setTitle("Electricity Management System");
		stage.setScene(scene);
		stage.show();

	}

	public void insert() {
		Stage s1 = new Stage();
		GridPane gp = new GridPane();
		Label warning = new Label();
		warning.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		warning.setVisible(false);
		warning.setManaged(false);
		gp.add(warning, 1, 11);
		Label warn1 = new Label("Must be a Double!!");
		warn1.setVisible(false);
		warn1.setManaged(false);
		warn1.setStyle("-fx-text-fill:red");
		warn1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		gp.add(warn1, 2, 1);
		Label date = new Label(" Date: ");
		Label israel = new Label(" Israeli Lines: ");
		Label gaza = new Label(" Gaza Power Plant: ");
		Label egypt = new Label(" Egyptian Lines: ");
		Label total = new Label(" Total Daily Supply: ");
		Label demand = new Label(" Overall Demand: ");
		Label cuts = new Label(" Power Cuts: ");
		Label temp = new Label(" Temperature: ");
		DatePicker dp = new DatePicker();
		// Set a custom date format using StringConverter
		MyDateConverter converter = new MyDateConverter();
		dp.setConverter(converter);
		dp.setDayCellFactory(new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(DatePicker param) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);

						// Disable dates after the current date
						setDisable(date.isAfter(LocalDate.now()));
					}
				};
			}
		});

		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		TextField tf4 = new TextField();
		TextField tf5 = new TextField();
		TextField tf6 = new TextField();
		TextField tf7 = new TextField();
		TextField tf8 = new TextField();
		Button insert = new Button("Insert");
		insert.setOnAction(e -> {
			if (dp.getValue() == null || tf2.getText().isEmpty() || tf3.getText().isEmpty() || tf4.getText().isEmpty()
					|| tf5.getText().isEmpty() || tf6.getText().isEmpty() || tf7.getText().isEmpty()
					|| tf8.getText().isEmpty()) {
				warning.setVisible(true);
				warning.setManaged(true);
				warning.setStyle("-fx-text-fill:red");
				warning.setText("Please fill all text fields");
			} else {
				LocalDate selectedDate = dp.getValue();
				double israel1;
				double gaza1;
				double egypt1;
				double total1;
				double demand1;
				double cuts1;
				double temp1;
				try {
					israel1 = Double.parseDouble(tf2.getText());
					gaza1 = Double.parseDouble(tf3.getText());
					egypt1 = Double.parseDouble(tf4.getText());
					total1 = Double.parseDouble(tf5.getText());
					demand1 = Double.parseDouble(tf6.getText());
					cuts1 = Double.parseDouble(tf7.getText());
					temp1 = Double.parseDouble(tf8.getText());
				} catch (NumberFormatException exception) {
					warn1.setVisible(true);
					warn1.setManaged(true);
					warn1.setStyle("-fx-text-fill:red");
					return;
				}
				if (israel1 < 0 || gaza1 < 0 || egypt1 < 0 || total1 < 0 || demand1 < 0 || cuts1 < 0 || temp1 < 0) {
					warn1.setVisible(true);
					warn1.setManaged(true);
					warn1.setText("Must be a positive value!!");
					warn1.setStyle("-fx-text-fill:red");
					return;
				}
				Electricity elect = new Electricity(selectedDate, israel1, gaza1, egypt1, total1, demand1, cuts1,
						temp1);
				Integer inputYear = elect.getDate().getYear();
				Integer inputMonth = elect.getDate().getMonthValue();
				Integer inputDay = elect.getDate().getDayOfMonth();
				Year yearCheck = (Year) yearList.findYearByValue(inputYear);// checking if year exists
				if (yearCheck == null) {
					Year yearObj = new Year(inputMonth, new MonthsList());// if it doesn't exist
					yearList.insertSorted4(yearObj);
					yearCheck = yearObj;
				}
				Month monthCheck = (Month) yearCheck.getMonthlist().findMonthByValue(inputMonth);// check if
				if (monthCheck == null) {
					Month monthObj = new Month(inputMonth, new DaysList());
					yearCheck.getMonthlist().insertSorted4(monthObj);
					monthCheck = monthObj;
				}
				Day dayCheck = (Day) monthCheck.getDays().findDayByValue(inputDay); // check if day exists
				if (dayCheck == null) {
					Day dayObj = new Day(inputDay, elect, records);
					monthCheck.getDays().insertSorted4(dayObj);
					dayCheck = dayObj;
					electricityTable.getItems().add(elect);
					warning.setVisible(true);
					warning.setManaged(true);
					warning.setStyle("-fx-text-fill:green");
					warning.setText("Successfully added!");
				} else {
					warning.setVisible(true);
					warning.setManaged(true);
					warning.setStyle("-fx-text-fill:red");
					warning.setText(" Electricity Record Already Exists!!");
				}
			}

			dp.setValue(null);
			tf2.clear();
			tf3.clear();
			tf4.clear();
			tf5.clear();
			tf6.clear();
			tf7.clear();
			tf8.clear();
		});

		gp.add(date, 0, 0);
		gp.add(dp, 1, 0);
		gp.add(israel, 0, 1);
		gp.add(tf2, 1, 1);
		gp.add(gaza, 0, 2);
		gp.add(tf3, 1, 2);
		gp.add(egypt, 0, 3);
		gp.add(tf4, 1, 3);
		gp.add(total, 0, 4);
		gp.add(tf5, 1, 4);
		gp.add(demand, 0, 5);
		gp.add(tf6, 1, 5);
		gp.add(cuts, 0, 6);
		gp.add(tf7, 1, 6);
		gp.add(temp, 0, 7);
		gp.add(tf8, 1, 7);
		gp.add(insert, 1, 9);

		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene1 = new Scene(gp, 650, 480);
		s1.setScene(scene1);
		s1.setTitle("Insert Electricity Record");
		s1.show();

	}

	public void delete() {
		Stage s1 = new Stage();
		GridPane gp = new GridPane();
		Label warning = new Label();
		warning.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		warning.setVisible(false);
		warning.setManaged(false);
		gp.add(warning, 1, 11);
		Label warn1 = new Label("Must be a Double!!");
		warn1.setVisible(false);
		warn1.setManaged(false);
		warn1.setStyle("-fx-text-fill:red");
		warn1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		gp.add(warn1, 2, 1);
		Label date = new Label(" Date: ");
		Label israel = new Label(" Israeli Lines: ");
		Label gaza = new Label(" Gaza Power Plant: ");
		Label egypt = new Label(" Egyptian Lines: ");
		Label total = new Label(" Total Daily Supply: ");
		Label demand = new Label(" Overall Demand: ");
		Label cuts = new Label(" Power Cuts: ");
		Label temp = new Label(" Temperature: ");
		DatePicker dp = new DatePicker();
		// Set a custom date format using StringConverter
		MyDateConverter converter = new MyDateConverter();
		dp.setConverter(converter);
		dp.setDayCellFactory(new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(DatePicker param) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);

						// Disable dates after the current date
						setDisable(date.isAfter(LocalDate.now()));
					}
				};
			}
		});
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		TextField tf4 = new TextField();
		TextField tf5 = new TextField();
		TextField tf6 = new TextField();
		TextField tf7 = new TextField();
		TextField tf8 = new TextField();
		Button delete = new Button("Delete");
		delete.setOnAction(e -> {
			if (dp.getValue() == null || tf2.getText().isEmpty() || tf3.getText().isEmpty() || tf4.getText().isEmpty()
					|| tf5.getText().isEmpty() || tf6.getText().isEmpty() || tf7.getText().isEmpty()
					|| tf8.getText().isEmpty()) {
				warning.setVisible(true);
				warning.setManaged(true);
				warning.setStyle("-fx-text-fill:red");
				warning.setText("Please fill all text fields");
			} else {
				LocalDate selectedDate = dp.getValue();
				double israel1;
				double gaza1;
				double egypt1;
				double total1;
				double demand1;
				double cuts1;
				double temp1;
				try {
					israel1 = Double.parseDouble(tf2.getText());
					gaza1 = Double.parseDouble(tf3.getText());
					egypt1 = Double.parseDouble(tf4.getText());
					total1 = Double.parseDouble(tf5.getText());
					demand1 = Double.parseDouble(tf6.getText());
					cuts1 = Double.parseDouble(tf7.getText());
					temp1 = Double.parseDouble(tf8.getText());
				} catch (NumberFormatException exception) {
					warn1.setVisible(true);
					warn1.setManaged(true);
					warn1.setStyle("-fx-text-fill:red");
					return;
				}
				if (israel1 < 0 || gaza1 < 0 || egypt1 < 0 || total1 < 0 || demand1 < 0 || cuts1 < 0 || temp1 < 0) {
					warn1.setVisible(true);
					warn1.setManaged(true);
					warn1.setText("Must be a positive value!!");
					warn1.setStyle("-fx-text-fill:red");
					return;
				}
				Electricity elect = new Electricity(selectedDate, israel1, gaza1, egypt1, total1, demand1, cuts1,
						temp1);
				Integer inputYear = elect.getDate().getYear();
				Integer inputMonth = elect.getDate().getMonthValue();
				Integer inputDay = elect.getDate().getDayOfMonth();
				Year yearCheck = (Year) yearList.findYearByValue(inputYear);// checking if year exists
				if (yearCheck == null) {
					Year yearObj = new Year(inputMonth, new MonthsList());// if it doesn't exist
					yearList.insertSorted4(yearObj);
					yearCheck = yearObj;
				}
				Month monthCheck = (Month) yearCheck.getMonthlist().findMonthByValue(inputMonth);// check if
				if (monthCheck == null) {
					Month monthObj = new Month(inputMonth, new DaysList());
					yearCheck.getMonthlist().insertSorted4(monthObj);
					monthCheck = monthObj;
				}
				Day dayCheck = (Day) monthCheck.getDays().findDayByValue(inputDay); // check if day exists
				if (dayCheck == null) {
					warning.setVisible(true);
					warning.setManaged(true);
					warning.setStyle("-fx-text-fill:red");
					warning.setText(" Electricity Record Doesn't Exist!!");
				} else { // if it does, delete it
					Alert a1 = new Alert(AlertType.CONFIRMATION,
							"Are you sure you want to delete this Electricity Record?");
					a1.show();
					monthCheck.getDays().remove(dayCheck.getElectric());
					electricityTable.getItems().remove(dayCheck.getElectric());
					warning.setVisible(true);
					warning.setManaged(true);
					warning.setStyle("-fx-text-fill:green");
					warning.setText(" Electricity Record Successfully Deleted!!");
				}

				dp.setValue(null);
				tf2.clear();
				tf3.clear();
				tf4.clear();
				tf5.clear();
				tf6.clear();
				tf7.clear();
				tf8.clear();
			}

		});

		gp.add(date, 0, 0);
		gp.add(dp, 1, 0);
		gp.add(israel, 0, 1);
		gp.add(tf2, 1, 1);
		gp.add(gaza, 0, 2);
		gp.add(tf3, 1, 2);
		gp.add(egypt, 0, 3);
		gp.add(tf4, 1, 3);
		gp.add(total, 0, 4);
		gp.add(tf5, 1, 4);
		gp.add(demand, 0, 5);
		gp.add(tf6, 1, 5);
		gp.add(cuts, 0, 6);
		gp.add(tf7, 1, 6);
		gp.add(temp, 0, 7);
		gp.add(tf8, 1, 7);
		gp.add(delete, 1, 9);

		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene1 = new Scene(gp, 650, 480);
		s1.setScene(scene1);
		s1.setTitle("Delete an Electricity Record");
		s1.show();

	}

	public void search() {
		Stage s1 = new Stage();
		GridPane gp = new GridPane();
		Label warning = new Label();
		warning.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		warning.setVisible(false);
		warning.setManaged(false);
		gp.add(warning, 1, 11);
		Label date = new Label("Date");
		DatePicker dp = new DatePicker();
		MyDateConverter converter = new MyDateConverter();
		dp.setConverter(converter);
		dp.setDayCellFactory(new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(DatePicker param) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);

						// Disable dates after the current date
						setDisable(date.isAfter(LocalDate.now()));
					}
				};
			}
		});
		Button search = new Button("Search");
		TableView<Electricity> electricityTable1 = new TableView<>();

		TableColumn<Electricity, LocalDate> dateColumn = new TableColumn<>("Date");
		dateColumn.setCellValueFactory(new PropertyValueFactory<Electricity, LocalDate>("date"));

		TableColumn<Electricity, Double> israeliColumn = new TableColumn<>("Israeli Lines");
		israeliColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("israeli_lines"));

		TableColumn<Electricity, Double> gazaColumn = new TableColumn<>("Gaza Power Plant");
		gazaColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("gaza_power_plant"));

		TableColumn<Electricity, Double> egyptColumn = new TableColumn<>("Egyptian Lines");
		egyptColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("egyptian_lines"));

		TableColumn<Electricity, Double> totalColumn = new TableColumn<>("Total Daily Supply");
		totalColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("total_daily_supply"));

		TableColumn<Electricity, Double> overallColumn = new TableColumn<>("Overall Demand");
		overallColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("overall_demand"));

		TableColumn<Electricity, Double> powerColumn = new TableColumn<>("Power Cuts");
		powerColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("power_cuts"));

		TableColumn<Electricity, Double> tempColumn = new TableColumn<>("Temperature");
		tempColumn.setCellValueFactory(new PropertyValueFactory<Electricity, Double>("temp"));

		electricityTable1.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		electricityTable1.getColumns().addAll(dateColumn, israeliColumn, gazaColumn, egyptColumn, totalColumn,
				overallColumn, powerColumn, tempColumn);

		search.setOnAction(e -> {
			LocalDate selectedDate = dp.getValue();
			Integer inputYear = selectedDate.getYear();
			Integer inputMonth = selectedDate.getMonthValue();
			Integer inputDay = selectedDate.getDayOfMonth();
			Year yearCheck = (Year) yearList.findYearByValue(inputYear);
			if (yearCheck == null) {
				warning.setVisible(true);
				warning.setManaged(true);
				warning.setStyle("-fx-text-fill:red");
				warning.setText(" Electricity Record Doesn't Exist!!");
			}
			Month monthCheck = (Month) yearCheck.getMonthlist().findMonthByValue(inputMonth);// check if
			if (monthCheck == null) {
				warning.setVisible(true);
				warning.setManaged(true);
				warning.setStyle("-fx-text-fill:red");
				warning.setText(" Electricity Record Doesn't Exist!!");
			}
			try {
				Day dayCheck = (Day) monthCheck.getDays().findDayByValue(inputDay);
				// System.out.println(dayCheck);
				if (dayCheck == null) {
					warning.setVisible(true);
					warning.setManaged(true);
					warning.setStyle("-fx-text-fill:red");
					warning.setText(" Electricity Record Doesn't Exist!!");
				} else {
					Electricity record = dayCheck.getElectricity().findElectricityByDate(selectedDate);
					electricityTable1.getItems().add(record);
				}
			} catch (NullPointerException exc) {
				System.out.println(" ");

			}

			dp.setValue(null);

		});
		gp.add(date, 0, 0);
		gp.add(dp, 1, 0);
		gp.add(search, 1, 1);
		gp.add(electricityTable1, 1, 2);

		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene1 = new Scene(gp, 700, 480);
		s1.setScene(scene1);
		s1.setTitle("Search for an Electricity Record by Date");
		s1.show();
	}

	public void update() {
		Stage s1 = new Stage();
		GridPane gp = new GridPane();
		Label warning = new Label();
		warning.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		warning.setVisible(false);
		warning.setManaged(false);
		gp.add(warning, 1, 11);
		Label warn1 = new Label("Must be a Double!!");
		warn1.setVisible(false);
		warn1.setManaged(false);
		warn1.setStyle("-fx-text-fill:red");
		warn1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		gp.add(warn1, 2, 1);
		Label date = new Label(" Date: ");
		Label israel = new Label(" Israeli Lines: ");
		Label gaza = new Label(" Gaza Power Plant: ");
		Label egypt = new Label(" Egyptian Lines: ");
		Label total = new Label(" Total Daily Supply: ");
		Label demand = new Label(" Overall Demand: ");
		Label cuts = new Label(" Power Cuts: ");
		Label temp = new Label(" Temperature: ");
		DatePicker dp = new DatePicker();
		// Set a custom date format using StringConverter
		MyDateConverter converter = new MyDateConverter();
		dp.setConverter(converter);
		dp.setDayCellFactory(new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(DatePicker param) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);

						// Disable dates after the current date
						setDisable(date.isAfter(LocalDate.now()));
					}
				};
			}
		});
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		TextField tf4 = new TextField();
		TextField tf5 = new TextField();
		TextField tf6 = new TextField();
		TextField tf7 = new TextField();
		TextField tf8 = new TextField();

		tf2.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf3.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf4.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf5.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf6.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf7.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf8.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

		Label date2 = new Label(" Date: ");
		Label israel2 = new Label(" Israeli Lines: ");
		Label gaza2 = new Label(" Gaza Power Plant: ");
		Label egypt2 = new Label(" Egyptian Lines: ");
		Label total2 = new Label(" Total Daily Supply: ");
		Label demand2 = new Label(" Overall Demand: ");
		Label cuts2 = new Label(" Power Cuts: ");
		Label temp2 = new Label(" Temperature: ");

		DatePicker dp2 = new DatePicker();
		// Set a custom date format using StringConverter
		MyDateConverter converter1 = new MyDateConverter();
		dp2.setConverter(converter);
		dp2.setDayCellFactory(new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(DatePicker param) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);

						// Disable dates after the current date
						setDisable(date.isAfter(LocalDate.now()));
					}
				};
			}
		});
		TextField tf22 = new TextField();
		TextField tf32 = new TextField();
		TextField tf42 = new TextField();
		TextField tf52 = new TextField();
		TextField tf62 = new TextField();
		TextField tf72 = new TextField();
		TextField tf82 = new TextField();

		tf22.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf32.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf42.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf52.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf62.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf72.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		tf82.setPrefSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

		Button update = new Button("Update");

		update.setOnAction(e -> {
			if (dp.getValue() == null || tf2.getText().isEmpty() || tf3.getText().isEmpty() || tf4.getText().isEmpty()
					|| tf5.getText().isEmpty() || tf6.getText().isEmpty() || tf7.getText().isEmpty()
					|| tf8.getText().isEmpty() || dp2.getValue() == null || tf22.getText().isEmpty()
					|| tf32.getText().isEmpty() || tf42.getText().isEmpty() || tf52.getText().isEmpty()
					|| tf62.getText().isEmpty() || tf72.getText().isEmpty() || tf82.getText().isEmpty()) {
				warning.setVisible(true);
				warning.setManaged(true);
				warning.setStyle("-fx-text-fill:red");
				warning.setText("Please fill all text fields");
			} else {
				LocalDate selectedDate = dp.getValue();
				LocalDate selectedDate2 = dp2.getValue();
				double israel1;
				double gaza1;
				double egypt1;
				double total1;
				double demand1;
				double cuts1;
				double temp1;
				double israel3;
				double gaza3;
				double egypt3;
				double total3;
				double demand3;
				double cuts3;
				double temp3;
				try {
					israel1 = Double.parseDouble(tf2.getText());
					gaza1 = Double.parseDouble(tf3.getText());
					egypt1 = Double.parseDouble(tf4.getText());
					total1 = Double.parseDouble(tf5.getText());
					demand1 = Double.parseDouble(tf6.getText());
					cuts1 = Double.parseDouble(tf7.getText());
					temp1 = Double.parseDouble(tf8.getText());
					israel3 = Double.parseDouble(tf22.getText());
					gaza3 = Double.parseDouble(tf32.getText());
					egypt3 = Double.parseDouble(tf42.getText());
					total3 = Double.parseDouble(tf52.getText());
					demand3 = Double.parseDouble(tf62.getText());
					cuts3 = Double.parseDouble(tf72.getText());
					temp3 = Double.parseDouble(tf82.getText());
				} catch (NumberFormatException exception) {
					warn1.setVisible(true);
					warn1.setManaged(true);
					warn1.setStyle("-fx-text-fill:red");
					return;
				}
				if (israel1 < 0 || gaza1 < 0 || egypt1 < 0 || total1 < 0 || demand1 < 0 || cuts1 < 0 || temp1 < 0
						|| israel3 < 0 || gaza3 < 0 || egypt3 < 0 || total3 < 0 || demand3 < 0 || cuts3 < 0
						|| temp3 < 0) {
					warn1.setVisible(true);
					warn1.setManaged(true);
					warn1.setText("Must be a positive value!!");
					warn1.setStyle("-fx-text-fill:red");
				}
				Electricity elect = new Electricity(selectedDate, israel1, gaza1, egypt1, total1, demand1, cuts1,
						temp1); // existing record
				Integer inputYear = elect.getDate().getYear();
				Integer inputMonth = elect.getDate().getMonthValue();
				Integer inputDay = elect.getDate().getDayOfMonth();
				Year yearCheck = (Year) yearList.findYearByValue(inputYear);// checking if year exists
				if (yearCheck == null) {
					Year yearObj = new Year(inputMonth, new MonthsList());// if it doesn't exist
					yearList.insertSorted4(yearObj);
					yearCheck = yearObj;
				}
				Month monthCheck = (Month) yearCheck.getMonthlist().findMonthByValue(inputMonth);// check if
				if (monthCheck == null) {
					Month monthObj = new Month(inputMonth, new DaysList());
					yearCheck.getMonthlist().insertSorted4(monthObj);
					monthCheck = monthObj;
				}
				Day dayCheck = (Day) monthCheck.getDays().findDayByValue(inputDay); // check if day exists
				if (dayCheck == null) {
					Day dayObj = new Day(inputDay, elect, records);
					Electricity find = (Electricity) records.findSorted1(elect);
					if (find == null) {
						warning.setVisible(true);
						warning.setManaged(true);
						warning.setStyle("-fx-text-fill:red");
						warning.setText(" Electricity Record Doesn't Exist!!");
					}
				} else {
					Alert a1 = new Alert(AlertType.CONFIRMATION,
							"Are you sure you want to update this Electricity Record?");
					a1.show();
					elect.setDate(selectedDate2);
					elect.setIsraeli_lines(israel3);
					elect.setGaza_power_plant(gaza3);
					elect.setEgyptian_lines(egypt3);
					elect.setTotal_daily_supply(total3);
					elect.setOverall_demand(demand3);
					elect.setPower_cuts(cuts3);
					elect.setTemp(temp3);
					electricityTable.getItems().add(elect);
					warning.setVisible(true);
					warning.setManaged(true);
					warning.setStyle("-fx-text-fill:green");
					warning.setText(" Electricity Record Successfully updated!!");
				}

				dp.setValue(null);
				tf2.clear();
				tf3.clear();
				tf4.clear();
				tf5.clear();
				tf6.clear();
				tf7.clear();
				tf8.clear();
				dp2.setValue(null);
				tf22.clear();
				tf32.clear();
				tf42.clear();
				tf52.clear();
				tf62.clear();
				tf72.clear();
				tf82.clear();

			}
		});

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPrefWidth(100); // Set your preferred width for column 1

		gp.getColumnConstraints().addAll(column1);

		gp.add(date, 0, 0);
		gp.add(dp, 1, 0);
		gp.add(date2, 2, 0);
		gp.add(dp2, 3, 0);
		gp.add(israel, 0, 1);
		gp.add(tf2, 1, 1);
		gp.add(israel2, 2, 1);
		gp.add(tf22, 3, 1);
		gp.add(gaza, 0, 2);
		gp.add(tf3, 1, 2);
		gp.add(gaza2, 2, 2);
		gp.add(tf32, 3, 2);
		gp.add(egypt, 0, 3);
		gp.add(tf4, 1, 3);
		gp.add(egypt2, 2, 3);
		gp.add(tf42, 3, 3);
		gp.add(total, 0, 4);
		gp.add(tf5, 1, 4);
		gp.add(total2, 2, 4);
		gp.add(tf52, 3, 4);
		gp.add(demand, 0, 5);
		gp.add(tf6, 1, 5);
		gp.add(demand2, 2, 5);
		gp.add(tf62, 3, 5);
		gp.add(cuts, 0, 6);
		gp.add(tf7, 1, 6);
		gp.add(cuts2, 2, 6);
		gp.add(tf72, 3, 6);
		gp.add(temp, 0, 7);
		gp.add(tf8, 1, 7);
		gp.add(temp2, 2, 7);
		gp.add(tf82, 3, 7);
		gp.add(update, 1, 9);
		// gp.add(electricityTable, 1, 10);

		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene1 = new Scene(gp, 800, 480);
		s1.setScene(scene1);
		s1.setTitle("Update an Electricity Record");
		s1.show();
	}

	public void dayStat() {
		Stage s2 = new Stage();
		GridPane gp = new GridPane();
		Label warning = new Label();
		warning.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		warning.setVisible(false);
		warning.setManaged(false);
		gp.add(warning, 1, 11);
		Label day = new Label("Day: ");
		TextField tf = new TextField();
		Label choose = new Label("Choose Column: ");
		TextField tf1 = new TextField();

		TableView<Statistics> tableView = new TableView<>();
		// ObservableList<Statistics> statisticsList =
		// FXCollections.observableArrayList();
		ObservableList<Statistics> sumList = FXCollections.observableArrayList();
		ObservableList<Statistics> minList = FXCollections.observableArrayList();
		ObservableList<Statistics> maxList = FXCollections.observableArrayList();
		ObservableList<Statistics> avgList = FXCollections.observableArrayList();
		// tableView.setItems(sumList);
		// tableView.setItems(statisticsList);

		TableColumn<Statistics, Double> sumColumn = new TableColumn<>("Sum");
		sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));

		TableColumn<Statistics, Double> minColumn = new TableColumn<>("Min");
		minColumn.setCellValueFactory(new PropertyValueFactory<>("min"));

		TableColumn<Statistics, Double> maxColumn = new TableColumn<>("Max");
		maxColumn.setCellValueFactory(new PropertyValueFactory<>("max"));

		TableColumn<Statistics, Double> averageColumn = new TableColumn<>("Average");
		averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableView.getColumns().addAll(sumColumn, minColumn, maxColumn, averageColumn);

		Button load = new Button("Load");
		load.setOnAction(e -> {
			int inputDay = Integer.parseInt(tf.getText());
			Day inputDayy = new Day(inputDay);

			tableView.getItems().clear();

			Runnable task = () -> {
				Year currentYearNode = (Year) yearList.getFirst();
				while (currentYearNode != null) {
					Month currentMonth = (Month) currentYearNode.getMonthlist().getFirst();
					while (currentMonth != null) {
						Day dayNode = (Day) currentMonth.getDays().findDayByValue(inputDay);
						while (dayNode != null) {
							Electricity recordss = dayNode.getElectricity().getFirst();
							System.out.println("Before Loop");
							while (recordss != null) {
								// System.out.println("Inside
								// Loop");
								if (tf1.getText().equalsIgnoreCase("Israel")) {
									Statistics israeli = recordss.getIsraelStat();
									tableView.getItems().add(israeli);
								} else if (tf1.getText().equalsIgnoreCase("Gaza")) {
									Statistics gaza = recordss.getGazaStat();
									tableView.getItems().add(gaza);
								} else if (tf1.getText().equalsIgnoreCase("Egypt")) {
									Statistics egypt = recordss.getEgyptStat();
									tableView.getItems().add(egypt);
								} else if (tf1.getText().equalsIgnoreCase("Total Daily Supply")) {
									Statistics total = recordss.getTotalStat();
									tableView.getItems().add(total);
								} else if (tf1.getText().equalsIgnoreCase("Overall Demand")) {
									Statistics demand = recordss.getDemandStat();
									tableView.getItems().add(demand);
								} else if (tf1.getText().equalsIgnoreCase("Power Cuts")) {
									Statistics cuts = recordss.getCutsStat();
									tableView.getItems().add(cuts);
								} else if (tf1.getText().equalsIgnoreCase("Temp")) {
									Statistics temp = recordss.getTempStat();
									tableView.getItems().add(temp);
								}
								recordss = dayNode.getElectricity().getNext();
							}
							System.out.println("After loop");
							dayNode = currentMonth.getDays().getNext();
						}
						currentMonth = currentYearNode.getMonthlist().getNext();
					}
					currentYearNode = yearList.getNext();
				}
			};

			// Start the background thread
			Thread backgroundThread = new Thread(task);
			backgroundThread.start();

		});
		gp.add(day, 0, 0);
		gp.add(tf, 1, 0);
		gp.add(choose, 2, 0);
		gp.add(tf1, 3, 0);
		gp.add(load, 1, 1);
		gp.add(tableView, 1, 2);

		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene2 = new Scene(gp, 700, 480);
		s2.setScene(scene2);
		s2.setTitle("Load Statistics for a Given Day");
		s2.show();
	}

	public void monthStat() {
		Stage s2 = new Stage();
		GridPane gp = new GridPane();
		Label warning = new Label();
		warning.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		warning.setVisible(false);
		warning.setManaged(false);
		gp.add(warning, 1, 11);
		Label day = new Label("Month: ");
		TextField tf = new TextField();
		Label choose = new Label("Choose Column: ");
		TextField tf1 = new TextField();

		TableView<Statistics> tableView = new TableView<>();

		TableColumn<Statistics, Double> sumColumn = new TableColumn<>("Sum");
		sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));

		TableColumn<Statistics, Double> minColumn = new TableColumn<>("Min");
		minColumn.setCellValueFactory(new PropertyValueFactory<>("min"));

		TableColumn<Statistics, Double> maxColumn = new TableColumn<>("Max");
		maxColumn.setCellValueFactory(new PropertyValueFactory<>("max"));

		TableColumn<Statistics, Double> averageColumn = new TableColumn<>("Average");
		averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableView.getColumns().addAll(sumColumn, minColumn, maxColumn, averageColumn);

		Button load = new Button("Load");
		load.setOnAction(e -> {
			int inputMonth = Integer.parseInt(tf.getText());
			Month inputMonthh = new Month(inputMonth);
			Runnable task1 = () -> {
				Year currentYearNode = yearList.getFirst();
				while (currentYearNode != null) {
					Month month = (Month) currentYearNode.getMonthlist().findMonthByValue(inputMonth);
					while (month != null) {
						Day day1 = (Day) month.getDays().getFirst();
						while (day1 != null) {
							Electricity recordss = day1.getElectricity().getFirst();
							while (recordss != null) {
								if (tf1.getText().equalsIgnoreCase("Israel")) {
									Statistics israeli = recordss.getIsraelStat();
									tableView.getItems().add(israeli);
								} else if (tf1.getText().equalsIgnoreCase("Gaza")) {
									Statistics gaza = recordss.getGazaStat();
									tableView.getItems().add(gaza);
								} else if (tf1.getText().equalsIgnoreCase("Egypt")) {
									Statistics egypt = recordss.getEgyptStat();
									tableView.getItems().add(egypt);
								} else if (tf1.getText().equalsIgnoreCase("Total Daily Supply")) {
									Statistics total = recordss.getTotalStat();
									tableView.getItems().add(total);
								} else if (tf1.getText().equalsIgnoreCase("Overall Demand")) {
									Statistics demand = recordss.getDemandStat();
									tableView.getItems().add(demand);
								} else if (tf1.getText().equalsIgnoreCase("Power Cuts")) {
									Statistics cuts = recordss.getCutsStat();
									tableView.getItems().add(cuts);
								} else if (tf1.getText().equalsIgnoreCase("Temp")) {
									Statistics temp = recordss.getTempStat();
									tableView.getItems().add(temp);
								}
								recordss = day1.getElectricity().getNext();
							}
							System.out.println("After loop");
							day1 = month.getDays().getNext();
						}
						month = currentYearNode.getMonthlist().getNext();
					}
					currentYearNode = yearList.getNext();
				}
			};
			Thread backgroundThread = new Thread(task1);
			backgroundThread.start();

		});

		gp.add(day, 0, 0);
		gp.add(tf, 1, 0);
		gp.add(choose, 2, 0);
		gp.add(tf1, 3, 0);
		gp.add(load, 1, 1);
		gp.add(tableView, 1, 2);

		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene2 = new Scene(gp, 700, 480);
		s2.setScene(scene2);
		s2.setTitle("Load Statistics for a Given Month");
		s2.show();

	}

	public void yearStat() {
		Stage s2 = new Stage();
		GridPane gp = new GridPane();
		Label warning = new Label();
		warning.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		warning.setVisible(false);
		warning.setManaged(false);
		gp.add(warning, 1, 11);
		Label day = new Label("Year: ");
		TextField tf = new TextField();
		Label choose = new Label("Choose Column: ");
		TextField tf1 = new TextField();

		TableView<Statistics> tableView = new TableView<>();

		TableColumn<Statistics, Double> sumColumn = new TableColumn<>("Sum");
		sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));

		TableColumn<Statistics, Double> minColumn = new TableColumn<>("Min");
		minColumn.setCellValueFactory(new PropertyValueFactory<>("min"));

		TableColumn<Statistics, Double> maxColumn = new TableColumn<>("Max");
		maxColumn.setCellValueFactory(new PropertyValueFactory<>("max"));

		TableColumn<Statistics, Double> averageColumn = new TableColumn<>("Average");
		averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableView.getColumns().addAll(sumColumn, minColumn, maxColumn, averageColumn);

		Button load = new Button("Load");
		load.setOnAction(e -> {
			int inputYear = Integer.parseInt(tf.getText());
			Year inputYearr = new Year(inputYear);
			Runnable task1 = () -> {
				Year findYear = yearList.findYearByValue(inputYear);
				while (findYear != null) {
					Month month = (Month) findYear.getMonthlist().getFirst();
					while (month != null) {
						Day day1 = (Day) month.getDays().getFirst();
						while (day1 != null) {
							Electricity recordss = day1.getElectricity().getFirst();
							while (recordss != null) {
								if (tf1.getText().equalsIgnoreCase("Israel")) {
									Statistics israeli = recordss.getIsraelStat();
									tableView.getItems().add(israeli);
								} else if (tf1.getText().equalsIgnoreCase("Gaza")) {
									Statistics gaza = recordss.getGazaStat();
									tableView.getItems().add(gaza);
								} else if (tf1.getText().equalsIgnoreCase("Egypt")) {
									Statistics egypt = recordss.getEgyptStat();
									tableView.getItems().add(egypt);
								} else if (tf1.getText().equalsIgnoreCase("Total Daily Supply")) {
									Statistics total = recordss.getTotalStat();
									tableView.getItems().add(total);
								} else if (tf1.getText().equalsIgnoreCase("Overall Demand")) {
									Statistics demand = recordss.getDemandStat();
									tableView.getItems().add(demand);
								} else if (tf1.getText().equalsIgnoreCase("Power Cuts")) {
									Statistics cuts = recordss.getCutsStat();
									tableView.getItems().add(cuts);
								} else if (tf1.getText().equalsIgnoreCase("Temp")) {
									Statistics temp = recordss.getTempStat();
									tableView.getItems().add(temp);
								}
								recordss = day1.getElectricity().getNext();
							}
							System.out.println("After loop");
							day1 = month.getDays().getNext();
						}
						month = findYear.getMonthlist().getNext();
					}
					findYear = yearList.getNext();
				}
			};
			Thread backgroundThread = new Thread(task1);
			backgroundThread.start();
		});

		gp.add(day, 0, 0);
		gp.add(tf, 1, 0);
		gp.add(choose, 2, 0);
		gp.add(tf1, 3, 0);
		gp.add(load, 1, 1);
		gp.add(tableView, 1, 2);

		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene2 = new Scene(gp, 700, 480);
		s2.setScene(scene2);
		s2.setTitle("Load Statistics for a Given Year");
		s2.show();

	}

	public void allStat() {
		Stage s2 = new Stage();
		GridPane gp = new GridPane();
		Label warning = new Label();
		warning.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		warning.setVisible(false);
		warning.setManaged(false);
		gp.add(warning, 1, 11);

		TableView<Statistics> tableView = new TableView<>();

		TableColumn<Statistics, Double> sumColumn = new TableColumn<>("Sum");
		sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));

		TableColumn<Statistics, Double> minColumn = new TableColumn<>("Min");
		minColumn.setCellValueFactory(new PropertyValueFactory<>("min"));

		TableColumn<Statistics, Double> maxColumn = new TableColumn<>("Max");
		maxColumn.setCellValueFactory(new PropertyValueFactory<>("max"));

		TableColumn<Statistics, Double> averageColumn = new TableColumn<>("Average");
		averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableView.getColumns().addAll(sumColumn, minColumn, maxColumn, averageColumn);

		Button load = new Button("Load");
		load.setOnAction(e -> {
			Runnable task1 = () -> {
				Year findYear = yearList.getFirst();
				while (findYear != null) {
					Month month = (Month) findYear.getMonthlist().getFirst();
					while (month != null) {
						Day day1 = (Day) month.getDays().getFirst();
						while (day1 != null) {
							Electricity recordss = day1.getElectricity().getFirst();
							while (recordss != null) {
								Statistics stat = recordss.getStatistics();
								tableView.getItems().add(stat);
								recordss = day1.getElectricity().getNext();
							}
							System.out.println("After loop");
							day1 = month.getDays().getNext();
						}
						month = findYear.getMonthlist().getNext();
					}
					findYear = yearList.getNext();
				}
			};
			Thread backgroundThread = new Thread(task1);
			backgroundThread.start();
		});
		gp.add(load, 1, 0);
		gp.add(tableView, 1, 2);

		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene2 = new Scene(gp, 700, 480);
		s2.setScene(scene2);
		s2.setTitle("Load Statistics for All Data");
		s2.show();

	}

	private void saveToFile(File file) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			Year currentYearNode = yearList.getFirst();
			while (currentYearNode != null) {
				Integer year = currentYearNode.getYear();
				Month month = currentYearNode.getMonthlist().getFirst();
				Day day = currentYearNode.getMonthlist().getNext().getDays().getFirst();
				Electricity record = day.getElectricity().getFirst();
				String line = record.getDate() + "," + record.getIsraeli_lines() + "," + record.getGaza_power_plant()
						+ "," + record.getEgyptian_lines() + "," + record.getTotal_daily_supply() + ","
						+ record.getOverall_demand() + "," + record.getPower_cuts() + "," + record.getTemp();
				writer.write(line);
				writer.newLine();
				currentYearNode = yearList.getNext();
			}
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
			// Handle exception appropriately
		}
	}

	private void saveToFile1(File file) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			Year currentYearNode = yearList.getFirst();

			while (currentYearNode != null) {
				Integer year = currentYearNode.getYear();
				Year year1 = new Year(year);
				Month currentMonthNode = year1.getMonthlist().getFirst();

				while (currentMonthNode != null) {
					Integer month = currentMonthNode.getMonth();
					Month month1 = new Month(month);
					Day currentDayNode = month1.getDays().getFirst();

					while (currentDayNode != null) {
						Integer day = currentDayNode.getDay();
						Day day1 = new Day(day);
						Electricity record = day1.getElectricity().getFirst();

						String line = record.getDate() + "," + record.getIsraeli_lines() + ","
								+ record.getGaza_power_plant() + "," + record.getEgyptian_lines() + ","
								+ record.getTotal_daily_supply() + "," + record.getOverall_demand() + ","
								+ record.getPower_cuts() + "," + record.getTemp();

						writer.write(line);
						writer.newLine();

						currentDayNode = currentMonthNode.getDays().getNext();
					}

					currentMonthNode = currentYearNode.getMonthlist().getNext();
				}

				currentYearNode = yearList.getNext();
			}

			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
			// Handle exception appropriately
		}
	}
	
		
	}


