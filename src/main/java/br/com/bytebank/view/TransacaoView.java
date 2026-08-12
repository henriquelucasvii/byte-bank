package br.com.bytebank.view;

import br.com.bytebank.controllers.TransacaoController;
import br.com.bytebank.model.Transacao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

public class TransacaoView extends JFrame {
    private final TransacaoController controller = new TransacaoController();
    private int id = 0;

    public TransacaoView() {
        setTitle("Byte Bank");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Valor");
        JTextField textField = new JTextField(10);
        JButton button = new JButton("Salvar");

        panel.add(label);
        panel.add(textField);
        panel.add(button);
        add(panel, BorderLayout.NORTH);

        String[] colunas = {"ID", "Valor", "Tipo", "Data"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        JTable tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);

        add(scrollPane, BorderLayout.CENTER);

        button.addActionListener((args) -> {
            double valor = Double.parseDouble(textField.getText());
            Transacao novaTransacao = new Transacao(id++, valor, "PIX", LocalDateTime.now().toString());

           controller.adicionarTransacao(novaTransacao);
        });
    }
}