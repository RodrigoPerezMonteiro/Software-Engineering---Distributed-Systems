package pt.ist.rest.domain;
import java.util.ArrayList;
import java.util.Set;

import pt.ist.rest.exceptions.ClienteNaoExisteException;
import pt.ist.rest.exceptions.GestorAddRemovePratosException;
import pt.ist.rest.exceptions.GestorJaGereRestauranteException;
import pt.ist.rest.exceptions.PratoNaoExisteEmRestauranteException;
import pt.ist.rest.exceptions.RestauranteJaExisteException;
import pt.ist.rest.exceptions.RestauranteNaoExisteException;
import pt.ist.rest.exceptions.restException;


// TODO: Auto-generated Javadoc
/**
 * The Class PortalRestaurante.
 */
public class PortalRestaurante extends PortalRestaurante_Base {

    /**
     * Instantiates a new portal restaurante.
     */
    public PortalRestaurante() {
        super();
    }

    /**
     * Gets the clientes.
     *
     * @return the clientes
     */
    public ArrayList<Cliente> getClientes() {
        ArrayList<Cliente> _clientes = new ArrayList<Cliente>();
        for (Utilizador user : getUtilizadorSet()) {
            if (user.getTipoUtilizador()==0) {
                _clientes.add((Cliente) user);
            }
        }
        return _clientes;
    }
    
    /**
     * Gets the gestores.
     *
     * @return the gestores
     */
    public ArrayList<GestorRestaurante> getGestores() {
        ArrayList<GestorRestaurante> _gestores = new ArrayList<GestorRestaurante>();
        for (Utilizador user : getUtilizadorSet()) {
            if (user.getTipoUtilizador()==1) {
                _gestores.add((GestorRestaurante) user);
            }
        }
        return _gestores;
    }

    /**
     * Gets the restaurantes.
     *
     * @return the restaurantes
     */
    public Set<Restaurante> getRestaurantes() {
        return getRestauranteSet();
    }
    
    /**
     * Gets the cliente by username.
     *
     * @param clienteUsername the cliente username
     * @return the cliente by username
     */
    public Cliente getClienteByUsername(String clienteUsername) {
        try {
            for (Utilizador user : getUtilizadorSet()) {
                if (user.getUsername().equals(clienteUsername) && user.getTipoUtilizador() == 0) {
                    return (Cliente) user;
                }
            }
            throw new ClienteNaoExisteException("Cliente não existe", clienteUsername);
        } catch (ClienteNaoExisteException e) {
            System.out.println("\n"+e);
        }
        return null;
    }
    
    /**
     * Gets the gestor by username.
     *
     * @param gestorUsername the gestor username
     * @return the gestor by username
     */
    public GestorRestaurante getGestorByUsername(String gestorUsername){
        for (Utilizador user : getUtilizadorSet()) {   
            if(user.getUsername().equals(gestorUsername) && user.getTipoUtilizador()==1){
                return (GestorRestaurante)user;   
            }
        }
        throw new UnknownError(); //TODO: DEFINIR EXCEPCAO
    }
    
    /**
     * Gets the restaurante by name.
     *
     * @param restNome the rest nome
     * @return the restaurante by name
     */
    
    public Restaurante getRestauranteByName(String restNome) {
        try {
            for (Restaurante r : getRestauranteSet()) {
                if (r.getNome().equals(restNome)) {
                    return r;
                }
            }
            throw new RestauranteNaoExisteException("Não existe Restaurante", restNome); //TODO: DEFINIR EXCEPCAO
        } catch (RestauranteNaoExisteException e) {
            System.out.println("\n" + e);
        }
        return null;
    }
    
    /**
     * Gets the prato by id from restaurante.
     *
     * @param idPrato the id prato
     * @param restaurante the restaurante
     * @return the prato by id from restaurante
     */
    public Prato getPratoByIdFromRestaurante(Integer idPrato, Restaurante restaurante) {
        for (Prato prato : restaurante.getPratoSet()) {
            if(prato.getId() == idPrato){
                return prato;
            }
        }
        throw new UnknownError(); //TODO: DEFINIR EXCEPCAO
    }
    
    /**
     * Gets the prato by nome from restaurante.
     *
     * @param nomePrato the nome prato
     * @param restaurante the restaurante
     * @return the prato by nome from restaurante
     */
    public Prato getPratoByNomeFromRestaurante(String nomePrato, Restaurante restaurante) {
        try {
            for (Prato prato : restaurante.getPratoSet()) {
                if(prato.getNome().equals(nomePrato)){
                    return prato;
                }
            }
            throw new PratoNaoExisteEmRestauranteException("Prato não existe no restaurante", nomePrato, restaurante.getNome());
        } catch (PratoNaoExisteEmRestauranteException e) {
            System.out.println("\n"+e);
        }
        return null;
    }
   
    /**
     * Existe cliente.
     *
     * @param username the username
     * @return true, if successful
     */
    public boolean existeCliente(String username) {
        boolean existe = false;

        for (Cliente c : getClientes()) {
            if (c.getUsername().equals(username)) {
                existe = true;
                return existe;
            }
        }
        return existe;
    }
    
    /**
     * Existe gestor.
     *
     * @param username the username
     * @return true, if successful
     */
    public boolean existeGestor(String username) {
        boolean existe = false;

        for (GestorRestaurante g : getGestores()) {
            if (g.getUsername().equals(username)) {
                existe = true;
                return existe;
            }
        }
        return existe;
    }

    /**
     * Existe restaurante.
     *
     * @param nome the nome
     * @return true, if successful
     * @throws restException the rest exception
     */
    public boolean existeRestaurante(String nome) throws restException {
        boolean existe = false;

        for (Restaurante r : getRestauranteSet()) {
            if (r.getNome().equals(nome)) {
                existe = true;
            }
        }
        if (existe) {
            throw new RestauranteJaExisteException("Restaurante já existe", nome);
        } else {
            return existe;
        }
    }
    
    /**
     * Regista cliente.
     *
     * @param nome the nome
     * @param username the username
     * @param password the password
     * @param morada the morada
     * @param email the email
     */
    public void registaCliente(String nome,String username,String password,String morada,String email) {
        if (existeCliente(username)) {
            throw new UnknownError(); // TODO: DEFINIR NOVA EXCEPCAO
        } else {
            Cliente cl = new Cliente(nome, username, password, morada, email);
            addUtilizador(cl);
        }
    }
    
  
    /**
     * Adiciona gestor.
     *
     * @param nome the nome
     * @param username the username
     * @param password the password
     */
    public void adicionaGestor(String nome,String username,String password) {
        if (existeGestor(username)) {
            throw new UnknownError(); // TODO: DEFINIR NOVA EXCEPCAO
        } else {
            GestorRestaurante gs = new GestorRestaurante(nome, username, password);
            addUtilizador(gs);
        }
    }


    /**
     * Adds the restaurante.
     *
     * @param nome the nome
     * @param morada the morada
     */
    public void adicionaRestaurante(String nome, String morada){
        try {
            if (!existeRestaurante(nome)) {
                Restaurante r = new Restaurante(nome, morada);
                addRestaurante(r);
            }
        } catch (RestauranteJaExisteException e) {
            System.out.print("\n"+e);
        }
    }

    /**
     * Generate prato id.
     *
     * @return the int
     */
    public int generatePratoId() {
        int id = get_idPrato();
        set_idPrato(id + 1);
        return id;
    }

    /**
     * Adds the prato to restaurante.
     *
     * @param nomePrato the nome prato
     * @param precoPrato the preco prato
     * @param caloriasPrato the calorias prato
     * @param classificacaoPrato the classificacao prato
     * @param nomeRestaurante the nome restaurante
     * @param usernameGestor the username gestor
     */
    public void addPratoToRestaurante(String nomePrato,Double precoPrato,Double caloriasPrato,Integer classificacaoPrato,String nomeRestaurante,String usernameGestor){
        Restaurante restaurante = getRestauranteByName(nomeRestaurante);
                try {
                    if(getGestorByUsername(usernameGestor).gereRestaurante(nomeRestaurante)) {
                            restaurante.adicionaPrato(nomePrato,precoPrato,caloriasPrato,classificacaoPrato, generatePratoId());
                     }
                    else{
                            throw new GestorAddRemovePratosException("Gestor sem privilégios sobre o restaurante",usernameGestor,nomeRestaurante); 
                    }
                } catch (GestorAddRemovePratosException e) {
                    System.out.println("\n"+e);
                }
    }

    /**
     * Removes the prato from restaurante.
     *
     * @param idPrato the id prato
     * @param nomeRestaurante the nome restaurante
     * @param usernameGestor the username gestor
     */
    public void removePratoFromRestaurante(Integer idPrato, String nomeRestaurante, String usernameGestor) {
        Restaurante restaurante = getRestauranteByName(nomeRestaurante);
        if(getGestorByUsername(usernameGestor).gereRestaurante(nomeRestaurante)) {
            restaurante.getPratoSet().remove(getPratoByIdFromRestaurante(idPrato, restaurante));
        }else
            throw new UnknownError(); // TODO: DEFINIR NOVA EXCEPCAO
    }

    /**
     * Like prato.
     *
     * @param usernameCliente the username cliente
     * @param nomePrato the nome prato
     * @param nomeRestaurante the nome restaurante
     */
    public void likePrato(String usernameCliente, String nomePrato, String nomeRestaurante) {
        Cliente cliente = getClienteByUsername(usernameCliente);
        Restaurante restaurante = getRestauranteByName(nomeRestaurante);
        Prato prato = getPratoByNomeFromRestaurante(nomePrato, restaurante);
        cliente.adicionaPratoQueGosta(prato);
    }

    /**
     * Unlike prato.
     *
     * @param usernameCliente the username cliente
     * @param idPrato the id prato
     */
    public void unlikePrato(String usernameCliente, Integer idPrato) {
        Cliente cliente = getClienteByUsername(usernameCliente);
        cliente.removePratoQueGosta(idPrato);
    }
    
    /**
     * Adiciona compra.
     *
     * @param compra the compra
     */
    public void adicionaCompra(Compra compra){
        addCompra(compra);
    }
    
    /**
     * Associa gestor a um Restaurante.
     *
     * @param usernameGestor the username gestor
     * @param nomeRestaurante the nome restaurante
     */
    public void associaGestor(String usernameGestor, String nomeRestaurante ){
        Restaurante restaurante = getRestauranteByName(nomeRestaurante);
        GestorRestaurante gestor = getGestorByUsername(usernameGestor);
        try {
            if(gestor.getGestorRestaurante() == null){
            restaurante.adicionaGestor(gestor);
            }else{
                throw new GestorJaGereRestauranteException("O Gestor já gere outro restaurante", gestor.getUsername(), restaurante.getNome());
            }
        } catch (GestorJaGereRestauranteException e) {
            System.out.println("\n"+e);
        }
    }
    
    /**
     * Prints the utilizadores.
     */
    public void printUtilizadores(){
        for (Cliente c : getClientes()) {
            System.out.println("\n\n"+c.print());
        }
        for (GestorRestaurante g : getGestores()) {
            System.out.println("\n\n"+g.print());
        }
    }
    
    /**
     * Prints the restaurantes.
     */
    public void printRestaurantes() {
        for (Restaurante r : getRestaurantes()) {
            System.out.println("\n\n"+r.print());
            for (Prato p : r.getPratos()) {
                System.out.println(p.print());
            }
        }
    }
}
