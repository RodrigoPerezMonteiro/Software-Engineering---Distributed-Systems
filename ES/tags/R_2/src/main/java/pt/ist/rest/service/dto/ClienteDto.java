package pt.ist.rest.service.dto;

public class ClienteDto extends UtilizadorDto {
	
	
	private String morada;
	private String email;
	private double saldo;
	
	public ClienteDto(String nome, String username, String password, String morada, String email, double saldo){
		super(nome,username,password,0);
		this.morada = morada;
		this.email = email;
		this.saldo = saldo;
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
	
    public String getMorada() {
        return morada;
    }

    public String getEmail() {
        return email;
    }

    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
