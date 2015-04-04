package pt.ist.rest.domain;

// TODO: Auto-generated Javadoc
/**
 * The Class Alimento.
 */
public class Alimento extends Alimento_Base {
	
	/**
	 * Instantiates a new alimento.
	 */
	public  Alimento() {
        super();
    }
    
    /**
     * Instantiates a new alimento.
     *
     * @param descricao the descricao
     */
    public Alimento(String descricao){
    	set_descricao(descricao);
    }
}
