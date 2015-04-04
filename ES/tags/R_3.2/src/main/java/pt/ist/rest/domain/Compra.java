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
     * Gets the lista itens.
     * 
     * @return the lista itens
     */
    public Set<Item> getListaItens() {
        return getItemSet();
    }


    /**
     * Checks for prato.
     *
     * @param p the p
     * @return true, if successful
     */
    public boolean hasPrato(Prato p) {
        for (Item i : getListaItens()) {
            if (i.get_Prato().getId() == p.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adiciona prato.
     *
     * @param p the p
     * @param quantidade the quantidade
     */
    public void adicionaPrato(Prato p, int quantidade, boolean alterar) {
        if (hasPrato(p)) {
            adicionaPratoEmItemExistente(p, quantidade, alterar);
        } else {
            adicionaPratoEmNovoItem(p, quantidade);
        }
    }


    /**
     * Adiciona prato em item existente.
     *
     * @param p the p
     * @param quantidade the quantidade
     */
    private void adicionaPratoEmItemExistente(Prato p, int quantidade, boolean alterar) {
        for (Item i : getListaItens()) {
            if (i.get_Prato().getId() == p.getId()) {
                int quantidadeActual = i.getQuantidade();
                if(!alterar){
                    i.set_quantidade(i.getQuantidade() + quantidade);
                }
                else{
                    i.set_quantidade(quantidade);
                }
                if (i.getQuantidade() < 1) {
                    decTotal(p.getPreco() * quantidadeActual);
                    i.removePrato();
                    this.removeItem(i);
                    System.out.println("REMOVI O PRATO");
                } else {
                    if (quantidade < 0) {
                        decTotal(p.getPreco() * quantidade);
                    }else {
                        incTotal(p.getPreco() * quantidade);
                    }
                }
            }
        }
    }

    /**
     * Adiciona prato em novo item.
     *
     * @param p the p
     * @param quantidade the quantidade
     * @throws UnknownError the unknown error
     */
    private void adicionaPratoEmNovoItem(Prato p, int quantidade) throws UnknownError {
        if (quantidade>0) {
            Item newItem = new Item(p, quantidade);
            addItem(newItem);
            incTotal(p.getPreco());
        }
        else{
            throw new UnknownError(); //TODO: DEFINIR EXCEPCAO 
        }
    }
    
    public Item getItemOfPrato(String nomePrato) {
        for (Item i : getItemSet()) {
            if(i.getPrato().getNome().equals(nomePrato)){
                return i;
            }
        }
        return null;
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
        if(get_total() == null){
            set_total(preco);
        }
        else{
        set_total(get_total() + preco);
        }
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
        getItemSet().clear();
    }

    /**
     * Gets the total.
     *
     * @return the total
     */
    public double getTotal() {
        return get_total();
    }
}