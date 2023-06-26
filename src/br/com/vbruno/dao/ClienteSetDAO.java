package br.com.vbruno.dao;

import br.com.vbruno.domain.Cliente;

import java.util.*;

public class ClienteSetDAO implements IClienteDAO{

    private Set<Cliente> set;

    public ClienteSetDAO() {
        this.set = new HashSet<>();
    }

    @Override
    public Boolean cadastrar(Cliente cliente) {
        return this.set.add(cliente);
    }

    @Override
    public Boolean excluir(Long cpf) {
        for(Cliente cliente : this.set) {
            if(cliente.getCpf().equals(cpf)) {
                this.set.remove(cliente);
                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean alterar(Cliente cliente) {
        if(this.set.contains(cliente)) {
            for(Cliente clienteSet : this.set) {
                if(clienteSet.equals(cliente)) {
                    clienteSet.setNome(cliente.getNome());
                    clienteSet.setCpf(cliente.getCpf());
                    clienteSet.setTelefone(cliente.getTelefone());
                    clienteSet.setEndereco(cliente.getEndereco());
                    clienteSet.setNumero(cliente.getNumero());
                    clienteSet.setCidade(cliente.getCidade());
                    clienteSet.setEstado(cliente.getEstado());
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Cliente consultar(Long cpf) {
        for (Cliente clienteSet : this.set) {
            if(clienteSet.getCpf().equals(cpf)) {
                return clienteSet;
            }
        }
        return null;
    }

    @Override
    public Collection<Cliente> buscarTodos() {
        return this.set;
    }
}
