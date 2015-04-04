package pt.ist.rest.service.dto;

import java.io.Serializable;

public class RestauranteDto implements Serializable {
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String nome;
	private String morada;
	private double classificacao;
	
	public RestauranteDto(){}
	
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
