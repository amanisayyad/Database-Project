import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class Main extends Application {
	DLinkedList<Brand> dList = new DLinkedList<>();
	LinkedStack<Order> stack = new LinkedStack<>();
	LinkedQueue<Order> q = new LinkedQueue<>();

	static String l;
	static Image im = new Image("24025670.jpg");
	static BackgroundImage image = new BackgroundImage(im, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT, new BackgroundSize(600, 550, true, true, true, true));
	static Background bg = new Background(image);
	static Scene scene1;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		main();
	}

	public void main() {
		Stage stage = new Stage();

		BorderPane bp1 = new BorderPane();
		bp1.setBackground(bg);
		HBox h = new HBox();
		Label choose = new Label("What type of user are you?");
		choose.setTextFill(Color.WHITE);
		choose.setStyle("-fx-background-color: black;");
		choose.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		Button admin = new Button("I am an Admin");
		admin.setOnAction(e -> {
			admin();
		});
		Button cust = new Button("I am a Customer");
		cust.setOnAction(e -> {
			customer();
		});
		admin.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		cust.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		VBox hb1 = new VBox(7);
		hb1.setAlignment(Pos.CENTER);
		hb1.getChildren().addAll(choose, admin, cust);
		Label lbl1 = new Label("Welcome to My Car Agency System!!");
		lbl1.setTextFill(Color.WHITE);
		lbl1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		h.setAlignment(Pos.CENTER);
		h.setStyle("-fx-background-color:black");
		h.getChildren().add(lbl1);
		bp1.setTop(h);
		bp1.setCenter(hb1);

		scene1 = new Scene(bp1, 600, 550);
		stage.setScene(scene1);
		stage.setTitle("Car Agency System");
		stage.show();
	}

	public void admin() {
		Stage stage1 = new Stage();
		TabPane tp = new TabPane();

		Tab tab1 = new Tab("Main");
		BorderPane bp = new BorderPane();
		Button back = new Button("Go Back to Main Page");
		back.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		back.setOnAction(e -> {
			main();
		});
		HBox hb2 = new HBox(5);
		Label lbl1 = new Label("Load Files");
		lbl1.setTextFill(Color.WHITE);
		lbl1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		hb2.setAlignment(Pos.CENTER);
		hb2.setStyle("-fx-background-color:black");
		hb2.getChildren().add(lbl1);
		bp.setBackground(bg);
		VBox vb = new VBox(5);
		Button load = new Button("Load Cars File");
		Button load1 = new Button("Load Orders File");
		load.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		load1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		Label label0 = new Label("Choose a file to load:");
		label0.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		vb.getChildren().addAll(label0, load, load1);
		vb.setAlignment(Pos.CENTER);
		bp.setTop(hb2);
		bp.setBottom(vb);
		bp.setLeft(back);
		tab1.setContent(bp);

		load.setOnAction(e -> {

			File file = new File("C:\\Users\\hp\\Downloads\\cars.txt");
			try {
				if (file.exists()) {
					Scanner scan = new Scanner(file);
					String line = ",";
					while (scan.hasNextLine()) {
						line = scan.nextLine();
						Car c = new Car(line);
						l = c.getBrand();// store brand into string
						Brand brandObj = dList.get(new Brand(l));// check if brand exists
						if (brandObj != null) {// if it exists, add to list
							dList.get(brandObj).getsList().insertSorted(c);
						} else {// if it doesn't exist, create new one and add to list
							brandObj = new Brand(l);
							dList.insertSorted(brandObj);
							dList.get(brandObj).getsList().insertSorted(c);
						}
					}
					 dList.traverase();
				}

			}

			catch (FileNotFoundException ex) {
				System.out.println("file not found");
			}

		});
		load1.setOnAction(e -> {
			File file = new File("C:\\Users\\hp\\Downloads\\orders.txt");
			try {
				if (file.exists()) {
					Scanner scan = new Scanner(file);
					String line = ",";
					while (scan.hasNextLine()) {
						line = scan.nextLine();
						String[] orderData = line.trim().split(",");
						if (orderData.length >= 9) {
							try {
								String custName = orderData[0].trim();
								String custMobile = orderData[1].trim();
								String brand = orderData[2].trim();
								String model = orderData[3].trim();
								String year = orderData[4].trim();
								String color = orderData[5].trim();
								String price = orderData[6].trim();
								DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
								Date orderDate = dateFormat.parse(orderData[7].trim());
								String orderStatus = orderData[8].trim();
								Order o = new Order(custName, custMobile, orderDate, brand, model, year, color, price,
										orderStatus);
								if (o.getOrderStatus().equals("Finished")) {// if order status is finished, add to stack
									stack.push(o);
								} else if (o.getOrderStatus().equals("InProcess")) {// if order is still in process add
																					// to queue
									q.enqueue(o);
								} else {
									System.out.println(" hi");
								}
							} catch (DateTimeParseException ex) {
								System.out.println(" ");
							} catch (ParseException e1) {
								System.out.println(" ");
							}

						}
					}
					q.print();
					stack.print();

				}
			} catch (FileNotFoundException ex) {
				System.out.println("file not found");
			}

		});

		Tab tab2 = new Tab("Brand");
		GridPane gp1 = new GridPane();
		gp1.setBackground(bg);
		Label insert = new Label("Insert New Brand: ");
		insert.setTextFill(Color.WHITE);
		insert.setStyle("-fx-background-color: black;");
		insert.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		Label update = new Label("Delete a Brand: ");
		update.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		update.setTextFill(Color.WHITE);
		update.setStyle("-fx-background-color: black;");
		Label search = new Label("Search and Display Brand:");
		search.setTextFill(Color.WHITE);
		search.setStyle("-fx-background-color: black;");
		search.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		TextField tf = new TextField();
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		Button insert1 = new Button("Insert");
		insert1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		insert1.setOnAction(e -> {
			Label lbl2 = new Label();
			lbl2.setStyle("-fx-text-fill:red");
			lbl2.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
			gp1.add(lbl2, 0, 5);
			String brand = tf.getText();
			Brand brandObj = new Brand(brand);
			if (dList.get(brandObj) != null) {
				lbl2.setVisible(true);
				lbl2.setText("This brand already exists");
			} else {
				lbl2.setVisible(false);
				Label lbl3 = new Label();
				lbl3.setStyle("-fx-text-fill:red");
				lbl3.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
				gp1.add(lbl3, 0, 6);
				dList.insertSorted(brandObj);
				lbl3.setVisible(true);
				lbl3.setText("This brand has been added succesfully");
			}
			tf.clear();
			// lbl2.setVisible(false);
		});

		Button delete = new Button("Delete");
		delete.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		delete.setOnAction(e -> {
			Label lbl3 = new Label();
			lbl3.setStyle("-fx-text-fill:red");
			lbl3.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
			gp1.add(lbl3, 0, 7);
			String brand = tf2.getText();
			Brand brandObj = new Brand(brand);
			if (dList.get(brandObj) != null) {
				dList.deleteSorted(brandObj);
				lbl3.setText("This brand has been deleted successfully");
			} else {
				lbl3.setText("This brand doesn't exist");
			}
			tf.clear();

		});

		Button search1 = new Button("Search");
		search1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		search1.setOnAction(e -> {
			Label lbl4 = new Label();
			lbl4.setStyle("-fx-text-fill:red");
			lbl4.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
			gp1.add(lbl4, 0, 8);
			String brand = tf3.getText();
			Brand brandObj = new Brand(brand);
			if (dList.get(brandObj) != null) {
				lbl4.setText(brand);
			} else {
				lbl4.setText("This brand doesn't exist");
			}
			tf3.clear();

		});

		gp1.add(insert, 0, 0);
		gp1.add(tf, 1, 0);
		gp1.add(insert1, 2, 0);
		gp1.add(update, 0, 1);
		gp1.add(tf2, 1, 1);
		gp1.add(delete, 2, 1);
		gp1.add(search, 0, 2);
		gp1.add(tf3, 1, 2);
		gp1.add(search1, 2, 2);
		gp1.setVgap(7);
		gp1.setHgap(7);
		gp1.setAlignment(Pos.CENTER);
		tab2.setContent(gp1);

		Tab tab3 = new Tab("Cars");
		GridPane gp2 = new GridPane();
		gp2.setBackground(bg);
		Button insert2 = new Button("Insert a New Car");
		insert2.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		insert2.setOnAction(e -> {
			insert();
		});
		Button update2 = new Button("Delete a Car");
		update2.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		update2.setOnAction(e -> {
			delete();
		});
		Button search2 = new Button("Search for Car");
		search2.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		search2.setOnAction(e -> {
			search();
		});

		gp2.add(insert2, 0, 0);
		gp2.add(update2, 0, 1);
		gp2.add(search2, 0, 2);
		gp2.setHgap(7);
		gp2.setVgap(7);
		gp2.setAlignment(Pos.CENTER);
		tab3.setContent(gp2);

		Tab tab4 = new Tab("Report");
		BorderPane bp1 = new BorderPane();
		bp1.setBackground(bg);
		TextArea ta = new TextArea();
		ta.setPrefHeight(5);
		ta.setPrefWidth(5);
		Button gen = new Button("Generate Report");
		gen.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		gen.setOnAction(e -> {
			int count = 0;
			while (!stack.isEmpty() && count <= 10) {
				SNode<Order> o = stack.pop();
				ta.appendText(o.toString());
				count++;
			}
		});
		bp1.setTop(gen);
		bp1.setCenter(ta);

		tab4.setContent(bp1);

		Tab tab5 = new Tab("Save");
		BorderPane bp3 = new BorderPane();
		bp3.setBackground(bg);
		Label lbl7 = new Label("Save Car to a File");
		lbl7.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		lbl7.setTextFill(Color.WHITE);
		HBox hb = new HBox(5);
		hb.setAlignment(Pos.CENTER);
		hb.setStyle("-fx-background-color:black");
		hb.getChildren().add(lbl7);
		Button save = new Button("Save to a File");
		save.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		bp3.setTop(hb);
		bp3.setCenter(save);
		tab5.setContent(bp3);
		save.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Save");
			fc.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
			File file = fc.showSaveDialog(stage1);
			String data = dList.print();
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				writer.write(data);
				System.out.println("Data saved successfully!");
			} catch (IOException ex) {
				System.out.println("Error occurred while saving the file");
			}

		});

		Tab tab6 = new Tab("Process Orders");
		StackPane sp = new StackPane();
		sp.setBackground(bg);
		Button process = new Button("Process");
		process.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		sp.getChildren().add(process);
		process.setOnAction(e -> {
//			Order o = q.getFront();
//			Car car = null;
//			dList.get(o).getsList().findSorted(car);
//			if (car != null) {
//				o.setOrderStatus("Finished");
//			} else {
//				q.dequeue();
//				q.enqueue(o);
//			}

		});
		tab6.setContent(sp);

		tp.getTabs().addAll(tab1, tab2, tab3, tab4, tab5, tab6);
		tp.setStyle("-fx-background-size: cover");
		Scene scene = new Scene(tp, 600, 550);
		stage1.setTitle("Admin");
		stage1.setScene(scene);
		stage1.show();
	}

	public void insert() {
		Stage s1 = new Stage();
		GridPane gp = new GridPane();
		Label name = new Label("Brand:");
		Label age = new Label("Model:");
		Label date = new Label("Year:");
		Label Gender = new Label("Color:");
		Label notes = new Label("Price:");
		TextField tf = new TextField();
		TextField tf1 = new TextField();
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		TextField tf4 = new TextField();
		TextField tf5 = new TextField();
		Button add = new Button("Add");
		add.setOnAction(e -> {
			Label lbl = new Label();
			gp.add(lbl, 1, 5);
			lbl.setStyle("-fx-text-fill:red");
			String brand1 = tf.getText();
			String model1 = tf1.getText();
			Integer year1 = Integer.parseInt(tf3.getText());
			String color1 = tf4.getText();
			String price1 = tf5.getText();
			Car car = new Car(brand1, model1, year1, color1, price1);
			l = car.getBrand();
			Brand brand = dList.get(new Brand(l));
			if (brand.getsList().findSorted(car).equals(car)) {
				lbl.setText("Car Already Exists");
			} else {
				brand.getsList().insertSorted(car);
				lbl.setText("Car has been successfully added");
			}
			tf.clear();
			tf1.clear();
			tf3.clear();
			tf4.clear();

		});

		gp.add(name, 0, 0);
		gp.add(tf, 1, 0);
		gp.add(age, 0, 1);
		gp.add(tf1, 1, 1);
		gp.add(date, 0, 2);
		gp.add(tf2, 1, 2);
		gp.add(Gender, 0, 3);
		gp.add(tf3, 1, 3);
		gp.add(notes, 0, 4);
		gp.add(tf5, 1, 4);
		gp.add(add, 1, 5);
		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene1 = new Scene(gp, 450, 300);
		s1.setScene(scene1);
		s1.setTitle("Add Car");
		s1.show();

	}

	public void search() {
		Stage stage2 = new Stage();
		GridPane gp = new GridPane();
		Button search = new Button("Search");
		Label enter = new Label("Enter Car:");
		TextField tf = new TextField();

		search.setOnAction(e -> {
			Label lbl1 = new Label();
			lbl1.setStyle("-fx-text-fill:red");
			gp.add(lbl1, 1, 5);
			String name = tf.getText();
			Brand brand = new Brand(name);
			if (dList.get(brand) == null) {
				lbl1.setText("This car doesn't exist");
			} else {
				lbl1.setText(name + brand.getsList().toString());
			}
			tf.clear();
		});
		gp.add(enter, 0, 0);
		gp.add(tf, 0, 1);
		gp.add(search, 1, 1);
		gp.setHgap(7);
		gp.setVgap(7);

		Scene scene2 = new Scene(gp, 450, 300);
		stage2.setScene(scene2);
		stage2.setTitle("Search");
		stage2.show();

	}

	public void delete() {
		Stage s2 = new Stage();
		GridPane gp = new GridPane();
		Label name = new Label("Brand:");
		Label age = new Label("Model:");
		Label location1 = new Label("Year:");
		Label date = new Label("Color:");
		Label Gender = new Label("Price:");
		Label notes = new Label("Notes:");
		TextField tf = new TextField();
		TextField tf1 = new TextField();
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		TextField tf4 = new TextField();
		TextField tf5 = new TextField();
		Button delete = new Button("Delete");
		delete.setOnAction(e -> {
			Label lbl = new Label();
			lbl.setStyle("-fx-text-fill:red");
			gp.add(lbl, 1, 5);
			String brand = tf.getText();
			String model = tf1.getText();
			Integer year = Integer.parseInt(tf2.getText());
			String color = tf4.getText();
			String price = tf5.getText();
			Car car = new Car(brand, model, year, color, price);
			l = car.getBrand();
			Brand brandObj = dList.get(new Brand(l));
			if (brandObj.getsList().findSorted(car).equals(car)) {
				brandObj.getsList().deleteSorted(car);
				lbl.setText("Car has been successfully deleted");
			} else {
				lbl.setText("Car Doesn't Exist");
			}
			tf.clear();
			tf1.clear();
			tf3.clear();
			tf4.clear();

		});

		gp.add(name, 0, 0);
		gp.add(tf, 1, 0);
		gp.add(age, 0, 1);
		gp.add(tf1, 1, 1);
		gp.add(location1, 0, 2);
		gp.add(tf2, 1, 2);
		gp.add(date, 0, 3);
		gp.add(tf3, 1, 3);
		gp.add(Gender, 0, 4);
		gp.add(tf4, 1, 4);
		gp.add(delete, 0, 5);
		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene1 = new Scene(gp, 450, 300);
		s2.setScene(scene1);
		s2.setTitle("Delete Car");
		s2.show();
	}

	public void customer() {
		Stage stage3 = new Stage();
		BorderPane bp = new BorderPane();
		bp.setBackground(bg);
		Label lbl = new Label("Available Car Brands: ");
		lbl.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		lbl.setTextFill(Color.WHITE);
		lbl.setStyle("-fx-background-color: black;");
		Label lbl1 = new Label("Filters:");
		lbl1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		lbl1.setTextFill(Color.WHITE);
		lbl1.setStyle("-fx-background-color: black;");
		Label lbl2 = new Label("Year: ");
		lbl2.setTextFill(Color.WHITE);
		lbl2.setStyle("-fx-background-color: black;");
		Label lbl3 = new Label("Color: ");
		lbl3.setTextFill(Color.WHITE);
		lbl3.setStyle("-fx-background-color: black;");
		Label lbl4 = new Label("Model: ");
		lbl4.setTextFill(Color.WHITE);
		lbl4.setStyle("-fx-background-color: black;");
		Label lbl5 = new Label("Price: ");
		lbl5.setTextFill(Color.WHITE);
		lbl5.setStyle("-fx-background-color: black;");
		lbl2.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		lbl3.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		lbl4.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		lbl5.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		ComboBox<String> cb = new ComboBox<>();
		String selectedBrand = cb.getValue();
		cb.setEditable(true);
		cb.getItems().addAll("BMW", "Ford", "KIA", "Tesla","K","T","M","B","F");
		Label lbl6 = new Label();
		lbl6.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		lbl6.setStyle("-fx-text-fill:red");
		Button order = new Button("Place an Order");
		order.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		order.setOnAction(e -> {
			order();
		});
		cb.setOnAction(e -> {
			if (cb.getValue() == "BMW" || cb.getValue() == "Ford" || cb.getValue() == "KIA"
					|| cb.getValue() == "Tesla" && cb.getValue() != "null") {
				lbl6.setText(" ");
				Label lbl7 = new Label("Available " + cb.getValue() + " Cars: ");
				lbl7.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
				lbl7.setTextFill(Color.WHITE);
				lbl7.setStyle("-fx-background-color: black;");
				TextArea ta = new TextArea();
				ta.setEditable(false);
				GridPane gp = new GridPane();
				gp.add(lbl7, 0, 1);
				gp.add(ta, 0, 3);
				gp.add(order, 0, 6);
				bp.setCenter(gp);
				Brand brand = dList.get(new Brand(cb.getValue()));
				if (brand != null) {
					String print = dList.get(brand).getsList().toString();
					ta.appendText(print);
				}
//					GridPane gp = new GridPane();
//					gp.add(lbl7, 0, 1);
//					gp.add(ta, 0, 3);
//					gp.add(order, 0, 6);
//					bp.setCenter(gp);
//				
			}

			else {
				lbl6.setText("Sorry, this brand is unavailable");

			}

		});
		Button save = new Button("Save");
		save.setFont(Font.font("Ariel", FontWeight.BOLD, 15));

		ComboBox<String> cb1 = new ComboBox<>();
		cb1.setEditable(true);
		cb1.getItems().addAll("Gray", "Black", "White", "Green", "Blue", "Red", "Metallic Gray", "Metallic Silver",
				"Silver", "Brown", "Gold", "Metallic Gold");
		ComboBox<String> cb2 = new ComboBox<>();
		cb2.setEditable(true);
		cb2.getItems().addAll("C200", "C300", "X", "X3", "X5", "X6", "MUSTANG", "RIO", "OPTIMA");
		ComboBox<String> cb3 = new ComboBox<>();
		cb3.setEditable(true);
		cb3.getItems().addAll("2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011",
				"2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023");
		ComboBox<String> cb4 = new ComboBox<>();
		cb4.setEditable(true);
		cb4.getItems().addAll("100K-150K", "150K-170K", "170K-200K", "200K-250K", "Above 250K");
		HBox hb = new HBox(5);
		hb.getChildren().addAll(lbl, cb, lbl6);
		VBox vb = new VBox(5);
		Label lbl9 = new Label();
		lbl9.setText(" ");
		lbl9.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		lbl9.setStyle("-fx-text-fill:red");
		save.setOnAction(e -> {
			try {
				String color = cb1.getValue();
				String model = cb2.getValue();
				Integer year = Integer.parseInt(cb3.getValue());
				String price = cb4.getValue();
				Car car = new Car(cb.getValue(), model, year, color, price);

				if (year != 0 && car.getYear() != year && color != null && !color.isEmpty()
						&& !car.getColor().equals(color) && model != null && !model.isEmpty()
						&& !car.getModel().equals(model) && price != "0" && car.getPrice() != price) {
					lbl9.setText(" ");
					TextArea ta1 = new TextArea();
					String printt = dList.get(new Brand(cb.getValue())).getsList().toString();
					ta1.setText(printt);
				    vb.getChildren().add(ta1);
				} else {
					lbl9.setText("Must Choose all Filters");
				}

			} catch (NumberFormatException ex) {
				System.out.println(" ");
			}
		});
		vb.getChildren().addAll(lbl1, lbl2, cb3, lbl3, cb1, lbl4, cb2, lbl5, cb4, lbl9, save);
		bp.setRight(vb);
		bp.setLeft(hb);

		Scene scene4 = new Scene(bp, 600, 550);
		stage3.setScene(scene4);
		stage3.setTitle("Customer");
		stage3.show();

	}

	public void order() {
		Stage s1 = new Stage();
		GridPane gp = new GridPane();
		Label name = new Label("Customer Name :");
		Label age = new Label("Customer Mobile :");
		Label date = new Label("Order Date:");
		Label brand = new Label("Brand : ");
		Label model = new Label("Model : ");
		Label year = new Label("Year : ");
		Label color1 = new Label("Color:");
		Label price1 = new Label("Price:");
		TextField tf = new TextField();
		TextField tf1 = new TextField();
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		TextField tf4 = new TextField();
		TextField tf5 = new TextField();
		TextField tf6 = new TextField();
		TextField tf7 = new TextField();
		Button add = new Button("Place Order");
		add.setOnAction(e -> {
			Label lbl10 = new Label();
			gp.add(lbl10, 1, 9);
			lbl10.setStyle("-fx-text-fill:red");
			try {
				String name1 = tf.getText();
				String mobile = tf1.getText();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date orderDate;
				orderDate = dateFormat.parse(tf2.getText());
				String brand1 = tf3.getText();
				String model1 = tf4.getText();
				String year1 = tf5.getText();
				String color = tf6.getText();
				String price = tf7.getText();
				if (!name1.isEmpty() || !mobile.isEmpty() || !((CharSequence) orderDate).isEmpty() || !brand1.isEmpty()
						|| !model1.isEmpty() || !year1.isEmpty() || !color.isEmpty() || !price.isEmpty()) {
					Order o = new Order(name1, mobile, orderDate, brand1, model1, year1, color, price);
					q.enqueue(o);
					lbl10.setText("Order has been Placed Successfully");
				} else {
					lbl10.setText("Must Fill in All TextFields");
				}
				tf.clear();
				tf1.clear();
				tf2.clear();
				tf3.clear();
				tf4.clear();
				tf5.clear();
				tf6.clear();
				tf7.clear();
				q.print();
			} catch (ParseException e1) {
				System.out.println(" ");
			}
		});

		gp.add(name, 0, 0);
		gp.add(tf, 1, 0);
		gp.add(age, 0, 1);
		gp.add(tf1, 1, 1);
		gp.add(date, 0, 2);
		gp.add(tf2, 1, 2);
		gp.add(brand, 0, 3);
		gp.add(tf3, 1, 3);
		gp.add(model, 0, 4);
		gp.add(tf4, 1, 4);
		gp.add(year, 0, 5);
		gp.add(tf5, 1, 5);
		gp.add(color1, 0, 6);
		gp.add(tf6, 1, 6);
		gp.add(price1, 0, 7);
		gp.add(tf7, 1, 7);
		gp.add(add, 1, 8);
		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene1 = new Scene(gp, 450, 400);
		s1.setScene(scene1);
		s1.setTitle("Place Order");
		s1.show();

	}

}
