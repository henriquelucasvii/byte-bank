package br.com.bytebank.controllers;

import br.com.bytebank.model.Transacao;

import java.util.ArrayList;
import java.util.List;

public class TransacaoController {
    private List<Transacao> transacoes = new ArrayList<Transacao>();

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<Transacao> listarTodos() {
        return transacoes;
    }

    public Transacao listarPorId(int id) {
        for(Transacao transacao: transacoes) {
            if (transacao.getId() == id) {
                return transacao;
            }
        }
        return null;
    }

    public void atualizarTransacao(int id, Transacao transacao) {
        Transacao transacaoExistente = listarPorId(id);

        if (transacaoExistente != null) {
            transacaoExistente.setTipo(transacao.getTipo());
            transacaoExistente.setValor(transacao.getValor());
            transacaoExistente.setData(transacao.getData());
        } else {
            System.out.println("Transação não encontrada");
        }
    }

    public void excluirTransacao(int id) {
        Transacao transacaoExistente = listarPorId(id);

        if (transacaoExistente != null) {
            transacoes.remove(transacaoExistente);
        }
    }

}