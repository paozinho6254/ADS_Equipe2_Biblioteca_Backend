package model;

import model.enums.Perfil;

public abstract class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senhaHash;
    private Perfil perfil;

    public Usuario(){
        
    }

    public Usuario(int id, String nome, String email, String senhaHash, Perfil perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.perfil = perfil;
    }

    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

}
