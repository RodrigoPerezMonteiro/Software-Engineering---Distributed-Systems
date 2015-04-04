package pt.ist.rest.exceptions;

public class CompraNaoExisteException extends restException {
	
	private static final long serialVersionUID = 1L;
	
	private Integer _compraId;
	
	public CompraNaoExisteException() {
		
	}
	
	public CompraNaoExisteException(String msg, Integer compraId) {
        super(msg + ": Compra: " + compraId);
        _compraId = compraId;
    }
	
	public Integer getCompraId() {
        return this._compraId;
    }

}

