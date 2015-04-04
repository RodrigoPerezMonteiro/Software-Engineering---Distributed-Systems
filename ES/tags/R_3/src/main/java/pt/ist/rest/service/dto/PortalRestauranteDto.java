package pt.ist.rest.service.dto;

import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class PortalRestauranteDto.
 */
public class PortalRestauranteDto {
	

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