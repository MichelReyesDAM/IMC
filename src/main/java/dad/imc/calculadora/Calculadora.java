package dad.imc.calculadora;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Calculadora extends Application {

	private Label pesoLabel;
	private Label alturaLabel;
	private Label imcLabel;
	private Label kgLabel;
	private Label cmLabel;
	private Label imcFormula;
	private Label imcCalculadoLabel;
	private TextField alturaText;
	private TextField pesoText;
	private DoubleProperty pesoDouble;
	private DoubleProperty alturaDouble;
	private DoubleProperty imcDouble;

	public void start(Stage primaryStage) throws Exception {

		pesoLabel = new Label("Peso: ");
		alturaLabel = new Label("Altura: ");
		imcLabel = new Label("IMC: ");
		kgLabel = new Label("kg");
		cmLabel = new Label("cm");
		imcFormula = new Label("(peso*altura^2)");
		imcCalculadoLabel = new Label("Bajo peso | Normal | Sobrepeso | Obeso");
		alturaText = new TextField();
		pesoText = new TextField();
		pesoDouble = new SimpleDoubleProperty();
		alturaDouble = new SimpleDoubleProperty();
		imcDouble = new SimpleDoubleProperty();

		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setFillWidth(false);

		HBox pesoHbox = new HBox(5);
		pesoHbox.setAlignment(Pos.CENTER);
		pesoHbox.setFillHeight(false);
		pesoHbox.getChildren().addAll(pesoLabel, pesoText, kgLabel);

		HBox alturaHbox = new HBox(5);
		alturaHbox.setAlignment(Pos.CENTER);
		alturaHbox.setFillHeight(false);
		alturaHbox.getChildren().addAll(alturaLabel, alturaText, cmLabel);

		HBox imcHbox = new HBox(5);
		imcHbox.setAlignment(Pos.BOTTOM_LEFT);
		imcHbox.setFillHeight(false);
		imcHbox.getChildren().addAll(imcLabel, imcFormula);

		root.getChildren().addAll(pesoHbox, alturaHbox, imcHbox, imcCalculadoLabel);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("IMC");
		primaryStage.setScene(scene);
		primaryStage.show();

		Bindings.bindBidirectional(pesoText.textProperty(), pesoDouble, new NumberStringConverter());
		Bindings.bindBidirectional(alturaText.textProperty(), alturaDouble, new NumberStringConverter());
		
		imcDouble.bind(pesoDouble.divide((alturaDouble.divide(100)).multiply(alturaDouble.divide(100))));
		
		imcFormula.textProperty().bind(imcDouble.asString("%.2f"));

		imcFormula.textProperty().addListener(e -> {
			if (imcDouble.doubleValue() < 18.5) {
				imcCalculadoLabel.setText("Bajo peso.");
			} else if (imcDouble.doubleValue() >= 18.5 && imcDouble.doubleValue() < 25) {
				imcCalculadoLabel.setText("Normal.");
			} else if (imcDouble.doubleValue() >= 25 && imcDouble.doubleValue() < 30) {
				imcCalculadoLabel.setText("Sobrepeso.");
			} else if (imcDouble.doubleValue() > 30) {
				imcCalculadoLabel.setText("Obeso.");
			}
		});

	}

	public static void main(String[] args) {
		launch(args);

	}

}
