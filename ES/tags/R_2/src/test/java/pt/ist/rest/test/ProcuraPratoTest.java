package pt.ist.rest.test;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.rest.exceptions.PratoNaoExisteException;
import java.util.List;

import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.domain.Prato;
import pt.ist.rest.domain.Restaurante;
import pt.ist.rest.service.ProcurarPratosPorNomeService;
import pt.ist.rest.service.dto.PratoDto;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


public class ProcuraPratoTest extends RestServiceTestCase {

private String nomePratoParcial1;
private String nomePratoParcial2;
private String nomePratoParcial3;
private Prato p1;
private Prato p2;
private Prato p3;
private Prato p4;
private Prato p5;
private Restaurante rest1;
private Restaurante rest2;
private StringBuilder stringBuilder;
private String result;
private ProcurarPratosPorNomeService procura;
private List<PratoDto> listaPratos;

public ProcuraPratoTest(String testName) {
	super(testName);
}

@Before
protected void setUp() {
	super.setUp();
	PortalRestaurante portal = FenixFramework.getRoot();
	nomePratoParcial1 = "acalhau";
	nomePratoParcial2 = "Carn";
	nomePratoParcial3 = "Azeite";
	stringBuilder = new StringBuilder();
	p1 = new Prato("Bacalhau com Natas", 2, 2, portal.generatePratoId());
	p2 = new Prato("Bacalhau a Bras", 3, 3, portal.generatePratoId());
	p3 = new Prato("Carninha", 70, 1, portal.generatePratoId());
	p4 = new Prato("Carne de Vaca", 7, 3, portal.generatePratoId());
	p5 = new Prato("Peixe embacalhau", 8, 60, portal.generatePratoId());
	rest1 = new Restaurante("Afonso dos Leitoes", "Rua do Cruzeiro");
	rest2 = new Restaurante("Ze do Pipo", "Rua das Conchas");
	rest1.adicionaPrato(p1.getNome(), p1.getPreco(), p1.getCalorias(), p1.getId());
	rest2.adicionaPrato(p2.getNome(), p2.getPreco(), p2.getCalorias(), p2.getId());
	rest1.adicionaPrato(p3.getNome(), p3.getPreco(), p3.getCalorias(), p3.getId());
	rest2.adicionaPrato(p4.getNome(), p4.getPreco(), p4.getCalorias(), p4.getId());
	rest2.adicionaPrato(p5.getNome(), p5.getPreco(), p5.getCalorias(), p5.getId());
}

@After
protected void tearDown() {
     super.tearDown();
	 p1 = null;
	 p2 = null;
	 p3 = null;
	 p4 = null;
	 p5 = null;
	 rest1 = null;
	 rest2 = null;
	 listaPratos = null;
	 result = null;
	 stringBuilder = null;
}

@Test
public void testStringMeio() {
	
	result = "Prato: Bacalhau com Natas\tcalorias = 2\tpreco = 2\tclassificacao = 1\trestaurante = Afonso dos Leitoes\nPrato: Bacalhau a Bras\tcalorias = 3\tpreco = 3\tclassificacao = 1\trestaurante = Ze do Pipo\nPrato: Peixe embacalhau\tcalorias = 60\tpreco = 8\tclassificacao = 1\trestaurante = Ze do Pipo\n";
	procura = new ProcurarPratosPorNomeService(nomePratoParcial1);
	
	try{ procura.execute(); }
	catch(PratoNaoExisteException e){
		System.out.println("\n" + e);
	}
	
	listaPratos = procura.getPratos();
	for(PratoDto pDto : listaPratos){
        stringBuilder.append("Prato: " + pDto.getNome());
        stringBuilder.append("\tcalorias = " + pDto.getCalorias());
        stringBuilder.append("\tpreco = " + pDto.getPreco());
        stringBuilder.append("\tclassificacao = " + pDto.getClassificacao());
        stringBuilder.append("\trestaurante = " + pDto.getRestaurante() + "\n");
	}
	
	assertTrue(stringBuilder.equals(result));

}

@Test
public void testStringInicio() {
	
	result = "Prato: Carninha\tcalorias = 1\tpreco = 70\tclassificacao = 1\trestaurante = Afonso dos Leitoes\nPrato: Carne de Vaca\tcalorias = 3\tpreco = 7\tclassificacao = 1\trestaurante = Ze do Pipo\n";
	procura = new ProcurarPratosPorNomeService(nomePratoParcial2);
	
	try{ procura.execute(); }
	catch(PratoNaoExisteException e){
		System.out.println("\n" + e);
	}
	
	listaPratos = procura.getPratos();
			
	for(PratoDto pDto : listaPratos){
        stringBuilder.append("Prato: " + pDto.getNome());
        stringBuilder.append("\tcalorias = " + pDto.getCalorias());
        stringBuilder.append("\tpreco = " + pDto.getPreco());
        stringBuilder.append("\tclassificacao = " + pDto.getClassificacao());
        stringBuilder.append("\trestaurante = " + pDto.getRestaurante() + "\n");
	}
	
	assertTrue(stringBuilder.equals(result));
}

@Test
public void testStringInexistente() {
	
	result = "";
	procura = new ProcurarPratosPorNomeService(nomePratoParcial3);
	
	try{ procura.execute(); }
	catch(PratoNaoExisteException e){
		System.out.println("\n" + e);
	}
	
	listaPratos = procura.getPratos();
			
	for(PratoDto pDto : listaPratos){
        stringBuilder.append("Prato: " + pDto.getNome());
        stringBuilder.append("\tcalorias = " + pDto.getCalorias());
        stringBuilder.append("\tpreco = " + pDto.getPreco());
        stringBuilder.append("\tclassificacao = " + pDto.getClassificacao());
        stringBuilder.append("\trestaurante = " + pDto.getRestaurante() + "\n");
	}
	
	assertTrue(stringBuilder.equals(result));
}

}
