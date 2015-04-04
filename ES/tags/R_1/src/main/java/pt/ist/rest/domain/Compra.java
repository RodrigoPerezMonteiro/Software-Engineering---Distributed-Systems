package pt.ist.rest.domain;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class Compra.
 */
public class Compra extends
        Compra_Base {
    /**
     * Instantiates a new compra.
     */
    public Compra() {
        super();
    }

    /**
     * Instantiates a new compra.
     * 
     * @param id the id
     */
    public Compra(Integer id) {
        set_id(id);
        set_registada(false);
    }

    /**
     * Checks if is registada.
     * 
     * @return true, if is registada
     */
    public boolean isRegistada() {
        return get_registada();
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public Integer getIdCompra() {
        return get_id();
    }

    /**
     * Gets the lista pratos.
     * 
     * @return the lista pratos
     */
    public Set<Prato> getListaPratos() {
        return getPratoSet();
    }

    /**
     * Adiciona prato.
     * 
     * @param p the p
     */
    public void adicionaPrato(Prato p, Integer quantidade) {
        while (quantidade > 0) {
            addPrato(p);
            incTotal(p.getPreco());
            --quantidade;
        }
    }

    /**
     * Rem prato.
     * 
     * @param p the p
     */
    public void remPrato(Prato p) {
        removePrato(p);
        decTotal(p.getPreco());
    }

    /**
     * Sets the registada.
     * 
     * @param status the new registada
     */
    public void setRegisto(boolean status) {
        if (status) {
            set_registada(status);
        }
    }

    /**
     * Inc total.
     * 
     * @param preco the preco
     */
    public void incTotal(Double preco) {
        set_total(get_total() + preco);
    }

    /**
     * Dec total.
     * 
     * @param preco the preco
     */
    public void decTotal(Double preco) {
        set_total(get_total() - preco);
    }

    /**
     * Limpa lista.
     */
    public void limpaLista() {
        getPratoSet().clear();
    }

}
