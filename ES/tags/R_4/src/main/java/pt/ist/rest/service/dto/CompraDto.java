package pt.ist.rest.service.dto;

import java.io.Serializable;

public class CompraDto implements Serializable {
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int id;
	private boolean registada;

	public CompraDto() {
        
    }
	public CompraDto(int id){
		this.id = id;
		this.registada = false;
	}
	
	public int getId(){
		return this.id;
	}
	
	public boolean getRegistada(){
		return this.registada;
	}
	
}
