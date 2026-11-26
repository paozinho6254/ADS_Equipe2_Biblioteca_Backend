package model;

import model.enums.Perfil;

/**
 * Representa o operador bibliotecário responsável por acervo, empréstimos, devoluções e pagamentos.
 */
public class Bibliotecario extends Usuario {

    public Bibliotecario() {
        setPerfil(Perfil.BIBLIOTECARIO);
    }

    public Bibliotecario(int id, String nome, String email, String senhaHash) {
        this(id, nome, email, senhaHash, Perfil.BIBLIOTECARIO);
    }

    public Bibliotecario(int id, String nome, String email, String senhaHash, Perfil perfil) {
        super(id, nome, email, senhaHash, perfil != null ? perfil : Perfil.BIBLIOTECARIO);
    }
}
