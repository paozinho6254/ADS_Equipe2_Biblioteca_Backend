package model;

import java.time.LocalDate;
import java.util.Objects;
import model.enums.StatusReserva;

/**
 * Representa a reserva de um {@link Livro} solicitada por um {@link Aluno}.
 */
public class Reserva {

    private int id;
    private LocalDate dataReserva;
    private LocalDate dataExpiracao;
    private StatusReserva status;
    private Aluno aluno;
    private Livro livro;

    public Reserva() {
    }

    public Reserva(Aluno aluno, Livro livro, LocalDate dataReserva, LocalDate dataExpiracao) {
        this(0,
                dataReserva != null ? dataReserva : LocalDate.now(),
                dataExpiracao,
                StatusReserva.ATIVA,
                Objects.requireNonNull(aluno, "Aluno é obrigatório para a reserva."),
                Objects.requireNonNull(livro, "Livro é obrigatório para a reserva."));
    }

    public Reserva(int id, LocalDate dataReserva, LocalDate dataExpiracao,
                   StatusReserva status, Aluno aluno, Livro livro) {
        this.id = id;
        this.dataReserva = dataReserva;
        this.dataExpiracao = dataExpiracao;
        this.status = status != null ? status : StatusReserva.ATIVA;
        this.aluno = aluno;
        this.livro = livro;
    }

    /**
     * Verifica se a reserva já está expirada na data de referência informada.
     */
    public boolean isExpirada(LocalDate hoje) {
        if (this.status == StatusReserva.EXPIRADA) {
            return true;
        }
        if (this.status != StatusReserva.ATIVA || this.dataExpiracao == null) {
            return false;
        }
        LocalDate referencia = hoje != null ? hoje : LocalDate.now();
        return referencia.isAfter(this.dataExpiracao);
    }

    /**
     * Marca a reserva ativa como atendida, validando que não expirou na data base.
     */
    public void marcarComoAtendida(LocalDate dataBase) {
        if (this.status != StatusReserva.ATIVA) {
            throw new IllegalStateException("Apenas reservas com status ATIVA podem ser atendidas.");
        }
        LocalDate referencia = dataBase != null ? dataBase : LocalDate.now();
        if (isExpirada(referencia)) {
            throw new IllegalStateException("Reserva expirada não pode ser marcada como atendida.");
        }
        this.status = StatusReserva.ATENDIDA;
    }

    /**
     * Cancela uma reserva que ainda esteja ativa.
     */
    public void cancelar() {
        if (this.status != StatusReserva.ATIVA) {
            throw new IllegalStateException("Apenas reservas com status ATIVA podem ser canceladas.");
        }
        this.status = StatusReserva.CANCELADA;
    }

    /**
     * Marca uma reserva ativa como expirada.
     */
    public void expirar() {
        if (this.status != StatusReserva.ATIVA) {
            throw new IllegalStateException("Apenas reservas com status ATIVA podem expirar.");
        }
        this.status = StatusReserva.EXPIRADA;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public LocalDate getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDate dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public String toString() {
        return "Reserva [id=" + id + ", dataReserva=" + dataReserva
                + ", dataExpiracao=" + dataExpiracao + ", status=" + status + "]";
    }
}
