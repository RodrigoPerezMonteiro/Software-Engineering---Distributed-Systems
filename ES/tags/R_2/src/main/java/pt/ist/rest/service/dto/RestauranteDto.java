package pt.ist.rest.service.dto;

public class RestauranteDto {
	
	private String nome;
	private String morada;
	private double classificacao;
	
	public RestauranteDto(String nome, String morada, double classificacao){
		this.nome = nome;
		this.morada = morada;
		this.classificacao = classificacao;
		
	}
	public RestauranteDto(String nome, String morada){
        this.nome = nome;
        this.morada = morada;
        
    }
	
	public String getNome(){
		return this.nome;
	}
	
	public String getMorada(){
		return this.morada;
	}
	
	public double getClassificacao(){
		return this.classificacao;
	}
}
