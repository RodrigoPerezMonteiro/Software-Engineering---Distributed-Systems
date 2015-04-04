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
     * @param classificacao the classificacao
     * @param id the id
     */
    public Prato(String nome, Double preco, Double calorias, Integer classificacao, Integer id) {
        set_nome(nome);
        set_preco(preco);
        set_calorias(calorias);
        set_classificacao(classificacao);
        set_id(id);
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
        if (get_classificacao() != null) {
            return get_classificacao();
        } else {
            return 0;
        }
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
     * Adds the classificacao.
     */
    public void addClassificacao() {
        set_classificacao(getClassificacao() + 1);
    }

    /**
     * Removes the classificacao.
     */
    public void removeClassificacao() {
        set_classificacao(getClassificacao() - 1);
    }
    
    /**
     * Prints the.
     *
     * @return the string
     */
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Prato: \n");
        stringBuilder.append("\n\tdescrição = " + getNome());
        return stringBuilder.toString();
    }

}
