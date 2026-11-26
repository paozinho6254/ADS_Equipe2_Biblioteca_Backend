package model;

/**
 * Representa um título catalográfico do acervo identificado por ISBN único.
 */
public class Livro {

    private int id;
    private String titulo;
    private String isbn;
    private String autorPrincipal;
    private int anoPublicacao;

    public Livro() {
    }

    public Livro(int id, String titulo, String isbn, String autorPrincipal, int anoPublicacao) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.autorPrincipal = autorPrincipal;
        this.anoPublicacao = anoPublicacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAutorPrincipal() {
        return autorPrincipal;
    }

    public void setAutorPrincipal(String autorPrincipal) {
        this.autorPrincipal = autorPrincipal;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    @Override
    public String toString() {
        return "Livro [id=" + id + ", titulo=" + titulo + ", isbn=" + isbn
                + ", autorPrincipal=" + autorPrincipal + ", anoPublicacao=" + anoPublicacao + "]";
    }
}
