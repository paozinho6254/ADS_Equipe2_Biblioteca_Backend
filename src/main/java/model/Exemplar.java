package model;

import java.util.Objects;
import model.enums.StatusExemplar;

/**
 * Representa uma cópia física específica de um {@link Livro}, identificada por código de barras único.
 */
public class Exemplar {

    private String codigoBarras;
    private StatusExemplar status;
    private Livro livro;

    public Exemplar() {
        this.status = StatusExemplar.DISPONIVEL;
    }

    public Exemplar(String codigoBarras, Livro livro) {
        this(codigoBarras, livro, StatusExemplar.DISPONIVEL);
    }

    public Exemplar(String codigoBarras, Livro livro, StatusExemplar status) {
        this.codigoBarras = codigoBarras;
        this.livro = livro;
        this.status = status != null ? status : StatusExemplar.DISPONIVEL;
    }

    /**
     * Atualiza o status físico do exemplar garantindo valor válido.
     */
    public void alterarStatus(StatusExemplar novoStatus) {
        this.status = Objects.requireNonNull(novoStatus, "O novo status do exemplar não pode ser nulo.");
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public StatusExemplar getStatus() {
        return status;
    }

    public void setStatus(StatusExemplar status) {
        this.status = status;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public String toString() {
        return "Exemplar [codigoBarras=" + codigoBarras + ", status=" + status + ", livro=" + livro + "]";
    }
}
