package pt.ist.rest.service.dto;

import java.io.Serializable;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class PortalRestauranteDto.
 */
public class PortalRestauranteDto implements Serializable {
	

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private List<RestauranteDto> restauranteList;
	private List<UtilizadorDto> usersList;
	

	protected PortalRestauranteDto() {
    }

    public PortalRestauranteDto(List<RestauranteDto> restauranteList, List<UtilizadorDto> usersList){
		this.restauranteList = restauranteList;
        this.usersList = usersList;
    }

	public List<RestauranteDto> getRestaurantes(){
		return this.restauranteList;
	}

    public List<UtilizadorDto> getUsersList() {
        return usersList;
    }

  

}