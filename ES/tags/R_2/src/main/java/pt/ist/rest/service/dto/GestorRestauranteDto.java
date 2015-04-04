package pt.ist.rest.service.dto;

public class GestorRestauranteDto extends UtilizadorDto {
	
	
	public GestorRestauranteDto(String nome, String username, String password){
	    super(nome,password,username,1);
	} 
	
	public String getNome() {
        return super.get_nome();
    }
	
	public String getUsername() {
        return super.get_username();
    }
	
	public String getPassword() {
        return super.get_password();
    }
	
}
