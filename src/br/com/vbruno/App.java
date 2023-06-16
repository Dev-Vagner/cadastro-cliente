package br.com.vbruno;

import br.com.vbruno.dao.ClienteMapDAO;
import br.com.vbruno.dao.IClienteDAO;

import javax.swing.*;

public class App {

    private static IClienteDAO iClienteDAO;

    public static void main(String[] args) {
        iClienteDAO = new ClienteMapDAO();

        String opcao = JOptionPane.showInputDialog(null, "Digite 1 para cadastro," +
                " 2 para consultar, 3 para exclusão, 4 para alteração ou 5 para sair.",
                "Cadastro", JOptionPane.INFORMATION_MESSAGE);
    }
}
