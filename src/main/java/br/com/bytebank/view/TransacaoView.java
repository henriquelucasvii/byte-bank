package br.com.bytebank.view;

import br.com.bytebank.controllers.TransacaoController;

import javax.swing.*;

public class TransacaoView extends JFrame {
    private TransacaoController controller = new TransacaoController();

    public TransacaoView() {
        setTitle("Byte Bank");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
