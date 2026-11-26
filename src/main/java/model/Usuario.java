package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Objects;
import model.enums.Perfil;

/**
 * Classe base abstrata que representa um usuário autenticado do sistema UniBiblio.
 */
public abstract class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senhaHash;
    private Perfil perfil;

    protected Usuario() {
    }

    protected Usuario(int id, String nome, String email, String senhaHash, Perfil perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.perfil = perfil;
    }

    /**
     * Valida a senha informada comparando com o {@code senhaHash} armazenado.
     */
    public boolean autenticar(String senha) {
        if (senha == null || senha.isBlank() || this.senhaHash == null || this.senhaHash.isBlank()) {
            return false;
        }
        return Objects.equals(this.senhaHash, senha)
                || Objects.equals(this.senhaHash, gerarHash(senha));
    }

    /**
     * Gera o resumo SHA-256 em hexadecimal para armazenamento seguro de senha.
     */
    public static String gerarHash(String senha) {
        Objects.requireNonNull(senha, "Senha não pode ser nula.");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Algoritmo SHA-256 indisponível na JVM.", e);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
