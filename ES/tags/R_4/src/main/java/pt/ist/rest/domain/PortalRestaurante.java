package pt.ist.rest.domain;

import java.util.ArrayList;
import java.util.Set;

import pt.ist.registofatura.ws.Serie;
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
import pt.ist.rest.exceptions.NaoExisteCompraException;
import pt.ist.rest.exceptions.restException;


// TODO: Auto-generated Javadoc
/**
 * The Class PortalRestaurante.
 */
public class PortalRestaurante extends
        PortalRestaurante_Base {
    
    /**
     * The Constant NUM_MAX_FACTURAS_POR_SERIE.
     */
    private static final int NUM_MAX_FACTURAS_POR_SERIE = 4;
    
    /**
     * The current facturas number.
     */
   
    /**
     * The reg fac local.
     */
   // private static final RegistoFaturaLocal regFacLocal = new RegistoFaturaLocal();
    
    /**
     * The serie.
     */
    private Serie serie = null;
    
    /**
     * Need serie.
     *
     * @return true, if successful
     */
    public boolean needSerie() {
        System.out.println("Current:\t"+get_currentFacturasNumber());
        System.out.println("MAX:\t"+NUM_MAX_FACTURAS_POR_SERIE);
        if(get_currentFacturasNumber() == NUM_MAX_FACTURAS_POR_SERIE){
            set_currentFacturasNumber(0);
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Inc current facturas number.
     */
    public void incCurrentFacturasNumber() {
        set_currentFacturasNumber(get_currentFacturasNumber() + 1);
    }
    
    /**
     * Gets the registo fatura local.
     *
     * @return the registo fatura local
     */
//    public RegistoFaturaLocal getRegistoFaturaLocal() {
//        return regFacLocal;
//    }
    
    
    /**
     * Sets the serie.
     *
     * @param s the new serie
     */
    public void setSerie(Serie s) {
        this.serie = s;
    }
    
    /**
     * Gets the serie.
     *
     * @return the serie
     */
    public Serie getSerie() {
        return this.serie;
    }
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
    
    /**
     * Gets the utilizador by usename.
     *
     * @param usernameUtilizador the username utilizador
     * @return the utilizador by usename
     * @throws UtilizadorNaoExisteException the utilizador nao existe exception
     */
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
     * @throws ClienteNaoExisteException the cliente nao existe exception
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
     * @throws UtilizadorNaoExisteException the utilizador nao existe exception
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
     * @throws RestauranteNaoExisteException the restaurante nao existe exception
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
     * @throws PratoNaoExisteEmRestauranteException the prato nao existe em restaurante exception
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
     * @throws PratoNaoExisteException the prato nao existe exception
     */
    public Prato getPratoByNomeFromRestaurante(String nomePrato, Restaurante restaurante) throws PratoNaoExisteException {

        for (Prato prato : restaurante.getPratoSet()) {
            if (prato.getNome().equals(nomePrato)) {
                return prato;
            }
        }
        throw new PratoNaoExisteException("Prato não existe no restaurante", nomePrato);
    }


    /**
     * Gets the prato by nome.
     *
     * @param nomePrato the nome prato
     * @return the prato by nome
     * @throws PratoNaoExisteException the prato nao existe exception
     */
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
    
      /**
       * Gets the prato by id.
       *
       * @param idPrato the id prato
       * @return the prato by id
       * @throws PratoNaoExisteException the prato nao existe exception
       */
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

    /**
     * Gets the lista de pratos.
     *
     * @return the lista de pratos
     */
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
    
    /**
 * Existe utilizador.
 *
 * @param username the username
 * @return true, if successful
 */
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
     * @throws ClienteJaExisteException the cliente ja existe exception
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
     * @throws UtilizadorJaExisteException the utilizador ja existe exception
     */
    public void adicionaGestor(String nome, String username, String password) throws UtilizadorJaExisteException {
        if (existeUtilizador(username)) {
            throw new UtilizadorJaExisteException("O Utilizador já existe", username);
        } else {
            GestorRestaurante gs = new GestorRestaurante(nome, username, password);
            adicionaUtilizador(gs);
        }
    }

    /**
     * Adiciona utilizador.
     *
     * @param user the user
     * @throws UtilizadorJaExisteException the utilizador ja existe exception
     */
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
     * @throws RestauranteJaExisteException the restaurante ja existe exception
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
     * @param tipo the tipo
     * @param precoPrato the preco prato
     * @param caloriasPrato the calorias prato
     * @param nomeRestaurante the nome restaurante
     * @param usernameGestor the username gestor
     * @throws GestorAddRemovePratosException the gestor add remove pratos exception
     */
    public void addPratoToRestaurante(String nomePrato,
                                      String tipo,
                                      Double precoPrato,
                                      Double caloriasPrato,
                                      String nomeRestaurante,
                                      String usernameGestor) throws GestorAddRemovePratosException {
        Restaurante restaurante = getRestauranteByName(nomeRestaurante);
            if (getGestorByUsername(usernameGestor).gereRestaurante(nomeRestaurante)) {
                restaurante.adicionaPrato(nomePrato,tipo, precoPrato, caloriasPrato,generatePratoId());
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
     * @throws GestorAddRemovePratosException the gestor add remove pratos exception
     */
    public void removePratoFromRestaurante(Integer idPrato,
                                           String nomeRestaurante,
                                           String usernameGestor) throws GestorAddRemovePratosException {
        Restaurante restaurante = getRestauranteByName(nomeRestaurante);
        if (getGestorByUsername(usernameGestor).gereRestaurante(nomeRestaurante)) {
            restaurante.getPratoSet().remove(getPratoByIdFromRestaurante(idPrato, restaurante));
        } else
            throw new GestorAddRemovePratosException(
                    "Gestor nao pode remover prato do restaurante", usernameGestor, nomeRestaurante);
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
     * @throws GestorJaGereRestauranteException the gestor ja gere restaurante exception
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


    /**
     * Adiciona prato cliente.
     *
     * @param p the p
     * @param username the username
     * @param quantidade the quantidade
     * @param alterar the alterar
     * @throws GestorAdicionaItemException the gestor adiciona item exception
     */
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

    /**
     * Cliente finaliza compra.
     *
     * @param username the username
     * @throws NaoExisteTabuleiroException the nao existe tabuleiro exception
     * @throws ClientESaldoInsuficienteException the client e saldo insuficiente exception
     */
    public void clienteFinalizaCompra(String username) throws NaoExisteTabuleiroException, ClientESaldoInsuficienteException {
        Cliente cl = getClienteByUsername(username);
        double totalCompra =0.0;
        if (cl.hasTabuleiro()) {
            Compra c = cl.getTabuleiro();
            System.out.println("Total da compra:\t"+c.getTotal());
            if (cl.get_saldo() >= c.getTotal()) {
                totalCompra = c.getTotal();
                System.out.println("saldo ANTERIOR:\t"+cl.getSaldo());
               // cl.setSaldo(cl.getSaldo() - c.getTotal());
                cl.setSaldo(totalCompra - (2 * totalCompra));
                System.out.println("saldo ACTUAL:\t"+cl.getSaldo());
                cl.fechaTabuleiro();
            } else {
                throw new ClientESaldoInsuficienteException("O cliente nao tem saldo suficiente", username, cl.getSaldo());
            }
        } else {
            throw new NaoExisteTabuleiroException("O cliente nao contem tabuleiro: ", username);  
        }
    }

    /**
     * Sets the cliente nif.
     *
     * @param username the username
     * @param nif the nif
     */
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
    
    /**
     * Existe prato.
     *
     * @param nomePrato the nome prato
     * @return true, if successful
     */
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
    
    /**
     * Verifica password.
     *
     * @param usernameCliente the username cliente
     * @param password the password
     * @return true, if successful
     */
    public boolean verificaPassword(String usernameCliente, String password) {
        Cliente cl = getClienteByUsername(usernameCliente);
        if(cl.getPassword().equals(password)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public Compra getCompraById(int idcompra) throws NaoExisteCompraException {
        for (Cliente cl : getClientes()) {
            for (Compra c : cl.getCompraSet()) {
                if (c.getIdCompra() == idcompra) {
                    return c;
                }
            }
        }
        throw new NaoExisteCompraException("Nao existe compra com esse id", idcompra);
    }
    
    public int getNifOfClientByUsername(String username) {
        return getClienteByUsername(username).get_nif();
    }
}
