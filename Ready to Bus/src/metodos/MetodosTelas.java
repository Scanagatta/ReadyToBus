package metodos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import visual.Main;

public class MetodosTelas {
	public void carregarTela(String nome) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(nome));

		try {
			AnchorPane loginView = (AnchorPane) loader.load();
			Main.root.setCenter(loginView);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void mostrarSobre() {
		Stage stage = new Stage();

		Text texto = new Text();
		stage.setScene(new Scene(new StackPane(texto)));
		stage.setTitle("Importante");
		texto.setText("ReadyToBus é um software desenvolvido para o\ncontrole de passageiros em ônibus escolares\n\n"
				+ "As empresas tem uma enorme facilidade no controle de\nseus motoristas, passageiros e viagens\n\nDesenvolvido por acadêmicos da UNOESC- Xanxerê "
				+ "do curso de\nTecnologia em Análise e Desenvolvimento de Sistemas - 4ª fase\n\nAndre Vicensi Uliana e João Victor Scanagatta\n\n\n\n ReadyToBus® 2017");

		stage.setWidth(390);
		stage.setHeight(300);
		stage.show();
	}

	/*
	 * funciona so que o burro do eclipse abre o codigo html ao inves do site no
	 * navegador
	 */
	public void abrirSite(String endereco) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(endereco));
			while (br.ready()) {
				String linha = br.readLine();
				System.out.println(linha);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void carregarImagem(ImageView imagem, Boolean dirigindo) {
		InputStream input;
		Image img;
		if (dirigindo == true) {
			input = getClass().getResourceAsStream("/visual/sim.png");
			img = new Image(input);
			imagem.setImage(img);
		} else {
			input = getClass().getResourceAsStream("/visual/nao.png");
			img = new Image(input);
			imagem.setImage(img);
		}
	}

}
