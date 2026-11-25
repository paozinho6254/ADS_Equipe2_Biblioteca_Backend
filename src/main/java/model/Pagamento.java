package model;

import java.time.LocalDate;

import model.enums.ModalidadePagamento;

public class Pagamento {

    private int id;
    private double valorPago;
    private LocalDate dataPagamento;
    private ModalidadePagamento modalidade;

    public Pagamento(int id, double valorPago, LocalDate dataPagamento, ModalidadePagamento modalidade) {
        this.id = id;
        this.valorPago = valorPago;
        this.dataPagamento = dataPagamento;
        this.modalidade = modalidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public ModalidadePagamento getModalidade() {
        return modalidade;
    }

    public void setModalidade(ModalidadePagamento modalidade) {
        this.modalidade = modalidade;
    }

    @Override
    public String toString() {
        return "Pagamento [id=" + id + ", valorPago=" + valorPago + ", dataPagamento=" + dataPagamento
                + ", modalidade=" + modalidade + "]";
    }

}
