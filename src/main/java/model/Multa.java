package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import model.enums.ModalidadePagamento;
import model.enums.StatusMulta;

/**
 * Representa uma penalidade financeira por devolução em atraso, calculada com taxa fixa diária.
 */
public class Multa {

    public static final BigDecimal TAXA_DIARIA = new BigDecimal("2.00");

    private int id;
    private int diasAtraso;
    private BigDecimal valor;
    private StatusMulta status;
    private Emprestimo emprestimo;

    public Multa() {
        this.status = StatusMulta.PENDENTE;
    }

    public Multa(int diasAtraso) {
        this.diasAtraso = Math.max(0, diasAtraso);
        this.valor = calcularValor();
        this.status = StatusMulta.PENDENTE;
    }

    public Multa(int id, int diasAtraso, BigDecimal valor, StatusMulta status) {
        this(id, null, diasAtraso, valor, status);
    }

    public Multa(int id, Emprestimo emprestimo, int diasAtraso, BigDecimal valor, StatusMulta status) {
        this.id = id;
        this.emprestimo = emprestimo;
        this.diasAtraso = Math.max(0, diasAtraso);
        this.valor = valor != null ? valor.setScale(2, RoundingMode.HALF_UP) : calcularValor();
        this.status = status != null ? status : StatusMulta.PENDENTE;
    }

    /**
     * Calcula o valor da multa multiplicando {@code diasAtraso} pela {@link #TAXA_DIARIA}.
     */
    public BigDecimal calcularValor() {
        if (this.diasAtraso <= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return TAXA_DIARIA.multiply(BigDecimal.valueOf(this.diasAtraso))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Marca a multa como paga caso ainda não esteja quitada.
     */
    public void marcarComoPaga() {
        if (this.status == StatusMulta.PAGA) {
            throw new IllegalStateException("A multa já foi marcada como paga.");
        }
        this.status = StatusMulta.PAGA;
    }

    /**
     * Registra o pagamento integral da multa e retorna a entidade {@link Pagamento} gerada.
     */
    public Pagamento registrarPagamento(BigDecimal valorPago, ModalidadePagamento modalidade) {
        if (this.status != StatusMulta.PENDENTE) {
            throw new IllegalStateException("Apenas multas pendentes podem receber pagamento.");
        }
        if (modalidade == null) {
            throw new IllegalArgumentException("A modalidade de pagamento é obrigatória.");
        }
        if (valorPago == null || valorPago.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor pago deve ser maior que zero.");
        }

        BigDecimal valorDevido = this.valor != null ? this.valor : calcularValor();
        if (valorPago.compareTo(valorDevido) < 0) {
            throw new IllegalArgumentException(
                    "Valor pago (" + valorPago + ") é inferior ao valor da multa (" + valorDevido + ").");
        }

        marcarComoPaga();
        return new Pagamento(0, valorPago.setScale(2, RoundingMode.HALF_UP), LocalDate.now(), modalidade);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(int diasAtraso) {
        this.diasAtraso = diasAtraso;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public StatusMulta getStatus() {
        return status;
    }

    public void setStatus(StatusMulta status) {
        this.status = status;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    @Override
    public String toString() {
        return "Multa [id=" + id + ", diasAtraso=" + diasAtraso + ", valor=" + valor + ", status=" + status + "]";
    }
}
