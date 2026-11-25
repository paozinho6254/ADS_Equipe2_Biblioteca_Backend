package model;

import model.enums.Perfil;

public class Administrador extends Usuario {

    public Administrador(int id, String nome, String email, String senhaHash, Perfil perfil) {
        super(id, nome, email, senhaHash, perfil);
    }

    public void gerarRelatorioAtrasos() {

    }

}