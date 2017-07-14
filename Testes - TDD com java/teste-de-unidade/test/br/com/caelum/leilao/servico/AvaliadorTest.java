package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;


public class AvaliadorTest {

	private Avaliador leiloeiro;

	private void criaAvaliador() {
		this.leiloeiro = new Avaliador();
	}
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		
		// parte 1: cenário
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Playstation 3 Novo");
		
		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));
		
		// parte 2: ação
		criaAvaliador();
		leiloeiro.avalia(leilao);
		
		double maiorEsperado = 400.0;
		double menorEsperado = 250.0;
		
		// parte 3: validacao
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}
	
	@Test
	public void deveSaberCalularMediaDeLances() {
		
		// parte 1: cenário
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Playstation 3 Novo");
		
		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));
		
		// parte 2: ação
		criaAvaliador();
		leiloeiro.avalia(leilao);
		
		double mediaEsperada = (250.0 + 300.0 + 400.0) / 3.0;
		
		// parte 3: validacao
		assertEquals(mediaEsperada, leiloeiro.getMedia(), 0.00001);
	}
	
	@Test
    public void deveEntenderLeilaoComApenasUmLance() {
        Usuario joao = new Usuario("Joao"); 
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao,1000.0));

        criaAvaliador();
        leiloeiro.avalia(leilao);

        assertEquals(1000, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(1000, leiloeiro.getMenorLance(), 0.0001);
    }
	
	@Test
    public void deveEncontrarOsTresMaioresLances() {
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));
        leilao.propoe(new Lance(joao, 300.0));
        leilao.propoe(new Lance(maria, 400.0));

        criaAvaliador();
        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(3, maiores.size());
        assertEquals(400, maiores.get(0).getValor(), 0.00001);
        assertEquals(300, maiores.get(1).getValor(), 0.00001);
        assertEquals(200, maiores.get(2).getValor(), 0.00001);
    }
	
	@Test
    public void deveEntenderLeilaoComDadosEmOrdemAleatoria() {
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");
        Usuario jose = new Usuario("José");
        Leilao leilao = new Leilao("Playstation 3 Novo");
        
        leilao.propoe(new Lance(joao, 200.0));
        leilao.propoe(new Lance(maria, 450.0));
        leilao.propoe(new Lance(jose, 120.0));
        leilao.propoe(new Lance(joao, 700.0));
        leilao.propoe(new Lance(maria, 630.0));
        leilao.propoe(new Lance(jose, 230.0));

        criaAvaliador();
        leiloeiro.avalia(leilao);
        
        assertEquals(700.0, leiloeiro.getMaiorLance(), 0.00001);
        assertEquals(120.0, leiloeiro.getMenorLance(), 0.00001);
    }
	
	@Test
    public void deveEntenderLancesEmOrdemDecrescente() {
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");
        Leilao leilao = new Leilao("Playstation 3 Novo");
        
        leilao.propoe(new Lance(joao, 400.0));
        leilao.propoe(new Lance(maria, 300.0));
        leilao.propoe(new Lance(joao, 200.0));
        leilao.propoe(new Lance(maria, 100.0));

        criaAvaliador();
        leiloeiro.avalia(leilao);
        
        assertEquals(400.0, leiloeiro.getMaiorLance(), 0.00001);
        assertEquals(100.0, leiloeiro.getMenorLance(), 0.00001);
    }
	
	@Test
    public void deveEncontrarOsMaioresComCincoLances() {
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");
        Leilao leilao = new Leilao("Playstation 3 Novo");
        
        leilao.propoe(new Lance(joao, 400.0));
        leilao.propoe(new Lance(maria, 500.0));
        leilao.propoe(new Lance(joao, 300.0));
        leilao.propoe(new Lance(maria, 100.0));
        leilao.propoe(new Lance(maria, 200.0));

        criaAvaliador();
        leiloeiro.avalia(leilao);
        
        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(3, maiores.size(), 0.00001);
        assertEquals(500.0, maiores.get(0).getValor(), 0.00001);
        assertEquals(400.0, maiores.get(1).getValor(), 0.00001);
        assertEquals(300.0, maiores.get(2).getValor(), 0.00001);
    }
	
	@Test
    public void deveEncontrarOsMaioresComDoisLances() {
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");
        Leilao leilao = new Leilao("Playstation 3 Novo");
        
        leilao.propoe(new Lance(joao, 200.0));
        leilao.propoe(new Lance(maria, 100.0));

        criaAvaliador();
        leiloeiro.avalia(leilao);
        
        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(2, maiores.size(), 0.00001);
        assertEquals(200.0, maiores.get(0).getValor(), 0.00001);
        assertEquals(100.0, maiores.get(1).getValor(), 0.00001);
    }
	
	@Test
    public void deveEntenderMaioresSemLance() {
		
        Leilao leilao = new Leilao("Playstation 3 Novo");

        criaAvaliador();
        leiloeiro.avalia(leilao);
        
        assertEquals(0, leiloeiro.getTresMaiores().size(), 0.00001);
    }
}
