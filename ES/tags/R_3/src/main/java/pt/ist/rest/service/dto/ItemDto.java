package pt.ist.rest.service.dto;

// TODO: Auto-generated Javadoc
/**
 * The Class ItemDto.
 */
public class ItemDto {


	private PratoDto pDTO;
    private Integer quantidade;

    /**
     * Instantiates a new item dto.
     *
     * @param quantidade the quantidade
     */
    public ItemDto(PratoDto pDTO, Integer quantidade) {
        this.pDTO = pDTO;
        this.quantidade = quantidade;
    }

    /**
     * Gets the quantidade.
     *
     * @return the quantidade
     */
    public Integer getQuantidade() {
        return quantidade;
    }
    
    public PratoDto getPratoDto(){
    	return pDTO;
    }
    
    
}
