package com.kania.kolkoikrzyzyk.demofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML private Button button0_0;
    @FXML private Button button0_1;
    @FXML private Button button0_2;
    @FXML private Button button1_0;
    @FXML private Button button1_1;
    @FXML private Button button1_2;
    @FXML private Button button2_0;
    @FXML private Button button2_1;
    @FXML private Button button2_2;

    @FXML private Label ruchLabel;
    @FXML private Label informacjaLabel;

    private boolean graWTrakcie = false;
    private char obecnyRuch = 'X';
    private Button[][] tablicaPrzyciski;
    private List<int[][]> mozliweZwyciestwa;

    public HelloController() {
        mozliweZwyciestwa = new ArrayList<>();

        // Zwyciestwa w wierszach.
        mozliweZwyciestwa.add(new int[][] {{0, 0}, {0, 1}, {0, 2}});
        mozliweZwyciestwa.add(new int[][] {{1, 0}, {1, 1}, {1, 2}});
        mozliweZwyciestwa.add(new int[][] {{2, 0}, {2, 1}, {2, 2}});

        // Zwyciestwa w kolumnach.
        mozliweZwyciestwa.add(new int[][] {{0, 0}, {1, 0}, {2, 0}});
        mozliweZwyciestwa.add(new int[][] {{0, 1}, {1, 1}, {2, 1}});
        mozliweZwyciestwa.add(new int[][] {{0, 2}, {1, 2}, {2, 2}});

        // Zwyciestwa po skosach.
        mozliweZwyciestwa.add(new int[][] {{0, 0}, {1, 1}, {2, 2}});
        mozliweZwyciestwa.add(new int[][] {{0, 2}, {1, 1}, {2, 0}});
    }

    @FXML
    public void initialize() {
        tablicaPrzyciski = new Button[][] {
                { button0_0, button0_1, button0_2 },
                { button1_0, button1_1, button1_2 },
                { button2_0, button2_1, button2_2 }
        };

        informacjaLabel.setText("");
        aktualizujInformacjeObecnyRuch();
    }

    @FXML
    private void nowaGraButton_Click(ActionEvent event) {
        wyczyscTabliceGry();
        graWTrakcie = true;
        obecnyRuch = 'X';
        aktualizujInformacjeObecnyRuch();
        informacjaLabel.setText("");
    }

    @FXML
    private void poleGryButton_Click(ActionEvent event) {
        if (!graWTrakcie) {
            return;
        }

        Button przycisk = (Button) event.getSource();
        przycisk.setText(String.valueOf(obecnyRuch));

        if (czyRemis()) {
            graWTrakcie = false;
            informacjaLabel.setText("Remis");
        } else {
            Character zwyciezkiZnak = sprawdzZwyciestwo();

            if (zwyciezkiZnak != null) {
                graWTrakcie = false;
                informacjaLabel.setText("Zwycięzca: " + zwyciezkiZnak);
            }
        }

        aktualizujInformacjeObecnyRuch();
    }

    private void aktualizujInformacjeObecnyRuch() {
        if (!graWTrakcie) {
            ruchLabel.setText("");
            return;
        }

        if (obecnyRuch == 'X') {
            obecnyRuch = 'O';
        } else {
            obecnyRuch = 'X';
        }

        ruchLabel.setText(String.valueOf(obecnyRuch));
    }

    private Character sprawdzZwyciestwo() {
        for (int[][] mozliweZwyciestwo : mozliweZwyciestwa) {
            if (czyPolaMajaTenSamNiepustyZnak(mozliweZwyciestwo)) {
                int x = mozliweZwyciestwo[0][0];
                int y = mozliweZwyciestwo[0][1];
                return tablicaPrzyciski[x][y].getText().charAt(0);
            }
        }

        return null;
    }

    private boolean czyRemis() {
        for (int wiersz = 0; wiersz < 3; wiersz++) {
            for (int kolumna = 0; kolumna < 3; kolumna++) {
                if (tablicaPrzyciski[wiersz][kolumna].getText() == null) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean czyPolaMajaTenSamNiepustyZnak(int[]... pola) {
        boolean teSamePola = true;
        String pierwszePole = null;

        for (int[] pole : pola) {
            int x = pole[0];
            int y = pole[1];
            String obecnePole = tablicaPrzyciski[x][y].getText();

            if (obecnePole == null) {
                teSamePola = false;
                break;
            }

            if (pierwszePole == null) {
                pierwszePole = obecnePole;
            } else if (!pierwszePole.equals(obecnePole)) {
                teSamePola = false;
                break;
            }
        }

        return teSamePola;
    }

    private void wyczyscTabliceGry() {
        for (Button[] wierszPrzyciskow : tablicaPrzyciski) {
            for (Button przycisk : wierszPrzyciskow) {
                przycisk.setText(null);
            }
        }
    }
}