package model;

import model.enums.Perfil;

/**
 * Representa o administrador do sistema com acesso a relatórios gerenciais.
 */
public class Administrador extends Usuario {

    public Administrador() {
        setPerfil(Perfil.ADMINISTRADOR);
    }

    public Administrador(int id, String nome, String email, String senhaHash) {
        this(id, nome, email, senhaHash, Perfil.ADMINISTRADOR);
    }

    public Administrador(int id, String nome, String email, String senhaHash, Perfil perfil) {
        super(id, nome, email, senhaHash, perfil != null ? perfil : Perfil.ADMINISTRADOR);
    }
}
