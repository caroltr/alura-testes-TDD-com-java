package br.com.caelum.leilao.servico;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;


public class AvaliadorTest {

	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario maria;
	private Usuario jose;

	@Before
	public void setUp() {
		this.leiloeiro = new Avaliador();
		
		this.joao = new Usuario("João");
		this.jose = new Usuario("José");
		this.maria = new Usuario("Maria");
	}
	
	@After
	public void finaliza() {
	  System.out.println("fim");
	}
	
	@BeforeClass
	public static void testandoBeforeClass() {
	  System.out.println("before class");
	}
	
	@AfterClass
	public static void testandoAfterClass() {
	  System.out.println("after class");
	}
	
	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.constroi();
		
		leiloeiro.avalia(leilao);
	}
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 250.0)
				.lance(jose, 300.0)
				.lance(maria, 400.0)
				.constroi();
		
		// parte 2: ação
		leiloeiro.avalia(leilao);
		
		double maiorEsperado = 400.0;
		double menorEsperado = 250.0;
		
		// parte 3: validacao
		assertThat(leiloeiro.getMaiorLance(), equalTo(maiorEsperado));
		assertThat(leiloeiro.getMenorLance(), equalTo(menorEsperado));
	}
	
	@Test
	public void deveSaberCalularMediaDeLances() {
		
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 250.0)
				.lance(jose, 300.0)
				.lance(maria, 400.0)
				.constroi();
		
		// parte 2: ação
		leiloeiro.avalia(leilao);
		
		double mediaEsperada = (250.0 + 300.0 + 400.0) / 3.0;
		
		// parte 3: validacao
		assertThat(leiloeiro.getMedia(), equalTo(mediaEsperada));
	}
	
	@Test
    public void deveEntenderLeilaoComApenasUmLance() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 10000.0)
				.constroi();
        
        leiloeiro.avalia(leilao);

        assertThat(leiloeiro.getMaiorLance(), equalTo(10000.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(10000.0));
    }
	
	@Test
    public void deveEncontrarOsTresMaioresLances() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 100.0)
				.lance(maria, 200.0)
				.lance(joao, 300.0)
				.lance(maria, 400.0)
				.constroi();
        
        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertThat(maiores.size(), equalTo(3));
        
        assertThat(maiores, hasItems(
        		new Lance(maria, 400.0),
        		new Lance(joao, 300.0),
        		new Lance(maria, 200.0)
        ));
    }
	
	@Test
    public void deveEntenderLeilaoComDadosEmOrdemAleatoria() {
        
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 200.0)
				.lance(maria, 450.0)
				.lance(jose, 120.0)
				.lance(joao, 700.0)
				.lance(maria, 630.0)
				.lance(jose, 230.0)
				.constroi();

        leiloeiro.avalia(leilao);
        
        assertThat(leiloeiro.getMaiorLance(), equalTo(700.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(120.0));
    }
	
	@Test
    public void deveEntenderLancesEmOrdemDecrescente() {
        
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 400.0)
				.lance(maria, 300.0)
				.lance(joao, 200.0)
				.lance(maria, 100.0)
				.constroi();

        leiloeiro.avalia(leilao);
        
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(100.0));
    }
	
	@Test
    public void deveEncontrarOsMaioresComCincoLances() {
        
        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 400.0)
				.lance(maria, 500.0)
				.lance(joao, 300.0)
				.lance(maria, 100.0)
				.lance(maria, 200.0)
				.constroi();

        leiloeiro.avalia(leilao);
        
        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertThat(maiores.size(), equalTo(3));
        
        assertThat(maiores, hasItems(
        		new Lance(maria, 500.0),
        		new Lance(joao, 400.0),
        		new Lance(joao, 300.0)
        ));
    }
	
	@Test
    public void deveEncontrarOsMaioresComDoisLances() {

        Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo")
				.lance(joao, 200.0)
				.lance(maria, 100.0)
				.constroi();

        leiloeiro.avalia(leilao);
        
        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertThat(maiores.size(), equalTo(2));
        
        assertThat(maiores, hasItems(
        		new Lance(joao, 200.0),
        		new Lance(maria, 100.0)
        ));
    }
}
