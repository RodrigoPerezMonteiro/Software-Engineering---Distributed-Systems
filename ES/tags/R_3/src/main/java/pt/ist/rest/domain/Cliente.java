package pt.ist.rest.domain;

import java.util.ArrayList;
import java.util.Set;

import pt.ist.rest.exceptions.ClienteJaGostaPratoException;
import pt.ist.rest.exceptions.ClienteMaxVotosException;
import pt.ist.rest.exceptions.CompraNaoExisteException;
import pt.ist.rest.exceptions.NaoExisteTabuleiroException;
import pt.ist.rest.exceptions.TabuleiroVazioException;

// TODO: Auto-generated Javadoc
/**
 * The Class Cliente.
 */
public class Cliente extends
        Cliente_Base {

    /**
     * The nif lenght.
     */
    private final int nifLenght = 9;

    /**
     * Instantiates a new cliente.
     */
    public Cliente() {
        super();
    }

    /**
     * Instantiates a new cliente.
     *
     * @param nome the nome
     * @param username the username
     * @param password the password
     * @param morada the morada
     * @param email the email
     * @param saldo the saldo
     */
    public Cliente(String nome,
                   String username,
                   String password,
                   String morada,
                   String email,
                   double saldo) {
        set_nome(nome);
        set_username(username);
        set_password(password);
        set_morada(morada);
        set_email(email);
        set_tipo(0);
        set_idCompra(0);
        set_saldo(saldo);
    }

    /**
     * Checks if is a valid nif.
     * 
     * @param nif the nif
     * @return true, if is valid a nif
     */
    public boolean isValidNif(int nif) {
        boolean valido = false;
        if (nif != 0 && (int) (Math.log10(nif) + 1) == nifLenght) {
            valido = true;
            return valido;
        }
        return valido;
    }

    /**
     * Adiciona o prato que gosta.
     * 
     * @param prato the prato
     */
    public void adicionaPratoQueGosta(Prato prato) throws ClienteMaxVotosException, ClienteJaGostaPratoException {
            if (getPratoSet().size() < getPortalRestaurante().get_maxVotosPorCliente()) {
                if (hasPrato(prato)) {
                    throw new ClienteJaGostaPratoException("Cliente já gosta do prato", getNome(),
                            prato.getNome());
                } else {
                    addPrato(prato);
                }
            } else {
                throw new ClienteMaxVotosException("Cliente atingiu maximo de Pratos que gosta",
                        getNome());
            }
    }

    /**
     * Remove o prato que gosta.
     * 
     * @param id the id
     */
    public void removePratoQueGosta(Integer id) {

        for (Prato p : getPratoSet()) {
            if (p.get_id() == id) {
                removePrato(p);
            }
        }
    }


    /**
     * Gets the ementa.
     * 
     * @param r the r
     * @return the ementa
     */
    public Set<Prato> getEmenta(Restaurante r) {
        return r.getPratos();
    }

    /**
     * Adiciona compra.
     *
     * @param c the c
     */
    public void adicionaCompra(Compra c) {
        getCompraSet().add(c);
    }

    /**
     * Nova compra.
     * 
     * @return the compra
     */
    public Compra novoTabuleiro() {
        Compra c = new Compra(generateCompraId());
        adicionaCompra(c);
        return c;
    }

    /**
     * Gets the compra by id.
     * 
     * @param id the id
     * @return the compra by id
     */
    public Compra getCompraById(Integer id) throws CompraNaoExisteException {
        for (Compra c : getCompraSet()) {
            if (c.getIdCompra() == id) {
                return c;
            }
        }
        throw new CompraNaoExisteException("Compra nao existe", id);
    }

    /**
     * Checks for tabuleiro.
     *
     * @return true, if successful
     */
    public boolean hasTabuleiro() {
        for (Compra c : getCompraSet()) {
            if (!c.get_registada()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the tabuleiro.
     * 
     * @return the tabuleiro
     */
    public Compra getTabuleiro() throws NaoExisteTabuleiroException {
        if (hasTabuleiro()) {
            for (Compra compra : getCompraSet()) {
                if (!compra.isRegistada()) {
                    return compra;
                }
            }
        } else {
            throw new NaoExisteTabuleiroException("Cliente nao contem tabuleiro", this.getNome());

        }
        return null;
    }

    /**
     * Gets the compras registadas.
     * 
     * @return the compras registadas
     */
    public ArrayList<Compra> getComprasRegistadas() {
        ArrayList<Compra> _comprasRegistadas = new ArrayList<Compra>();
        for (Compra compra : getCompraSet()) {
            if (compra.isRegistada()) {
                _comprasRegistadas.add(compra);
            }
        }
        return _comprasRegistadas;
    }

    /**
     * Generate compra id.
     * 
     * @return the int
     */
    public int generateCompraId() {
        int id = get_idCompra();
        set_idCompra(id + 1);
        return id;
    }

    /**
     * Regista nif.
     * 
     * @param nif the nif
     */
    public void registaNIF(int nif) {
        if (isValidNif(nif)) {
            set_nif(nif);
        } else {
            throw new UnknownError(); //TODO: DEFINIR EXCEPCAO
        }
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
     * Gets the email.
     * 
     * @return the email
     */
    public String getEmail() {
        return get_email();
    }

    /**
     * Gets the nif.
     * 
     * @return the nif
     */
    public String getNif() {
        if (get_nif() != null) {
            return get_nif().toString();
        } else {
            String s = new String("Não tem Nif disponivel");
            return s;
        }
    }

    /**
     * Gets the saldo.
     *
     * @return the saldo
     */
    public double getSaldo() {
        return get_saldo();
    }
    
    /**
     * Sets the saldo.
     *
     * @param quantidade the new saldo
     */
    public void setSaldo(double quantidade) {
        double temSaldo = getSaldo() + quantidade;
        if(temSaldo>0)
            set_saldo(temSaldo);
        else
            set_saldo(0.0);
    }
    
    public void limpaTabuleiro() throws TabuleiroVazioException, NaoExisteTabuleiroException {
        if(hasTabuleiro()){
            if(getTabuleiro().hasAnyItem()){
                getTabuleiro().limpaLista();
            }
            else{
               throw new TabuleiroVazioException("Tabuleiro do cliente ja esta vazio", this.getNome());  
            }
        }
        else{
            throw new NaoExisteTabuleiroException("Cliente nao contem tabuleiro", this.getNome());  
        }
    }

    /**
     * Imprime Cliente do portal.
     * 
     * @return The client information
     */
    public String print() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.print());
        stringBuilder.append("\n\tmorada = " + getMorada() + "\n\temail = " + getEmail()
                + "\n\tnif = " + getNif());
        return stringBuilder.toString();
    }
}
