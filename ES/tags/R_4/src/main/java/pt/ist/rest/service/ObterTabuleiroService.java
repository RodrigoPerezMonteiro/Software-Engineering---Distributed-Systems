
package pt.ist.rest.service;

import java.util.ArrayList;
import java.util.List;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.domain.Cliente;
import pt.ist.rest.domain.Compra;
import pt.ist.rest.domain.Item;
import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.service.dto.ItemDto;
import pt.ist.rest.service.dto.PratoDto;

// TODO: Auto-generated Javadoc
/**
 * The Class ObterTabuleiroService.
 */
public class ObterTabuleiroService extends PortalRestauranteService{
	
	/**
	 * The item list.
	 */
	List<ItemDto> itemList = new ArrayList<ItemDto>();
	
	/**
	 * The username.
	 */
	private String username;
	
	/**
	 * The custo actual tabuleiro.
	 */
	private double custoActualTabuleiro;
	
	/**
	 * The saldo actual cliente.
	 */
	private double saldoActualCliente;
	
	/**
	 * Instantiates a new obter tabuleiro service.
	 *
	 * @param username the username
	 */
	public ObterTabuleiroService(String username){
		this.username = username;
	}
	

    /* (non-Javadoc)
     * @see pt.ist.rest.service.PortalRestauranteService#dispatch()
     */
    public final void dispatch (){
		PortalRestaurante portal = FenixFramework.getRoot();
		Cliente cl = portal.getClienteByUsername(username);
		this.saldoActualCliente = cl.getSaldo();
		Compra c = cl.getTabuleiro();
		this.custoActualTabuleiro = c.getTotal();
		ItemDto itemDTO;
		PratoDto pratoDTO;
		
		for (Item i : c.getListaItens()){
			pratoDTO = new PratoDto(i.get_Prato().getNome(),i.getPrato().getPreco(), i.get_Prato().getCalorias(), i.get_Prato().getClassificacao(), i.get_Prato().getId(), i.getPrato().getRestaurante().getNome());
			itemDTO = new ItemDto(pratoDTO, i.getQuantidade());
			itemList.add(itemDTO);
		}
		
	}
	
    /**
     * Gets the saldo actual cliente.
     *
     * @return the saldo actual cliente
     */
    public double getSaldoActualCliente() {
        return saldoActualCliente;
    }
	
	/**
	 * Gets the custo actual tabuleiro.
	 *
	 * @return the custo actual tabuleiro
	 */
	public double getCustoActualTabuleiro() {
        return custoActualTabuleiro;
    }

    /**
     * Gets the tabuleiro.
     *
     * @return the tabuleiro
     */
    public List<ItemDto> getTabuleiro(){
		return this.itemList;
	}


}