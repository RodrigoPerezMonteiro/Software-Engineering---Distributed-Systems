package pt.ist.rest.service.dto;

import java.io.Serializable;
import java.util.List;

public class ChequeDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> checks;

	protected ChequeDto(){}
	
	public ChequeDto (List<String> checks, String username){
		this.checks = checks;
	}
	
	
	public List<String> getChequeList(){
		return checks;
	}
	
}
