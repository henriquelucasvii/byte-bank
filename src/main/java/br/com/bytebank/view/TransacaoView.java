package br.com.bytebank.view;

import br.com.bytebank.controllers.TransacaoController;
import br.com.bytebank.model.Transacao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDateTime;

public class TransacaoView extends JFrame {
    private TransacaoController controller = new TransacaoController();
    private String[] tipos = {"PIX", "Dinheiro"};

    public TransacaoView() {
        setTitle("Sistema de Transações ByteBank");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Paineis
        JPanel panelTopo = new JPanel(new BorderLayout());
        panelTopo.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        JPanel panelBotoes = new JPanel();

        // Campos
        JTextField campoId = new JTextField();
        JTextField campoValor = new JTextField();
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        MaskFormatter maskFormatter = null;

        try {
            maskFormatter = new MaskFormatter("##/##/####");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JFormattedTextField campoData = new JFormattedTextField(maskFormatter);

        criaFormulario(panelFormulario, campoId, campoValor, comboTipo, campoData);

        JButton botaoSalvar = new JButton("Salvar");
        JButton botaoBuscar = new JButton("Buscar (Id)");
        JButton botaoEditar = new JButton("Editar");
        JButton botaoDeletar = new JButton("Deletar");

        panelBotoes.add(botaoSalvar);
        panelBotoes.add(botaoBuscar);
        panelBotoes.add(botaoEditar);
        panelBotoes.add(botaoDeletar);

        panelTopo.add(panelFormulario, BorderLayout.CENTER);
        panelTopo.add(panelBotoes, BorderLayout.SOUTH);

        add(panelTopo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Valor", "Tipo", "Data"};

        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);

        JTable tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10,15,15,15));

        add(scrollPane, BorderLayout.CENTER);

        botaoSalvar.addActionListener((args) -> salvarDados(campoValor, campoId, modeloTabela, comboTipo));
        botaoBuscar.addActionListener((args) -> buscarId(campoId, campoValor, comboTipo));
        botaoEditar.addActionListener((args) -> editarDados(campoId, campoValor, modeloTabela, comboTipo));
        botaoDeletar.addActionListener((args) -> deletarDados(campoId, campoValor, modeloTabela));
    }

    private void atualizarTabela(DefaultTableModel modeloTabela) {
        modeloTabela.setRowCount(0);

        for (Transacao t: controller.listarTodos()) {
            modeloTabela.addRow(new Object[] {
                    t.getId(), t.getValor(), t.getTipo(), t.getData()
            });
        }
    }

    private void limparCampos(JTextField id, JTextField valor) {
        id.setText("");
        valor.setText("");
    }

    private void salvarDados(JTextField campoValor, JTextField campoId, DefaultTableModel modeloTabela, JComboBox<String> comboTipo) {
        if (campoValor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o campo VALOR para salvar!!");
            return;
        }

        try {
            int novoId = controller.listarTodos().size() + 1;
            double valor = Double.parseDouble(campoValor.getText());
            String tipo = (String) comboTipo.getSelectedItem();

            Transacao novaTransacao = new Transacao(novoId, valor, tipo, LocalDateTime.now().toString());

            controller.adicionarTransacao(novaTransacao);
            atualizarTabela(modeloTabela);
            limparCampos(campoId, campoValor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Não foi possível salvar a transação");
        }
    }

    private void buscarId(JTextField campoId, JTextField campoValor, JComboBox<String> comboTipo) {
        try {
            int idBusca = Integer.parseInt(campoId.getText());
            Transacao t = controller.listarPorId((idBusca));

            if (t !=  null) {
                campoValor.setText(String.valueOf(t.getValor()));
                comboTipo.setSelectedItem(t.getTipo());
            } else {
                JOptionPane.showMessageDialog(this, "Transação não encontrada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido.");
        }

    }

    private void editarDados(JTextField campoId, JTextField campoValor, DefaultTableModel modeloTabela, JComboBox<String> comboTipo) {
        try {
            int idBusca = Integer.parseInt(campoId.getText());
            double valor = Double.parseDouble(campoValor.getText());
            String tipo = (String) comboTipo.getSelectedItem();

            Transacao transacaoAtualizada = new Transacao(idBusca, valor, tipo, LocalDateTime.now().toString());

            controller.atualizarTransacao(idBusca, transacaoAtualizada);

            atualizarTabela(modeloTabela);
            limparCampos(campoId, campoValor);
            JOptionPane.showMessageDialog(this, "Transação atualizda com sucesso!!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao editar. Busque um ID válido primeiro");
        }
    }

    private void deletarDados(JTextField campoId, JTextField campoValor, DefaultTableModel modeloTabela) {
        try {
            int idBusca = Integer.parseInt(campoId.getText());

            Transacao t = controller.listarPorId(idBusca);
            if (t != null) {
                controller.excluirTransacao(idBusca);
            } else {
                JOptionPane.showMessageDialog(this, "ID não existe!");
            }

            atualizarTabela(modeloTabela);
            limparCampos(campoId, campoValor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Digite um ID válido para deletar");
        }
    }

    private void criaFormulario(JPanel panelFormulario, JTextField campoId, JTextField campoValor, JComboBox<String> comboTipo, JTextField campoData) {
        panelFormulario.add(new JLabel("ID (Para Buscar/deletar):"));
        panelFormulario.add(campoId);
        panelFormulario.add(new JLabel("Valor (R$):"));
        panelFormulario.add(campoValor);
        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(comboTipo);
        panelFormulario.add(new JLabel("Data:"));
        panelFormulario.add(campoData);
    }
}