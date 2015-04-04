package pt.ist.rest.service.dto;

public class PratoDto {

    private String nome;
    private double preco;
    private double calorias;
    private int classificacao;
    private int id;
    private String restaurante;

    public PratoDto(String nome, double preco, double calorias, int classificacao, int id) {
        this.nome = nome;
        this.preco = preco;
        this.calorias = calorias;
        this.classificacao = classificacao;
        this.id = id;
    }

    public PratoDto(String nome, double preco, double calorias, int classificacao) {
        this.nome = nome;
        this.preco = preco;
        this.calorias = calorias;
        this.classificacao = classificacao;
    }


    public PratoDto(String nome,
                    double preco,
                    double calorias,
                    int classificacao,
                    String restaurante) {
        this.nome = nome;
        this.preco = preco;
        this.calorias = calorias;
        this.classificacao = classificacao;
        this.restaurante = restaurante;
    }

    public String getNome() {
        return this.nome;
    }

    public double getPreco() {
        return this.preco;
    }

    public double getCalorias() {
        return this.calorias;
    }

    public int getClassificacao() {
        return this.classificacao;
    }

    public int getId() {
        return this.id;
    }

    public String getRestaurante() {
        return this.restaurante;
    }
}
