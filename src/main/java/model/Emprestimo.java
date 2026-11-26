package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import model.enums.StatusEmprestimo;

/**
 * Representa um empréstimo de um {@link Exemplar} para um {@link Aluno},
 * encapsulando prazo de 7 dias corridos, verificação de atraso e devolução.
 */
public class Emprestimo {

    public static final int PRAZO_DIAS = 7;

    private int id;
    private LocalDate dataInicio;
    private LocalDate dataPrevista;
    private LocalDate dataDevolucao;
    private StatusEmprestimo status;
    private Aluno aluno;
    private Exemplar exemplar;

    public Emprestimo() {
    }

    public Emprestimo(Aluno aluno, Exemplar exemplar, LocalDate dataInicio) {
        this.aluno = Objects.requireNonNull(aluno, "Aluno é obrigatório para o empréstimo.");
        this.exemplar = Objects.requireNonNull(exemplar, "Exemplar é obrigatório para o empréstimo.");
        this.dataInicio = dataInicio != null ? dataInicio : LocalDate.now();
        this.dataPrevista = calcularDataPrevista();
        this.status = StatusEmprestimo.ATIVO;
    }

    public Emprestimo(int id, LocalDate dataInicio, LocalDate dataPrevista, LocalDate dataDevolucao,
                      StatusEmprestimo status, Aluno aluno, Exemplar exemplar) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataPrevista = dataPrevista;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
        this.aluno = aluno;
        this.exemplar = exemplar;
    }

    /**
     * Calcula a data prevista de devolução somando {@link #PRAZO_DIAS} dias corridos à {@code dataInicio}.
     */
    public LocalDate calcularDataPrevista() {
        if (this.dataInicio == null) {
            throw new IllegalStateException("A data de início do empréstimo não foi definida.");
        }
        return this.dataInicio.plusDays(PRAZO_DIAS);
    }

    /**
     * Indica se o empréstimo está ou foi encerrado em atraso em relação à data de referência.
     */
    public boolean isAtrasado(LocalDate hoje) {
        if (this.status == StatusEmprestimo.DEVOLVIDO_COM_ATRASO) {
            return true;
        }
        if (this.status == StatusEmprestimo.DEVOLVIDO_NO_PRAZO || this.dataPrevista == null) {
            return false;
        }
        if (this.dataDevolucao != null) {
            return this.dataDevolucao.isAfter(this.dataPrevista);
        }
        LocalDate referencia = hoje != null ? hoje : LocalDate.now();
        return referencia.isAfter(this.dataPrevista);
    }

    /**
     * Calcula os dias corridos de atraso até {@code dataRef} (ou até a devolução efetiva/hoje).
     */
    public int calcularDiasAtraso(LocalDate dataRef) {
        if (this.dataPrevista == null) {
            return 0;
        }
        LocalDate referencia = dataRef != null
                ? dataRef
                : (this.dataDevolucao != null ? this.dataDevolucao : LocalDate.now());
        if (!referencia.isAfter(this.dataPrevista)) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(this.dataPrevista, referencia);
    }

    /**
     * Registra a devolução do empréstimo e transiciona o status conforme RN09.
     */
    public void registrarDevolucao(LocalDate dataDevolucao) {
        if (this.status != StatusEmprestimo.ATIVO) {
            throw new IllegalStateException("Apenas empréstimos ativos podem receber devolução.");
        }
        LocalDate efetiva = dataDevolucao != null ? dataDevolucao : LocalDate.now();
        this.dataDevolucao = efetiva;
        if (this.dataPrevista != null && efetiva.isAfter(this.dataPrevista)) {
            this.status = StatusEmprestimo.DEVOLVIDO_COM_ATRASO;
        } else {
            this.status = StatusEmprestimo.DEVOLVIDO_NO_PRAZO;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    @Override
    public String toString() {
        return "Emprestimo [id=" + id + ", dataInicio=" + dataInicio + ", dataPrevista=" + dataPrevista
                + ", dataDevolucao=" + dataDevolucao + ", status=" + status + "]";
    }
}
