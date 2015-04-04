package pt.ist.rest.domain;

// TODO: Auto-generated Javadoc
/**
 * The Class Prato.
 */
public class Prato extends Prato_Base {

    /**
     * Instantiates a new prato.
     */
    public Prato() {
        super();
    }
    
    /**
     * Instantiates a new prato.
     *
     * @param nome the nome
     * @param preco the preco
     * @param calorias the calorias
     * @param id the id
     * @param tipo the tipo
     */
    public Prato(String nome,String tipo, double preco, double calorias, int id) {
        set_nome(nome);
        set_preco(preco);
        set_calorias(calorias);
        set_id(id);
        setAlimento(new Alimento(tipo));
    }
    
    /**
     * Gets the nome.
     *
     * @return the nome
     */
    public String getNome(){
        return get_nome();
    }
    
    /**
     * Gets the tipo.
     *
     * @return the tipo
     */
    public String getTipo() {
        return this.getAlimento().get_descricao();
    }
    /**
     * Gets the preco.
     *
     * @return the preco
     */
    public Double getPreco() {
        if (get_preco() != null) {
            return get_preco();
        } else {
            return 0.0;
        }
    }
    
    /**
     * Gets the calorias.
     *
     * @return the calorias
     */
    public Double getCalorias(){
        if (get_preco() != null) {
            return get_calorias();
        } else {
            return 0.0;
        }
    }

    /**
     * Gets the classificacao.
     *
     * @return the classificacao
     */
    public int getClassificacao() {
            return getClienteSet().size();
    }
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return get_id();
    }
    
    /**
     * Prints the.
     *
     * @return the string
     */
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Prato: " + getNome());
        stringBuilder.append("Tipo: " + getTipo());
        stringBuilder.append("\tcalorias = " + getCalorias());
        stringBuilder.append("\tpreco = " + getPreco());
        stringBuilder.append("\tclassificacao = " + getClassificacao() + "\n");
        return stringBuilder.toString();
    }

}
