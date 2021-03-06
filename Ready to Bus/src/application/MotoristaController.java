package application;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

import dao.DaoFactory;
import dao.Passageiro_ViagemDao;
import dao.ViagemDao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import metodos.AplicacaoSessao;
import metodos.MetodosTelas;
import model.Passageiro_Viagem;
import model.Viagem;

public class MotoristaController {

	@FXML
	private TableView<Passageiro_Viagem> tblLista;

	@FXML
	private TableColumn<Passageiro_Viagem, String> tbcPassageiro;

	@FXML
	private TableColumn<Passageiro_Viagem, String> tbcTelefone;

	@FXML
	private TableColumn<Passageiro_Viagem, Image> tbcStatus;

	@FXML
	private TableColumn<Passageiro_Viagem, Image> tbcCheck;

	@FXML
	private Button btnDirigir;

	@FXML
	private Button btnChegada;

	@FXML
	private Label ldataDia;

	@FXML
	private ImageView imgDirigindo;

	@FXML
	private Button btnSair;

	@FXML
	private ComboBox<Viagem> cbxViagem;

	private MetodosTelas tela = new MetodosTelas();
	// private static MotoristaDao motoristaDao =
	// DaoFactory.get().motoristaDao();
	private static ViagemDao viagemDao = DaoFactory.get().viagemDao();
	public Viagem viagem = new Viagem();
	public Viagem viagemData = new Viagem();
	// private MetodosLogin login = new MetodosLogin();
	private Image dirigindo = new Image("/visual/sim.png");
	private Image chegada = new Image("/visual/nao.png");
	LocalTime tempo;
	Viagem data;

	public static final long TEMPO = (1000 * 10); // atualiza a cada 10 segundos

	public static Integer codmotorista;
	public static Integer codviagem;

	private static Passageiro_ViagemDao passageiroViagemDao = DaoFactory.get().passageiro_ViagemDao();

	public void initialize() {

		cbxViagem.setItems(FXCollections
				.observableArrayList(viagemDao.listarMotorista(AplicacaoSessao.motorista.getIdMotorista())));

		carregarLista();
	}

	@FXML
	void onChegada(ActionEvent event) {
		viagemDao.alterarDiringindo(codviagem, false);
		tempo = LocalTime.now();
		viagem.setChegada(tempo);
		viagemDao.alterarChegada(codviagem, tempo);
		imgDirigindo.setImage(chegada);
	}

	@FXML
	void onDirigir(ActionEvent event) {
		viagemDao.alterarDiringindo(codviagem, true);
		tempo = LocalTime.now();
		viagem.setSaida(tempo);
		viagemDao.alterarSaida(codviagem, tempo);
		imgDirigindo.setImage(dirigindo);
	}

	@FXML
	void onSair(ActionEvent event) {
		tela.carregarTela("/visual/TelaLogin.fxml");
	}

	@FXML
	void onSetarValor(ActionEvent event) {
		codviagem = cbxViagem.getValue().getIdViagem();
		codmotorista = AplicacaoSessao.motorista.getIdMotorista();

		cbxViagem.setItems(FXCollections
				.observableArrayList(viagemDao.listarMotorista(AplicacaoSessao.motorista.getIdMotorista())));

		ldataDia.setText(cbxViagem.getValue().getData().toString());

		tbcPassageiro.setCellValueFactory(new PropertyValueFactory<>("passageiro"));
		tbcTelefone.setCellValueFactory(new PropertyValueFactory<>("TelefoneNumero"));
		tbcStatus.setCellValueFactory(new PropertyValueFactory<>("ImagemStatus"));
		tbcCheck.setCellValueFactory(new PropertyValueFactory<>("ImagemCheck"));

		tblLista.setItems(
				FXCollections.observableArrayList(passageiroViagemDao.ListaMotorista(codmotorista, codviagem)));

		tbcCheck.setCellFactory(
				new Callback<TableColumn<Passageiro_Viagem, Image>, TableCell<Passageiro_Viagem, Image>>() {
					@Override
					public TableCell<Passageiro_Viagem, Image> call(TableColumn<Passageiro_Viagem, Image> param) {
						final ImageView imageView = new ImageView();
						TableCell<Passageiro_Viagem, Image> cell = new TableCell<Passageiro_Viagem, Image>() {
							protected void updateItem(Image item, boolean empty) {
								if (item != null) {
									imageView.setFitHeight(20);
									imageView.setFitWidth(20);
									imageView.setImage(item);
								}
							}
						};
						cell.setGraphic(imageView);
						return cell;
					}
				});

		tbcStatus.setCellFactory(
				new Callback<TableColumn<Passageiro_Viagem, Image>, TableCell<Passageiro_Viagem, Image>>() {
					@Override
					public TableCell<Passageiro_Viagem, Image> call(TableColumn<Passageiro_Viagem, Image> param) {
						final ImageView imageView1 = new ImageView();
						TableCell<Passageiro_Viagem, Image> cell = new TableCell<Passageiro_Viagem, Image>() {
							protected void updateItem(Image item1, boolean empty) {
								if (item1 != null) {
									imageView1.setFitHeight(20);
									imageView1.setFitWidth(100);
									imageView1.setImage(item1);
								}
							}
						};
						cell.setGraphic(imageView1);
						return cell;
					}
				});

		if (cbxViagem.getValue().getDirigindo().equals(true)) {
			tela.carregarImagem(imgDirigindo, true);
		} else {
			tela.carregarImagem(imgDirigindo, false);
		}

	}

	public void carregarLista() {

		Timer timer = null;
		if (timer == null) {
			timer = new Timer();
			TimerTask tarefa = new TimerTask() {
				public void run() {
					try {
						tblLista.setItems(FXCollections
								.observableArrayList(passageiroViagemDao.ListaMotorista(codmotorista, codviagem)));
						// chamar metodo
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			timer.scheduleAtFixedRate(tarefa, TEMPO, TEMPO);
		}
	}

}
