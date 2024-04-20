import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main extends Application {
	CLinkedList<Location> dList = new CLinkedList();
	static String l;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Image im = new Image("d969eb398bda76328b3387b34c29faf6.png");
		BackgroundImage image = new BackgroundImage(im, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bg = new Background(image);

		TabPane tp = new TabPane();

		Tab tab1 = new Tab("Main");
		BorderPane bp = new BorderPane();
		HBox hb2 = new HBox(5);
		Label lbl1 = new Label("Welcome to My Martyrs Statistics Program");
		lbl1.setTextFill(Color.WHITE);
		lbl1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		hb2.setAlignment(Pos.CENTER);
		hb2.setStyle("-fx-background-color:black");
		hb2.getChildren().add(lbl1);
		bp.setBackground(bg);
		StackPane sp = new StackPane();
		Button load = new Button("Load Martyrs File");
		load.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		sp.getChildren().add(load);
		load.setAlignment(Pos.CENTER);
		bp.setTop(hb2);
		bp.setCenter(sp);
		tab1.setContent(bp);

		load.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			File file = fc.showOpenDialog(stage);// choose file to load martyr data
			try {
				if (file.exists()) {
					Scanner scan = new Scanner(file);
					String line = ",";
					while (scan.hasNextLine()) {
						line = scan.nextLine();
						Martyr m = new Martyr(line);// create martyr object from reading the file lines
						l = m.getLoc();// store location into string
						Location location = dList.get(new Location(l));// storing location object into method that
																		// checks if location exists or not(to skip
																		// duplicate locations)
						if (location != null) {// if location exists
							dList.get(location);// gets location node from doubly list and
												// adds a new martyr record to it
						} else {// location doesn't exist
							location = new Location(l);// creates new location
							dList.insertSorted(location);// inserts new location into doubly linked list
							dList.get(location);
						}
					}
					dList.traverase();

				}
			}

			catch (FileNotFoundException ex) {
				System.out.println("file not found");
			}

		});

		Tab tab2 = new Tab("Location");
		GridPane gp = new GridPane();
		gp.setBackground(bg);
		Label insert = new Label("Insert New Location Record:");
		insert.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		Label update = new Label("Delete a Location Record:");
		update.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		Label search = new Label("Search and Display Location Record:");
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
			gp.add(lbl2, 0, 5);
			String location = tf.getText();
			Location loc = new Location(location);
			if (dList.get(loc) != null) {
				lbl2.setVisible(true);
				lbl2.setText("This location already exists");
			} else {
				lbl2.setVisible(false);
				Label lbl3 = new Label();
				lbl3.setStyle("-fx-text-fill:red");
				lbl3.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
				gp.add(lbl3, 0, 6);
				dList.insertSorted(loc);
				lbl3.setVisible(true);
				lbl3.setText("This location has been added succesfully");
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
			gp.add(lbl3, 0, 7);
			String location = tf2.getText();
			Location loc = new Location(location);
			if (dList.get(loc) != null) {
				dList.deleteSorted(loc);
				lbl3.setText("This location has been deleted successfully");
			} else {
				lbl3.setText("This location doesn't exist");
			}
			tf.clear();

		});

		Button search1 = new Button("Search");
		search1.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		search1.setOnAction(e -> {
			Label lbl4 = new Label();
			lbl4.setStyle("-fx-text-fill:red");
			lbl4.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
			gp.add(lbl4, 0, 8);
			String location = tf3.getText();
			Location loc = new Location(location);
			if (dList.get(loc) != null) {
				lbl4.setText(location);
			} else {
				lbl4.setText("This location doesn't exist");
			}
			tf3.clear();

		});

		gp.add(insert, 0, 0);
		gp.add(tf, 1, 0);
		gp.add(insert1, 2, 0);
		gp.add(update, 0, 1);
		gp.add(tf2, 1, 1);
		gp.add(delete, 2, 1);
		gp.add(search, 0, 2);
		gp.add(tf3, 1, 2);
		gp.add(search1, 2, 2);
		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.CENTER);
		tab2.setContent(gp);

		Tab tab3 = new Tab("Martyrs");
		GridPane gp2 = new GridPane();
		gp2.setBackground(bg);
		Button insert2 = new Button("Insert a New Martyr Record");
		insert2.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		insert2.setOnAction(e -> {
			insert();
		});
		Button update2 = new Button("Delete a Martyr Record");
		update2.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		update2.setOnAction(e -> {
			delete();
		});
		Button search2 = new Button("Search for Martyr Record by Name");
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

		Tab tab4 = new Tab("Statistics");
		BorderPane bp1 = new BorderPane();
		bp1.setBackground(bg);
		TextArea ta = new TextArea();
		Button gen = new Button("Generate Summary Report");
		gen.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		gen.setOnAction(e -> {
			Statistics s = new Statistics();
			int ageCount = s.getAgeCount();
			int genderCount = s.getGenderCount();
			double averageAge = s.getAverageAge();
			ta.setText("The Number of Martyrs by Age:  " + ageCount + "The number of Martyrs by Gender: " + genderCount
					+ " The Average Age of Martyrs: " + averageAge);

		});
		Button previous = new Button("Previous");
		previous.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		Button next = new Button("Next");
		next.setFont(Font.font("Ariel", FontWeight.BOLD, 15));
		bp1.setTop(gen);
		bp1.setCenter(ta);
		bp1.setLeft(previous);
		bp1.setRight(next);

		tab4.setContent(bp1);

		Tab tab5 = new Tab("Save");
		BorderPane bp3 = new BorderPane();
		bp3.setBackground(bg);
		Label lbl7 = new Label("Save Martyr Records to a File");
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
			File file = fc.showSaveDialog(stage);

			String data = dList.toString();

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				writer.write(data);
				System.out.println("Data saved successfully!");
			} catch (IOException ex) {
				System.out.println("Error occurred while saving the file");
			}

		});

		tp.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);

		Scene scene = new Scene(tp, 500, 450);
		stage.setTitle("Martyrs");
		stage.setScene(scene);
		stage.show();

	}

	public void insert() {
		Stage s1 = new Stage();
		GridPane gp = new GridPane();
		Label name = new Label("Name:");
		Label age = new Label("Age:");
		Label date = new Label("Date of Death:");
		Label Gender = new Label("Gender:");
		Label notes = new Label("Notes:");
		TextField tf = new TextField();
		TextField tf1 = new TextField();
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		TextField tf4 = new TextField();
		Button add = new Button("Add");
		add.setOnAction(e -> {
			Label lbl = new Label();
			gp.add(lbl, 1, 5);
			lbl.setStyle("-fx-text-fill:red");
			String name1 = tf.getText();
			int age1 = Integer.parseInt(tf1.getText());
			String date1 = tf3.getText();
			char gender = tf4.getText().toString().charAt(0);
			Martyr martyr = new Martyr(name1, age1, date1, gender);
			l = martyr.getLoc();
			Location location = dList.get(new Location(l));
//			if (location.getsList().findSorted(martyr).equals(martyr)) {
//				lbl.setText("Martyr Record Already Exists");
//
//			} else {
//				location.getsList().insertSorted(martyr);
//				lbl.setText("Martyr record has been successfully added");
//			}
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
		gp.add(add, 1, 4);
		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene1 = new Scene(gp, 450, 300);
		s1.setScene(scene1);
		s1.setTitle("Add Martyr");
		s1.show();

	}

	public void search() {
		Stage stage2 = new Stage();
		GridPane gp = new GridPane();
		Button search = new Button("Search");
		Label enter = new Label("Enter martyr's name:");
		TextField tf = new TextField();

		search.setOnAction(e -> {
			Label lbl1 = new Label();
			lbl1.setStyle("-fx-text-fill:red");
			gp.add(lbl1, 1, 5);
			String name = tf.getText();
			Location location = new Location(name);
//			if (dList.get(location) == null) {
//				lbl1.setText("This martyr record doesn't exist");
//			} else {
//				lbl1.setText(name + location.getsList().toString());
//			}
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
		Label name = new Label("Name:");
		Label age = new Label("Age:");
		Label location1 = new Label("Location:");
		Label date = new Label("Date of Death:");
		Label Gender = new Label("Gender:");
		Label notes = new Label("Notes:");
		TextField tf = new TextField();
		TextField tf1 = new TextField();
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		TextField tf4 = new TextField();
		Button delete = new Button("Delete");
		delete.setOnAction(e -> {
			Label lbl = new Label();
			lbl.setStyle("-fx-text-fill:red");
			gp.add(lbl, 1, 5);
			String name1 = tf.getText();
			int age1 = Integer.parseInt(tf1.getText());
			String date1 = tf3.getText();
			char gender = tf4.getText().charAt(0);
			Martyr martyr = new Martyr(name1, age1, date1, gender);
			l = martyr.getLoc();
			Location location = dList.get(new Location(l));
//			if (location.getsList().findRecSorted(martyr).equals(martyr)) {
//				location.getsList().deleteSorted(martyr);
//				lbl.setText("Martyr record has been successfully deleted");
//			} else {
//				lbl.setText("Martyr Record Doesn't Exist");
//			}
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
		gp.add(delete, 1, 4);
		gp.setVgap(7);
		gp.setHgap(7);
		gp.setAlignment(Pos.TOP_LEFT);

		Scene scene1 = new Scene(gp, 450, 300);
		s2.setScene(scene1);
		s2.setTitle("Delete Martyr");
		s2.show();
	}

}
