package br.com.vbruno;

import br.com.vbruno.dao.ClienteMapDAO;
import br.com.vbruno.dao.IClienteDAO;
import br.com.vbruno.domain.Cliente;

import javax.swing.*;

public class App {

    private static IClienteDAO iClienteDAO;

    public static void main(String[] args) {
        iClienteDAO = new ClienteMapDAO();

        String opcao = opcoesIniciais();

        while(isOpcaoValida(opcao)) {
            if(isOpcaoCadastro(opcao)) {
                String dados = JOptionPane.showInputDialog(null, "Digite os " +
                                "dados dos clientes separados por vírgulas, " +
                                "conforme o exemplo: Nome, CPF, telefone, Endereço, " +
                                "Número, Cidade, Estado", "Cadastrar",
                        JOptionPane.INFORMATION_MESSAGE);
                while(!isDadosValidos(dados)) {
                    dados = JOptionPane.showInputDialog(null,
                            "Dados inválidos. Digite os dados dos clientes separados por " +
                                    "vírgulas, conforme o exemplo: Nome, CPF, telefone, " +
                                    "Endereço, Número, Cidade, Estado",
                            "Cadastrar novamente", JOptionPane.INFORMATION_MESSAGE);
                }
                cadastrar(dados);
                opcao = opcoesIniciais();

            } else if (isOpcaoConsultar(opcao)){
                String cpf = JOptionPane.showInputDialog(null, "Digite o " +
                                "CPF do cliente que deseja consultar", "Consultar",
                        JOptionPane.INFORMATION_MESSAGE);
                consultar(cpf);
                opcao = opcoesIniciais();

            } else if(isOpcaoSair(opcao)) {
                sair();
            }
        }
    }

    private static String opcoesIniciais() {
        String opcao = JOptionPane.showInputDialog(null, "Digite 1 para cadastro," +
                        " 2 para consultar, 3 para exclusão, 4 para alteração ou 5 para sair.",
                "Opção", JOptionPane.INFORMATION_MESSAGE);

        while(!isOpcaoValida(opcao)) {
            opcao = JOptionPane.showInputDialog(null, "Opção inválida! " +
                            "Digite 1 para cadastro," + " 2 para consultar, 3 para exclusão, " +
                            "4 para alteração ou 5 para sair.",
                    "Opção inválida", JOptionPane.INFORMATION_MESSAGE);
        }

        return opcao;
    }

    private static boolean isOpcaoCadastro(String opcao) {
        if ("1".equals(opcao)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isOpcaoConsultar(String opcao) {
        if ("2".equals(opcao)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isOpcaoSair(String opcao) {
        if ("5".equals(opcao)) {
            return true;
        } else {
            return false;
        }
    }

    private static void cadastrar(String dados) {
        String[] dadosSeparados = dados.split(",");
        Cliente cliente = new Cliente(dadosSeparados[0], dadosSeparados[1], dadosSeparados[2],
                dadosSeparados[3], dadosSeparados[4], dadosSeparados[5], dadosSeparados[6]);
        Boolean isCadastrado = iClienteDAO.cadastrar(cliente);
        if(isCadastrado) {
            JOptionPane.showMessageDialog(null,
                    "Cadastro realizado com sucesso!", "Cadastro realizado",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Ocorreu um erro interno no cadastro do cliente!", "Erro no cadastro",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void consultar(String cpf) {
        if(isApenasNumeros(cpf)) {
            Cliente cliente = iClienteDAO.consultar(Long.parseLong(cpf));
            if(cliente != null){
                JOptionPane.showMessageDialog(null,
                        "Os dados do cliente são: " + cliente.toString(),
                        "Mostrar dados do cliente", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Cliente não encontrado!",
                        "Cliente não encontrado", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "O CPF digitado é inválido!",
                    "CPF inválido!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void sair() {
        JOptionPane.showMessageDialog(null, "Até mais!", "Sair",
                JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static boolean isOpcaoValida(String opcao) {
        if ("1".equals(opcao) || "2".equals(opcao) || "3".equals(opcao) || "4".equals(opcao)
                || "5".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static Boolean isDadosValidos(String dados) {
        int contadorDados = 0;
        Boolean result = true;

        String[] dadosSeparados = dados.split(",");

        for(String dado : dadosSeparados){
            String dadoLimpo = dado.trim();
            contadorDados ++;

            if(contadorDados == 2 || contadorDados == 3 || contadorDados == 5){
                if(!isApenasNumeros(dadoLimpo)) {
                    result = false;
                    break;
                }
            } else if (contadorDados == 1 || contadorDados == 4 || contadorDados == 6 || contadorDados == 7) {
                if(!isApenasLetras(dadoLimpo)) {
                    result = false;
                    break;
                }
            }
        }

        if(contadorDados != 7) {
            result = false;
        }

        return result;
    }

    private static Boolean isApenasNumeros(String str){
        return str != null && str.matches("^\\d+$");
    }

    private static Boolean isApenasLetras(String str){
        return str != null && str.matches("[a-zA-Z\\s]+");
    }
}
