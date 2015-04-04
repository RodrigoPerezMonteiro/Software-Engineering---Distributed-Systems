package pt.ist.rest.service.dto;

import java.util.List;

public class ChequeDto {
	
	private List<String> checks;

	public ChequeDto (List<String> checks, String username){
		this.checks = checks;
	}
	
	
	public List<String> getChequeList(){
		return checks;
	}
	
}
