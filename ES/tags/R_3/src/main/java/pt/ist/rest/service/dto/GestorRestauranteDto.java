package pt.ist.rest.service.dto;

public class GestorRestauranteDto extends UtilizadorDto implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected GestorRestauranteDto(){
	    super();
	} 
	
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
