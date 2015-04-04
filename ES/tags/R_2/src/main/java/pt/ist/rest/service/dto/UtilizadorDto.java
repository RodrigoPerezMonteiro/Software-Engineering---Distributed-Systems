package pt.ist.rest.service.dto;

public class UtilizadorDto {
    private String _nome;
    private String _username;
    private String _password;
    private Integer _tipo;

    public UtilizadorDto(String _nome, String _username, String _password, Integer _tipo) {
        this._nome = _nome;
        this._username = _username;
        this._password = _password;
        this._tipo = _tipo;
    }

    public String get_nome() {
        return _nome;
    }

    public String get_username() {
        return _username;
    }

    public String get_password() {
        return _password;
    }

    public Integer get_tipo() {
        return _tipo;
    }
}
