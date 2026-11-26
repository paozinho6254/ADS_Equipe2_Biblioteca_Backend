package model;

import model.enums.Perfil;

/**
 * Representa um aluno habilitado a consultar acervo, reservar livros e realizar empréstimos.
 */
public class Aluno extends Usuario {

    private String matricula;
    private String curso;
    private String endereco;

    public Aluno() {
        setPerfil(Perfil.ALUNO);
    }

    public Aluno(int id, String nome, String email, String senhaHash,
                 String matricula, String curso, String endereco) {
        this(id, nome, email, senhaHash, Perfil.ALUNO, matricula, curso, endereco);
    }

    public Aluno(int id, String nome, String email, String senhaHash, Perfil perfil,
                 String matricula, String curso, String endereco) {
        super(id, nome, email, senhaHash, perfil != null ? perfil : Perfil.ALUNO);
        this.matricula = matricula;
        this.curso = curso;
        this.endereco = endereco;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Aluno [id=" + getId() + ", nome=" + getNome() + ", matricula=" + matricula
                + ", curso=" + curso + ", endereco=" + endereco + "]";
    }
}
