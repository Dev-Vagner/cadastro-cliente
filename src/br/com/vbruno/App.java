package br.com.vbruno;

import br.com.vbruno.dao.ClienteMapDAO;
import br.com.vbruno.dao.ClienteSetDAO;
import br.com.vbruno.dao.IClienteDAO;
import br.com.vbruno.domain.Cliente;

import javax.swing.*;

public class App {

    private static IClienteDAO iClienteDAO;

    public static void main(String[] args) {
        iClienteDAO = new ClienteMapDAO();

        String opcao = opcoesIniciais();

        while(isOpcaoValida(opcao)) {
            if(isOpcaoCadastrar(opcao)) {
                String dados = JOptionPane.showInputDialog(null, "Digite os " +
                                "dados dos clientes separados por vírgulas, " +
                                "conforme o exemplo: Nome, CPF, telefone, Endereço, " +
                                "Número, Cidade, Estado", "Cadastrar",
                        JOptionPane.INFORMATION_MESSAGE);
                if(isOpcaoSair(dados)) {
                    System.exit(0);
                }

                cadastrar(dados);
                opcao = opcoesIniciais();

            } else if (isOpcaoConsultar(opcao)){
                String cpf = JOptionPane.showInputDialog(null, "Digite o " +
                                "CPF do cliente que deseja consultar", "Consultar",
                        JOptionPane.INFORMATION_MESSAGE);
                if(isOpcaoSair(cpf)) {
                    System.exit(0);
                }

                consultar(cpf);
                opcao = opcoesIniciais();

            } else if (isOpcaoExcluir(opcao)) {
                String cpf = JOptionPane.showInputDialog(null, "Digite o " +
                                "CPF do cliente que deseja excluir", "Excluir",
                        JOptionPane.INFORMATION_MESSAGE);
                if(isOpcaoSair(cpf)) {
                    System.exit(0);
                }

                excluir(cpf);
                opcao = opcoesIniciais();

            } else if (isOpcaoEditar(opcao)) {
                String dados = JOptionPane.showInputDialog(null, "Digite os " +
                                "dados dos clientes separados por vírgulas, " +
                                "conforme o exemplo: Nome, CPF, telefone, Endereço, " +
                                "Número, Cidade, Estado", "Editar",
                        JOptionPane.INFORMATION_MESSAGE);
                if(isOpcaoSair(dados)) {
                    System.exit(0);
                }

                editar(dados);
                opcao = opcoesIniciais();
            }
        }
    }

    private static String opcoesIniciais() {
        String opcao = JOptionPane.showInputDialog(null, "Digite 1 para cadastro," +
                        " 2 para consultar, 3 para exclusão ou 4 para alteração",
                "Opção", JOptionPane.INFORMATION_MESSAGE);

        while(!isOpcaoValida(opcao)) {
            if(isOpcaoSair(opcao)) {
                System.exit(0);
            }
            opcao = JOptionPane.showInputDialog(null, "Opção inválida! " +
                            "Digite 1 para cadastro," + " 2 para consultar, 3 para exclusão, " +
                            "4 para alteração ou 5 para sair.",
                    "Opção inválida", JOptionPane.INFORMATION_MESSAGE);
        }

        return opcao;
    }

    private static boolean isOpcaoValida(String opcao) {
        if ("1".equals(opcao) || "2".equals(opcao) || "3".equals(opcao) || "4".equals(opcao)) {
            return true;
        }
        return false;
    }

    private static boolean isOpcaoCadastrar(String opcao) {
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

    private static boolean isOpcaoExcluir(String opcao) {
        if ("3".equals(opcao)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isOpcaoEditar(String opcao) {
        if ("4".equals(opcao)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isOpcaoSair(String opcao) {
        if (opcao == null) {
            return true;
        } else {
            return false;
        }
    }

    private static void cadastrar(String dados) {
        while(!isDadosValidos(dados)) {
            dados = JOptionPane.showInputDialog(null,
                    "Dados inválidos. Digite os dados dos clientes separados por " +
                            "vírgulas, conforme o exemplo: Nome, CPF, telefone, " +
                            "Endereço, Número, Cidade, Estado",
                    "Cadastrar novamente", JOptionPane.INFORMATION_MESSAGE);
            if(isOpcaoSair(dados)) {
                System.exit(0);
            }
        }
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
                    "CPF já cadastrado!", "Erro no cadastro",
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

    private static void excluir (String cpf) {
        if(isApenasNumeros(cpf)){
            Long cpfLong = Long.parseLong(cpf);
            Boolean isExcluido = iClienteDAO.excluir(cpfLong);
            if(isExcluido) {
                JOptionPane.showMessageDialog(null,
                        "Cliente exclúido!", "Exclusão realizada",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "CPF não encontrado!", "Erro na exclusão",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "O CPF digitado é inválido!",
                    "CPF inválido!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void editar (String dados) {
        while(!isDadosValidos(dados)) {
            dados = JOptionPane.showInputDialog(null,
                    "Dados inválidos. Digite os dados dos clientes separados por " +
                            "vírgulas, conforme o exemplo: Nome, CPF, telefone, " +
                            "Endereço, Número, Cidade, Estado",
                    "Cadastrar novamente", JOptionPane.INFORMATION_MESSAGE);
            if(isOpcaoSair(dados)) {
                System.exit(0);
            }
        }
        String[] dadosSeparados = dados.split(",");

        Cliente cliente = new Cliente(dadosSeparados[0], dadosSeparados[1], dadosSeparados[2],
                dadosSeparados[3], dadosSeparados[4], dadosSeparados[5], dadosSeparados[6]);
        Boolean isEditado = iClienteDAO.alterar(cliente);

        if(isEditado) {
            JOptionPane.showMessageDialog(null,
                    "Edição realizada com sucesso!", "Edição realizada",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Cliente não encontrado", "Erro na edição",
                    JOptionPane.INFORMATION_MESSAGE);
        }
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
