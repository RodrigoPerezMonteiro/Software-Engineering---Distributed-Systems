package pt.ist.rest.service.dto;

public class CompraDto {
	
	private int id;
	private boolean registada;

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
