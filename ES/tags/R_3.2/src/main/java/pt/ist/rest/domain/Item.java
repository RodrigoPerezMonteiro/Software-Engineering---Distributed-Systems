package pt.ist.rest.domain;

public class Item extends Item_Base {
    
    public  Item() {
        super();
    }
    
    public Item(Prato p, int quantidade) {
        setPrato(p);
        set_quantidade(quantidade);
    }
    
    public Prato get_Prato() {
        return getPrato();
    }
    
    public int getQuantidade() {
        return get_quantidade();
    }
}
