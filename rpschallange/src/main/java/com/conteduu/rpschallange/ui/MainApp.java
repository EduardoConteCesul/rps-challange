package com.conteduu.rpschallange.ui;

import com.conteduu.rpschallange.dao.ScoreDao;
import com.conteduu.rpschallange.model.Move;
import com.conteduu.rpschallange.model.PlayerStats;
import com.conteduu.rpschallange.model.Score;
import com.conteduu.rpschallange.util.CpuStratagy;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainApp extends Application {

    private Button btnPlay, btnReset;
    private Label labelStatusDaPartida, labelPlayer, labelCPU, labelRes, qntdDeJogadas;
    private ProgressBar progressoPartida;
    private HBox jogadasPossiveis;
    private ListView<String> listView;

    private CpuStratagy cpuStratagy = new CpuStratagy();
    PlayerStats playerStats;
    ScoreDao scoreDao = new ScoreDao();

    private final ObservableList<String> history = FXCollections.observableArrayList();
    private static final int MAX_ROUND = 10;
    private static final int WINDOW_AI = 3;

    @Override
    public void start(Stage stage) {

        // Campo antes do ComboBox de jogada
        Label labelHeader = new Label("Sua jogada: ");

        // ComboBox com os dados do ENUM Move
        ComboBox<Move> comboBoxHeader = new ComboBox<>(
                FXCollections.observableArrayList(Move.values())
        );
        //Seleciona por padrão o primeiro elemento do ComboBox
        comboBoxHeader.getSelectionModel().selectFirst();

        // Cria botao de play
        btnPlay = new Button("Jogar");

        // Cria botão de resetar o game
        btnReset = new Button("Nova Partida");
        btnReset.setDisable(true);

        // Header da tela - adiciona todos os componentes a ele
        HBox header = new HBox(
                8,
                labelHeader,
                comboBoxHeader,
                btnPlay,
                btnReset
        );
        // Alinha os componentes do header ao centro e a esquerda
        header.setAlignment(Pos.CENTER_LEFT);


        // Campos com dados dos jogadores
        labelPlayer = new Label("Você: - ");
        labelCPU = new Label("CPU: - ");
        labelRes = new Label("Resultado: - ");

        // Informacoes da rodada atual
        HBox lastInfo = new HBox(
                20,
                labelPlayer,
                labelCPU,
                // Mudar Nome abaixo
                labelRes
        );

        labelStatusDaPartida = new Label("V: 0 | D: 0 | E: 0");
        progressoPartida = new ProgressBar(0);

        jogadasPossiveis = new HBox(
                card("PEDRA"),
                card("PAPEL"),
                card("TESOURA")
        );

        listView = new ListView<>(history);


        // Componente com todas as Hbox
        VBox root = new VBox(
                10,
                header,
                lastInfo,
                labelStatusDaPartida,
                progressoPartida,
                jogadasPossiveis,
                listView

        );

        playerStats = new PlayerStats();

        // -------- EVENTS -------- \\

        btnPlay.setOnAction(e -> playRound(comboBoxHeader.getValue()));
        btnReset.setOnAction(e -> resetGame());


        // Adiciona a vbox principal a Scene(tela padrão) e define o tamanho
        stage.setScene(new Scene(root, 800, 800));
        stage.setTitle("RPS Challenge");
        stage.show();
    }

    private VBox card(String nome){

        Label tipoJogada = new Label(nome);
        tipoJogada.setStyle("-fx-font-size:28;");

        qntdDeJogadas = new Label("0");

        VBox box = new VBox(tipoJogada, qntdDeJogadas);
        box.setStyle("-fx-padding:10; -fx-background-color:#f2f2f2; -fx-border-radius:9; -fx-background-radius:8;");


        return box;
    }


    private void playRound(Move jogadaAtual){

        labelPlayer.setText("Você - " + jogadaAtual.toString());

        Move jogadaCPU = cpuStratagy.proximaJogada(jogadaAtual, WINDOW_AI);
        labelCPU.setText("CPU - " + jogadaCPU);

        int resultado = jogadaAtual.versus(jogadaCPU);
        playerStats.register(resultado);

        labelRes.setText("Resultado: " + ((resultado > 0) ? "Vitoria": resultado == 0 ? "Empate": "Derrota"));

        labelStatusDaPartida.setText(playerStats.toString());

        progressoPartida.setProgress(((((playerStats.totalRounds() * 100.0) / MAX_ROUND) / 100)));

        Score score = new Score(
            LocalDate.now().toString(),
            playerStats.getWins(),
            playerStats.getDraws(),
            playerStats.getLosses(),
            playerStats.winRate(),
            WINDOW_AI
        );

        if (playerStats.totalRounds() == MAX_ROUND){
            btnPlay.setDisable(true);
            btnReset.setDisable(false);
            scoreDao.save(score);
            history.addAll(scoreDao.list());
        }
    }

    private void resetGame(){
        labelPlayer.setText("Você: - ");
        labelCPU.setText("CPU: - ");
        labelRes.setText("Resultado: - ");
        labelStatusDaPartida.setText("V: 0 | D: 0 | E: 0");
        progressoPartida.setProgress(0);
        history.clear();
        cpuStratagy.resetStrategy();
        playerStats = new PlayerStats();
        btnPlay.setDisable(false);
        btnReset.setDisable(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
