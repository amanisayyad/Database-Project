import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {
	static TextArea ta = new TextArea();
	static String currentSec = " ";
	static Image im = new Image("equation.jpg");
	static BackgroundImage image = new BackgroundImage(im, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT, new BackgroundSize(600, 550, true, true, true, true));
	static Background bg = new Background(image);

	static CursorStack sectionStack;
	private static CursorStack tempStack = new CursorStack();
	static Image next = new Image("nextArrow.png");
	static ImageView iv1 = new ImageView(next);
	static Button nextt = new Button("", iv1);
	static int size = 0;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane bp = new BorderPane();
		bp.setBackground(bg);

		VBox vb = new VBox(50);
		Label lbl = new Label("Welcome to My Equation Processor Application!");
		lbl.setFont(Font.font("Lucida Handwriting", FontWeight.BOLD, 30));
		lbl.setStyle("-fx-background-color:#cfe1b9;");
		lbl.setTextFill(Color.BLACK);
		Label lbl1 = new Label("Click the Button Below to Choose a File");
		lbl1.setFont(Font.font("Lucida Handwriting", FontWeight.BOLD, 20));
		lbl1.setStyle("-fx-background-color: black;");
		lbl1.setTextFill(Color.ANTIQUEWHITE);

		Image im = new Image("downButton.png");
		ImageView iv = new ImageView(im);

		iv.setFitWidth(50);
		iv.setFitHeight(50);

		vb.setAlignment(Pos.CENTER);
		vb.setPadding(new Insets(50));
		vb.getChildren().addAll(lbl, lbl1, iv);

		Button load = new Button("Choose File");
		load.setFont(Font.font("Lucida Handwriting", FontWeight.BOLD, 20));
		load.setStyle("-fx-background-color:black;");
		load.setTextFill(Color.ANTIQUEWHITE);
		load.setOnMouseEntered(e -> {
			load.setOpacity(0.5);
		});
		load.setOnMouseExited(e -> {
			load.setOpacity(1.0);
		});
		load.setOnAction(e -> {
			load.setOpacity(0.5);
			FileChooser fc = new FileChooser();
			File file = fc.showOpenDialog(stage);
			Scanner scan;
			try {
				scan = new Scanner(file);
				String tag = "";
				String line = "";
				while (scan.hasNextLine()) {
					line += scan.nextLine();
					line = line.trim();
					if (!line.trim().isEmpty()) {
						tag = getTags(line);
					}
				}
				if (isValidTag(tag)) {
					if (isBalanced(tag)) {
						process(line);
					} else {
						Alert a2 = new Alert(AlertType.ERROR, "Invalid File");
						a2.show();
					}
				} else {
					System.out.println("INVALID");
					Alert a1 = new Alert(AlertType.ERROR, "Invalid Tag Format");
					a1.show();
				}
			}

			catch (FileNotFoundException e1) {
				System.out.println(" ");

			} catch (NullPointerException ex) {

			}

		});

		bp.setTop(vb);
		bp.setCenter(load);

		Scene scene = new Scene(bp, 1000, 500);
		stage.setScene(scene);
		stage.setTitle("Equation Processor");
		stage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public static void process(String line) {
		Stage stage = new Stage();
		BorderPane bp = new BorderPane();
		bp.setBackground(bg);

		HBox hb = new HBox();
		hb.setAlignment(Pos.CENTER);

		Image prev = new Image("prevArrow.png");

		ImageView iv2 = new ImageView(prev);

		iv1.setFitWidth(100);
		iv1.setFitHeight(100);

		iv2.setFitWidth(100);
		iv2.setFitHeight(100);

		Button prevv = new Button("", iv2);
		Button back = new Button("Back");
		back.setOnAction(e -> {
			stage.close();

		});

		Circle c = new Circle(0.5);

		nextt.setContentDisplay(ContentDisplay.CENTER);
		prevv.setContentDisplay(ContentDisplay.CENTER);

		nextt.setShape(c);
		prevv.setShape(c);

		nextt.setStyle("-fx-background-color:rgb(255, 249, 237);");
		prevv.setStyle("-fx-background-color:rgb(255, 249, 237);");
		back.setFont(Font.font("Lucida Handwriting", FontWeight.BOLD, 15));
		back.setStyle("-fx-background-color:black;");
		back.setTextFill(Color.ANTIQUEWHITE);

		nextt.setOnMouseEntered(e -> {
			nextt.setOpacity(0.5);
		});
		nextt.setOnMouseExited(e -> {
			nextt.setOpacity(1.0);
		});
		prevv.setOnMouseEntered(e -> {
			prevv.setOpacity(0.5);
		});
		prevv.setOnMouseExited(e -> {
			prevv.setOpacity(1.0);
		});
		back.setOnMouseEntered(e -> {
			back.setOpacity(0.5);
		});
		back.setOnMouseExited(e -> {
			back.setOpacity(1.0);
		});

		ta.setMinHeight(600);
		ta.setMinWidth(400);
		ta.setMaxHeight(600);
		ta.setMaxWidth(400);
		ta.setEditable(false);
		Font boldFont = Font.font("Arial", FontWeight.BOLD, 13);
		ta.setFont(boldFont);

		bp.setAlignment(back, Pos.TOP_LEFT);
		bp.setAlignment(hb, Pos.CENTER);

		hb.getChildren().addAll(prevv, ta, nextt);
		bp.setLeft(back);
		bp.setCenter(hb);

		Scene scene = new Scene(bp, 1000, 500);
		stage.setScene(scene);
		stage.setTitle("Equation Processor");
		stage.show();

		prevv.setOpacity(0.5);
		prevv.setDisable(true);

		sectionStack = sectionDivider(line);// cursor stack of sections
		if (!sectionStack.isEmpty()) {
			currentSec = (String) sectionStack.peek();
			ta.appendText(processSection(currentSec));
			nextt.setOpacity(1.0);
			nextt.setDisable(false);
		} else {
			nextt.setOpacity(0.5);
			nextt.setDisable(true);
		}
		nextt.setOnAction(e -> {
			if (!sectionStack.isEmpty()) {
				currentSec = (String) sectionStack.pop();
				ta.setText(processSection(currentSec));
				prevv.setOpacity(1.0);
				prevv.setDisable(false);
				tempStack.push(currentSec);
			} else if (sectionStack.isEmpty()) {
				nextt.setOpacity(0.5);
				nextt.setDisable(true);
			} else {
				nextt.setOpacity(0.5);
				nextt.setDisable(true);
			}
		});

		prevv.setOnAction(e -> {
			if (!tempStack.isEmpty()) {
				currentSec = (String) tempStack.pop();
				ta.setText(processSection(currentSec));
				nextt.setOpacity(1.0);
				nextt.setDisable(false);
			} else {
				prevv.setOpacity(0.5);
				prevv.setDisable(true);
				prevv.setOnMouseEntered(ex -> {
					prevv.setOpacity(0.5);
				});
				prevv.setOnMouseExited(exc -> {
					prevv.setOpacity(0.5);
				});
			}
		});
	}

	private static String processSection(String currentSec) {
		String currentEq = " ";
		StringBuilder finalText = new StringBuilder();
		try {
			if (currentSec.contains("<infix>") && currentSec.contains("</infix>") && currentSec.contains("<postfix>")
					&& currentSec.contains("</postfix>")) {
				if (currentSec.contains("<infix>") && currentSec.contains("</infix>")) {
					String infixPart = extractSubstring(currentSec, "<infix>", "</infix>");
					CursorStack infixEquations = extractInfix(infixPart);
					while (!infixEquations.isEmpty()) {
						currentEq = (String) infixEquations.pop();
						System.out.println(currentEq);
						finalText.append(" Infix:          ").append("\n").append("* ").append(currentEq)
								.append(" ==> ").append(infixToPostfix10(currentEq)).append(" ==> ")
								.append(evaluatePostfix(infixToPostfix10(currentEq))).append("\n").append("\n");
					}
				}
				if (currentSec.contains("<postfix>") && currentSec.contains("</postfix>")) {
					String postfixPart = extractSubstring(currentSec, "<postfix>", "</postfix>");
					CursorStack postfixEquations = extractPostfix(postfixPart);
					while (!postfixEquations.isEmpty()) {
						currentEq = (String) postfixEquations.pop();
						finalText.append(" Postfix:          ").append("\n").append("* ").append(currentEq)
								.append(" ==> ").append(postfixToPrefix(currentEq)).append(" ==> ")
								.append(evaluatePrefix(postfixToPrefix(currentEq))).append("\n").append("\n");
					}
				}
			} else if (currentSec.contains("<infix>") && currentSec.contains("</infix>")) {
				String infixPart = extractSubstring(currentSec, "<infix>", "</infix>");
				CursorStack infixEquations = extractInfix(infixPart);
				while (!infixEquations.isEmpty()) {
					currentEq = (String) infixEquations.pop();
					finalText.append(" Infix:          ").append("\n").append("* ").append(currentEq).append(" ==> ")
							.append(infixToPostfix10(currentEq)).append(" ==> ")
							.append(evaluatePostfix(infixToPostfix10(currentEq))).append("\n").append("\n");
				}
			} else if (currentSec.contains("<postfix>") && currentSec.contains("</postfix>")) {
				String postfixPart = extractSubstring(currentSec, "<postfix>", "</postfix>");
				CursorStack postfixEquations = extractPostfix(postfixPart);
				while (!postfixEquations.isEmpty()) {
					currentEq = (String) postfixEquations.pop();
					finalText.append(" Postfix:          ").append("\n").append("* ").append(currentEq).append(" ==> ")
							.append(postfixToPrefix(currentEq)).append(" ==> ")
							.append(evaluatePrefix(postfixToPrefix(currentEq))).append("\n").append("\n");
				}
			} else {
				finalText.append("No more sections available");
				nextt.setOnMouseEntered(e -> {
					nextt.setOpacity(0.5);
				});
				nextt.setOnMouseExited(e -> {
					nextt.setOpacity(0.5);
				});
			}
		} catch (NullPointerException exc) {

		}

		return finalText.toString();
	}

	public static CursorStack sectionDivider(String line) {
		CursorStack sectionsStack = new CursorStack();
		CursorStack tempStack = new CursorStack();

		String[] parts = line.split("</section>");

		for (String part : parts) {
			String trimmedPart = part.trim();
			if (!trimmedPart.isEmpty()) {
				String processedPart = trimmedPart.replaceAll("(?<=<equation>)(.+?)(?=</equation>)",
						"$1".replaceAll("\\s+", " "));
				tempStack.push(processedPart + "</section>");
			}
		}
		while (!tempStack.isEmpty()) {
			sectionsStack.push(tempStack.pop());
		}
		tempStack.clear();
		return sectionsStack;
	}

	public static CursorStack extractEquations(String section) {
		CursorStack equationsStack = new CursorStack();
		String[] parts = section.split("</equation>");

		for (String part : parts) {
			String trimmedPart = part.trim();
			if (trimmedPart.startsWith("<equation>")) {
				equationsStack.push(trimmedPart.replace("<equation>", ""));
			}
		}

		return equationsStack;
	}

	public static CursorStack extractInfix(String infix) {
		CursorStack equations = new CursorStack();
		CursorStack tempStack = new CursorStack();

		infix = infix.trim();
		infix = infix.replace("<infix>", "").replace("</infix>", "");
		String[] parts = infix.split("</equation>");

		for (String part : parts) {
			String trimmedPart = part.trim();
			if (trimmedPart.startsWith("<equation>")) {
				tempStack.push(trimmedPart.replace("<equation>", ""));
			}
		}
		while (!tempStack.isEmpty()) {
			equations.push(tempStack.pop());
		}
		tempStack.clear();
		return equations;
	}

	public static CursorStack extractPostfix(String postfix) {
		CursorStack equations = new CursorStack();
		CursorStack tempStack = new CursorStack();

		postfix = postfix.trim();
		postfix = postfix.replace("<postfix>", "").replace("</postfix>", "");
		String[] parts = postfix.split("</equation>");

		for (String part : parts) {
			String trimmedPart = part.trim();
			if (trimmedPart.startsWith("<equation>")) {
				tempStack.push(trimmedPart.replace("<equation>", ""));
			}
		}
		while (!tempStack.isEmpty()) {
			equations.push(tempStack.pop());
		}
		tempStack.clear();
		return equations;

	}

	public static boolean isValidInfix(String s) {
		if (isValid(s)) {
			return checkBalance(s);
		}
		return false;
	}

	private static boolean isValid(String s) {
		boolean contains = true;
		for (char c : s.toCharArray()) {
			if (Character.isDigit(c) || isOperator(c)) {
				contains = true;
			} else {
				contains = false;
			}
		}
		return contains;
	}

	private static boolean isOperator(char ch) {
		return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%';
	}

	private static boolean checkBalance(String s) {
		CursorStack stack = new CursorStack();
		boolean last = false;

		for (char c : s.toCharArray()) {
			if (Character.isDigit(c)) {
				last = true;
			} else if (isOperator(c)) {
				if (!last) {
					return false;
				}
				last = false;
			} else if (c == '(') {
				stack.push(c);
				last = false;
			} else if (c == ')') {
				if (stack.isEmpty() || (Character) stack.pop() != '(') {
					return false; // Unbalanced parentheses
				}
				last = true;
			} else {
				return false; // Invalid character
			}
		}
		boolean isBalanced = last && stack.isEmpty();
		stack.clear();
		return isBalanced;
	}

	public static String extractSubstring(String source, String startTag, String endTag) {
		int startIndex = source.indexOf(startTag);
		int endIndex = source.indexOf(endTag, startIndex + startTag.length());

		if (startIndex != -1 && endIndex != -1) {
			return source.substring(startIndex, endIndex + endTag.length());
		} else {
			return "";
		}
	}

	public static boolean isBalanced(String tags) {
		CursorStack stack = new CursorStack();
		boolean isBalanced = true;
		String[] split = tags.trim().split(",");
		for (int i = 0; i < split.length; i++) {
			String currentTag = split[i];
			if (isOpen(currentTag)) {
				stack.push(currentTag);
			} else if (isClosed(currentTag)) {
				if (stack.isEmpty()) {
					isBalanced = false;
				}
				try {
					String openTag = (String) stack.pop();
					if (!compareTags(openTag, currentTag)) {
						isBalanced = false;
					}
				} catch (NullPointerException e) {
					System.out.println(" ");
				}
			}
		}
		stack.clear();
		return isBalanced;
	}

	public static String getTags(String line) {
		line = line.replaceAll("\\s+", "");
		String tags = "";
		int startIndex = 0;

		while (startIndex < line.length()) {
			int openTagIndex = line.indexOf('<', startIndex);
			int closeTagIndex = line.indexOf('>', openTagIndex);

			if (openTagIndex != -1 && closeTagIndex != -1) {
				String tag = line.substring(openTagIndex, closeTagIndex + 1);
				tags += tag + ",";
				startIndex = closeTagIndex + 1;
			} else {
				break; // No more tags found
			}
		}

		return tags;
	}

	public static boolean isOpen(String tag) { // check if it's an open tag
		return tag.equals("<242>") || tag.equals("<section>") || tag.equals("<infix>") || tag.equals("<postfix>")
				|| tag.equals("<equation>");
	}

	public static boolean isClosed(String tag) { // check if it's a closed tag

		return tag.equals("</242>") || tag.equals("</section>") || tag.equals("</infix>") || tag.equals("</postfix>")
				|| tag.equals("</equation>");
	}

	public static boolean compareTags(String openTag, String closeTag) { // compare open and closed tags
		return (openTag.equals("<242>") && closeTag.equals("</242>"))
				|| (openTag.equals("<section>") && closeTag.equals("</section>"))
				|| (openTag.equals("<infix>") && closeTag.equals("</infix>"))
				|| (openTag.equals("<postfix>") && closeTag.equals("</postfix>"))
				|| (openTag.equals("<equation>") && closeTag.equals("</equation>") && !openTag.equals("</equation>")
						&& !closeTag.equals("<equation<"));
	}

	private static boolean isValidTag(String tag) {
		String[] tags = tag.split(",");
		for (String token : tags) {
			if (!token.isEmpty() && !isOpen(token) && !isClosed(token)) {
				return false; // Invalid tag
			}
		}
		return true;
	}

	public static String postfixToPrefix(String postfix) {
		CursorStack stack = new CursorStack();
		String prefix = "";
		String[] tags = postfix.split(" ");
		String tag;

		for (int i = 0; i < tags.length; i++) {
			tag = tags[i].trim();

			if (!tag.equals("")) {
				if (!isOperator(tag)) {
					stack.push(tag);
				} else {
					String second = (String) stack.pop();
					String first = (String) stack.pop();
					String prefixx = tag + " " + first + " " + second;
					stack.push(prefixx);
				}
			}

		}
		String result = (String) stack.pop();
		stack.clear();
		return result;
	}

	public static double evaluatePrefix(String prefix) {
		CursorStack stack = new CursorStack();
		String[] tags = prefix.split("\\s+");
		for (int i = tags.length - 1; i >= 0; i--) {
			String tag = tags[i].trim();
			if (!tag.equals("")) {
				if (isNumeric(tag)) {
					try {
						stack.push(Double.parseDouble(tag));
					} catch (NumberFormatException e) {
						System.err.println("Invalid numeric token: " + tag);
						ta.appendText("Invalid Numeric Token: " + tag);
						return Double.NaN;
					}
				} else if (isOperator(tag)) {
					double first = (double) stack.pop();
					double second = (double) stack.pop();
					double result = calculate(first, second, tag);
					stack.push(result);
				} else {
					System.err.println("Invalid Token: " + tag);
					ta.appendText("Invalid Token: " + tag);
					return Double.NaN;
				}
			}
		}
		double result = (double) stack.pop();
		stack.clear();
		return result;
	}

	public static String infixToPostfix(String infix) {
		CursorStack stack = new CursorStack();
		String postfix = "";
		String[] tags = infix.split(" ");
		String tag;
		for (int i = 0; i < tags.length; i++) {
			tag = tags[i].trim();
			if (!tag.equals("")) {
				if (!isOperator(tag)) {
					postfix += tag + " ";
				} else if (tag.equals("(")) {
					stack.push(tag);
				} else if (tag.equals(")")) {
					while (!stack.isEmpty() && !stack.peek().equals("(")) {
						postfix += stack.pop() + " ";
					}
					if (!stack.isEmpty() && !stack.peek().equals("(")) {
						return null;
					} else if (!stack.isEmpty()) {
						stack.pop();
					}
				} else {
					while (!stack.isEmpty() && precedence(tag) <= precedence((String) stack.peek())) {
						postfix += stack.pop() + " ";
					}
					stack.push(tag);
				}
			}
		}
		while (!stack.isEmpty()) {
			postfix += stack.pop() + " ";
		}
		stack.clear();

		return postfix.trim();
	}

	public static String infixToPostfix10(String infix) {
		CursorStack stack = new CursorStack();
		StringBuilder postfix = new StringBuilder();
		String[] tokens = infix.split(" ");
		String token;

		int openParentheses = 0;

		for (int i = 0; i < tokens.length; i++) {
			token = tokens[i].trim();
			if (!token.equals("")) {
				if (!isOperator(token)) {
					postfix.append(token).append(" ");
				} else if (token.equals("(")) {
					stack.push(token);
					openParentheses++;
				} else if (token.equals(")")) {
					while (!stack.isEmpty() && !stack.peek().equals("(")) {
						postfix.append(stack.pop()).append(" ");
					}
					if (stack.isEmpty()) {
						return "Error: Unbalanced parentheses (missing opening parenthesis)";
					} else {
						stack.pop(); // Pop the opening parenthesis
						openParentheses--;
					}
				} else {
					while (!stack.isEmpty() && precedence(token) <= precedence((String) stack.peek())) {
						postfix.append(stack.pop()).append(" ");
					}
					stack.push(token);
				}
			}
		}

		while (!stack.isEmpty()) {
			postfix.append(stack.pop()).append(" ");
		}

		if (openParentheses > 0) {
			return "Error: Unbalanced parentheses (missing closing parenthesis)";
		}

		return postfix.toString().trim();
	}

	private static boolean isOperator(String c) {
		return c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/") || c.equals("^") || c.equals("%")
				|| c.equals("(") || c.equals(")");
	}

	private static int precedence(String s) {
		switch (s) {
		case "+":
		case "-":
			return 1;
		case "*":
		case "/":
		case "%":
			return 2;
		case "^":
			return 3;
		}
		return -1;
	}

	public static double evaluatePostfix(String postfix) {
		CursorStack stack = new CursorStack();
		String[] tags = postfix.split(" ");
		for (String tag : tags) {
			tag = tag.trim();
			if (!tag.equals("")) {
				if (isNumeric(tag)) {
					try {
						stack.push(Double.parseDouble(tag));
					} catch (NumberFormatException e) {
						System.err.println("Invalid numeric token: " + tag);
						return Double.NaN;
					}
				} else if (isOperator(tag)) {
					double second = (double) stack.pop();
					double first = (double) stack.pop();
					double result = calculate(first, second, tag);
					stack.push(result);
				} else {
					System.err.println("Invalid token: " + tag);
					return Double.NaN;
				}
			}
		}
		double result = (double) stack.pop();
		stack.clear();
		return result;
	}

	private static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?"); // Check if the string is a number
	}

	private static double calculate(double first, double second, String operator) {
		switch (operator) {
		case "+":
			return first + second;
		case "-":
			return first - second;
		case "*":
			return first * second;
		case "/":
			return first / second;
		case "%":
			return first % second;
		case "^":
			return Math.pow(first, second);
		default:
			throw new IllegalArgumentException("Invalid operator: " + operator);
		}
	}
}
