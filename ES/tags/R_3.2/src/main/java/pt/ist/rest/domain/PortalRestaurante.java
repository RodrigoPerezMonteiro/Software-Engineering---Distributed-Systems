package pt.ist.rest.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import pt.ist.rest.exceptions.ClientESaldoInsuficienteException;
import pt.ist.rest.exceptions.ClienteJaExisteException;
import pt.ist.rest.exceptions.ClienteNaoExisteException;
import pt.ist.rest.exceptions.GestorAddRemovePratosException;
import pt.ist.rest.exceptions.GestorAdicionaItemException;
import pt.ist.rest.exceptions.GestorJaGereRestauranteException;
import pt.ist.rest.exceptions.NaoExisteTabuleiroException;
import pt.ist.rest.exceptions.PratoNaoExisteEmRestauranteException;
import pt.ist.rest.exceptions.PratoNaoExisteException;
import pt.ist.rest.exceptions.RestauranteJaExisteException;
import pt.ist.rest.exceptions.RestauranteNaoExisteException;
import pt.ist.rest.exceptions.UtilizadorJaExisteException;
import pt.ist.rest.exceptions.UtilizadorNaoExisteException;
import pt.ist.rest.exceptions.restException;


// TODO: Auto-generated Javadoc
/**
 * The Class PortalRestaurante.
 */
public class PortalRestaurante extends
        PortalRestaurante_Base {

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
            if (user.getTipoUtilizador() == 0) {
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
            if (user.getTipoUtilizador() == 1) {
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
    
    public Utilizador getUtilizadorByUsename(String usernameUtilizador) throws UtilizadorNaoExisteException {
        for (Utilizador user : getUtilizadorSet()) {
            if(user.getUsername().equals(usernameUtilizador)){
                return user;
            }
        }
        throw new UtilizadorNaoExisteException("Cliente não existe", usernameUtilizador);
    }

    /**
     * Gets the cliente by username.
     * 
     * @param clienteUsername the cliente username
     * @return the cliente by username
     */
    public Cliente getClienteByUsername(String clienteUsername) throws ClienteNaoExisteException {
            for (Utilizador user : getUtilizadorSet()) {
                if (user.getUsername().equals(clienteUsername) && user.getTipoUtilizador() == 0) {
                    return (Cliente) user;
                }
            }
            throw new ClienteNaoExisteException("Cliente não existe", clienteUsername);
    }

    /**
     * Gets the gestor by username.
     * 
     * @param gestorUsername the gestor username
     * @return the gestor by username
     */
    public GestorRestaurante getGestorByUsername(String gestorUsername) throws UtilizadorNaoExisteException {
        for (Utilizador user : getUtilizadorSet()) {
            if (user.getUsername().equals(gestorUsername) && user.getTipoUtilizador() == 1) {
                return (GestorRestaurante) user;
            }
        }
        throw new UtilizadorNaoExisteException("Não existe gestor", gestorUsername);
    }

    /**
     * Gets the restaurante by name.
     * 
     * @param restNome the rest nome
     * @return the restaurante by name
     */

    public Restaurante getRestauranteByName(String restNome) throws RestauranteNaoExisteException {
        for (Restaurante r : getRestauranteSet()) {
            if (r.getNome().equals(restNome)) {
                return r;
            }
        }
        throw new RestauranteNaoExisteException("Não existe Restaurante", restNome);
    }

    /**
     * Gets the prato by id from restaurante.
     * 
     * @param idPrato the id prato
     * @param restaurante the restaurante
     * @return the prato by id from restaurante
     */
    public Prato getPratoByIdFromRestaurante(Integer idPrato, Restaurante restaurante) throws PratoNaoExisteEmRestauranteException {
        for (Prato prato : restaurante.getPratoSet()) {
            if (prato.getId() == idPrato) {
                return prato;
            }
        }
        throw new PratoNaoExisteEmRestauranteException("O prato nao existe no restaurante: ",idPrato,restaurante.getNome()); 
    }

    /**
     * Gets the prato by nome from restaurante.
     * 
     * @param nomePrato the nome prato
     * @param restaurante the restaurante
     * @return the prato by nome from restaurante
     */
    public Prato getPratoByNomeFromRestaurante(String nomePrato, Restaurante restaurante) throws PratoNaoExisteException {

        for (Prato prato : restaurante.getPratoSet()) {
            if (prato.getNome().equals(nomePrato)) {
                return prato;
            }
        }
        throw new PratoNaoExisteException("Prato não existe no restaurante", nomePrato);
    }


    public Prato getPratoByNome(String nomePrato) throws PratoNaoExisteException {
        for (Restaurante restaurante : getRestauranteSet()) {
            for (Prato prato : restaurante.getPratoSet()) {
                if (prato.getNome().equals(nomePrato)) {
                    return prato;
                }
            }
        }
        throw new PratoNaoExisteException("Prato não existe: ", nomePrato);
    }
    
      public Prato getPratoById(int idPrato) throws PratoNaoExisteException {
      for (Restaurante restaurante : getRestauranteSet()) {
          for (Prato prato : restaurante.getPratoSet()) {
              if (prato.getId()==idPrato) {
                  return prato;
              }
          }
      }
      throw new PratoNaoExisteException("Prato não existe: id: ", String.valueOf(idPrato));
    }

    public ArrayList<Prato> getListaDePratos() {
        	ArrayList<Prato> listaPratos = new ArrayList<Prato>();
        	for(Restaurante restaurante : getRestauranteSet()){
        		for (Prato prato : restaurante.getPratoSet()) {
        			listaPratos.add(prato);
            	}
        	} 
        	return listaPratos;
    }

//    public String getRestauranteDoPrato(String nomePrato) {
//        Prato p = getPratoByNome(nomePrato);
//        return p.getRestaurante().getNome();
//    }
    
    public boolean existeUtilizador(String username) {
        boolean existe = false;

        for (Utilizador user :getUtilizadorSet()) {
            if (user.getUsername().equals(username)) {
                existe = true;
                return existe;
            }
        }
        return existe;
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
     * @param saldo the saldo
     */
    public void registaCliente(String nome,
                               String username,
                               String password,
                               String morada,
                               String email,
                               double saldo) throws ClienteJaExisteException{
        if (existeCliente(username)) {
            throw new ClienteJaExisteException(); 
        } else {
            Cliente cl = new Cliente(nome, username, password, morada, email, saldo);
            adicionaUtilizador(cl);
        }
    }


    /**
     * Adiciona gestor.
     * 
     * @param nome the nome
     * @param username the username
     * @param password the password
     */
    public void adicionaGestor(String nome, String username, String password) throws UtilizadorJaExisteException {
        if (existeUtilizador(username)) {
            throw new UtilizadorJaExisteException("O Utilizador já existe", username);
        } else {
            GestorRestaurante gs = new GestorRestaurante(nome, username, password);
            adicionaUtilizador(gs);
        }
    }

    public void adicionaUtilizador(Utilizador user) throws UtilizadorJaExisteException {
        boolean existe = false;
        for (Utilizador utilizador : getUtilizadorSet()) {
            if (utilizador.getUsername().equals(user.getUsername())) {
                existe = true;
            }
        }
        if (existe) {
            throw new UtilizadorJaExisteException("Utilizador já existe", user.getNome());
        } else {
            super.addUtilizador(user);
        }
    }


    /**
     * Adds the restaurante.
     * 
     * @param nome the nome
     * @param morada the morada
     */
    public void adicionaRestaurante(String nome, String morada) throws RestauranteJaExisteException {
        if (!existeRestaurante(nome)) {
            Restaurante r = new Restaurante(nome, morada);
            addRestaurante(r);
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
     * @param nomeRestaurante the nome restaurante
     * @param usernameGestor the username gestor
     */
    public void addPratoToRestaurante(String nomePrato,
                                      Double precoPrato,
                                      Double caloriasPrato,
                                      String nomeRestaurante,
                                      String usernameGestor) throws GestorAddRemovePratosException {
        Restaurante restaurante = getRestauranteByName(nomeRestaurante);
            if (getGestorByUsername(usernameGestor).gereRestaurante(nomeRestaurante)) {
                restaurante.adicionaPrato(nomePrato, precoPrato, caloriasPrato,generatePratoId());
            } else {
                throw new GestorAddRemovePratosException(
                        "Gestor sem privilégios sobre o restaurante", usernameGestor,
                        nomeRestaurante);
            }   
    }

    /**
     * Removes the prato from restaurante.
     * 
     * @param idPrato the id prato
     * @param nomeRestaurante the nome restaurante
     * @param usernameGestor the username gestor
     */
    public void removePratoFromRestaurante(Integer idPrato,
                                           String nomeRestaurante,
                                           String usernameGestor) {
        Restaurante restaurante = getRestauranteByName(nomeRestaurante);
        if (getGestorByUsername(usernameGestor).gereRestaurante(nomeRestaurante)) {
            restaurante.getPratoSet().remove(getPratoByIdFromRestaurante(idPrato, restaurante));
        } else
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
    public void adicionaCompra(Compra compra) {
        addCompra(compra);
    }

    /**
     * Associa gestor a um Restaurante.
     * 
     * @param usernameGestor the username gestor
     * @param nomeRestaurante the nome restaurante
     */
    public void associaGestor(String usernameGestor, String nomeRestaurante) throws GestorJaGereRestauranteException {
        Restaurante restaurante = getRestauranteByName(nomeRestaurante);
        GestorRestaurante gestor = getGestorByUsername(usernameGestor);
            if (gestor.getGestorRestaurante() == null) {
                restaurante.adicionaGestor(gestor);
            } else {
                throw new GestorJaGereRestauranteException("O Gestor já gere outro restaurante",
                        gestor.getUsername(), restaurante.getNome());
            }
    }

    /**
     * Prints the utilizadores.
     */
    public void printUtilizadores() {
        for (Cliente c : getClientes()) {
            System.out.println("\n\n" + c.print());
        }
        for (GestorRestaurante g : getGestores()) {
            System.out.println("\n\n" + g.print());
        }
    }


    public void adicionaPratoCliente(Prato p, String username, int quantidade, boolean alterar) throws GestorAdicionaItemException {
        Utilizador user = getUtilizadorByUsename(username);

        if (user.isCliente()) {
            Cliente cl = getClienteByUsername(username);
            Compra c;
            if (!cl.hasTabuleiro()) {
                c = cl.novoTabuleiro();
            } else {
                c = cl.getTabuleiro();
            }
            c.adicionaPrato(p, quantidade, alterar);
        } else {
            throw new GestorAdicionaItemException("Gestor não pode adicionar Prato", user.getUsername());
        }
    }

    public void clienteFinalizaCompra(String username) throws NaoExisteTabuleiroException, ClientESaldoInsuficienteException {
        Cliente cl = getClienteByUsername(username);
        if (cl.hasTabuleiro()) {
            Compra c = cl.getTabuleiro();
            if (cl.get_saldo() >= c.getTotal()) {
                cl.setSaldo(cl.getSaldo() - c.getTotal());
                c.setRegisto(true);
            } else {
                throw new ClientESaldoInsuficienteException("O cliente nao tem saldo suficiente", username, cl.getSaldo());
            }
        } else {
            throw new NaoExisteTabuleiroException("O cliente nao contem tabuleiro: ", username);  
        }
    }

    public void setClienteNIF(String username, int nif) {
        getClienteByUsername(username).registaNIF(nif);
    }
    
    /**
     * Prints the restaurantes.
     */
    public void printRestaurantes() {
        for (Restaurante r : getRestaurantes()) {
            System.out.println("\n\n" + r.print());
            for (Prato p : r.getPratos()) {
                System.out.println(p.print());
            }
        }
    }
    
    public boolean existePrato(String nomePrato) {
        for (Restaurante rest : getRestauranteSet()) {
            for (Prato p : rest.getPratoSet()) {
                if(p.getNome().equals(nomePrato)){
                    return true;
                }
            }
        }
        return false;
    }
}
