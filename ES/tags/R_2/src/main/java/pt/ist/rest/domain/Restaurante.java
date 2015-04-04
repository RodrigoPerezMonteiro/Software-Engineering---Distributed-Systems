package pt.ist.rest.domain;

import java.util.Set;

import pt.ist.rest.exceptions.RestauranteJaContemPratoException;
import pt.ist.rest.exceptions.RestauranteMaxPratosException;
import pt.ist.rest.exceptions.RestauranteSemRequisitosClassificacaoException;

// TODO: Auto-generated Javadoc
/**
 * The Class Restaurante.
 */
public class Restaurante extends
        Restaurante_Base {

    /**
     * Instantiates a new restaurante.
     */
    public Restaurante() {
        super();
    }

    /**
     * Instantiates a new restaurante.
     *
     * @param nome the nome
     * @param morada the morada
     */
    public Restaurante(String nome, String morada) {
        set_nome(nome);
        set_morada(morada);
    }

    /**
     * Gets the average classificacao.
     * 
     * @return the average classificacao
     */
    public Double getMediaClassificacao() throws RestauranteSemRequisitosClassificacaoException {
        double media = 0.0;
        int numPratos = getPratoSet().size();
        int acomulador = 0;
        int numPratosParaClass = 0;

        for (Prato p : getPratoSet()) {
            if (p.getClassificacao() > getPortalRestaurante().get_minClassificacaoPratos()) {
                numPratosParaClass++;
            }
            acomulador += p.getClassificacao();
        }
            if (numPratosParaClass < getPortalRestaurante().get_minPratosParaClassificarRest()) {
                throw new RestauranteSemRequisitosClassificacaoException("Restaurante não cumpre requisitos para calcular media", getNome());
            } else {
                media = (double)acomulador / (double) numPratos;
                return media;
            }
        
    }

    /**
     * Gets the nome.
     * 
     * @return the nome
     */
    public String getNome() {
        return get_nome();
    }

    /**
     * Gets the pratos.
     * 
     * @return the pratos
     */
    public Set<Prato> getPratos() {
        return getPratoSet();
    }


    /**
     * Adiciona prato.
     *
     * @param nomePrato the name of dish
     * @param precoPrato the price of dish
     * @param caloriasPrato the calories dish
     * @param classificacaoPrato the classification dish
     * @param idPrato the id of dish
     */
     
    public void adicionaPrato(String nomePrato,Double precoPrato,Double caloriasPrato,Integer idPrato) throws RestauranteMaxPratosException, RestauranteJaContemPratoException{
            if (getPratoSet().size() < getPortalRestaurante().get_maxPratosPorRestaurante()) {
                if (contemPrato(nomePrato)) {
                    throw new RestauranteJaContemPratoException("Prato já existente",getNome(),nomePrato);
                } else {
                    Prato p = new Prato(nomePrato, precoPrato, caloriasPrato,idPrato);
                    addPrato(p);
                }
            } else {
                throw new RestauranteMaxPratosException("O limite de pratos foi atingido no",getNome());
            }
    }
    
    /**
     * Contem prato.
     *
     * @param nomePrato the nome prato
     * @return true, if successful
     */
    public boolean contemPrato(String nomePrato){
        boolean temPrato = false;
        for (Prato p : getPratos()) {
            if (p.getNome().equals(nomePrato)) {
                temPrato = true;
            }
        }
        return temPrato;
    }

    /**
     * Removes the prato.
     * 
     * @param id the id
     * @return the int
     */
    public int removerPrato(Integer id) {
        for (Prato p : getPratoSet()) {
            if (p.getId() == id) {
                removePrato(p);
                return 0;
            }
        }
        throw new UnknownError(); // TODO: DEFINIR NOVA EXCEPCAO
    }
    
    /**
     * Gets the gestores.
     *
     * @return the gestores
     */
    public Set<GestorRestaurante> getGestores() {
        return getGestorRestauranteSet();
    }
    
    /**
     * Adiciona gestor.
     *
     * @param gestor the gestor
     */
    public void adicionaGestor(GestorRestaurante gestor){
        getGestorRestauranteSet().add(gestor);
    }
    
    /**
     * Gets the morada.
     *
     * @return the morada
     */
    public String getMorada() {
        return get_morada();
    }
    
    /**
     * Prints the Restaurante information.
     *
     * @return the string with information
     */
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nRestaurante: \n");
        stringBuilder.append("\n\tnome = " + getNome() + "\n\tmorada = " + getMorada());
        return stringBuilder.toString();
    }
}