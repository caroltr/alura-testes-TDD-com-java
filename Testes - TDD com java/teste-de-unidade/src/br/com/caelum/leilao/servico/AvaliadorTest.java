package br.com.caelum.leilao.servico;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;


public class AvaliadorTest {

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
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		double maiorEsperado = 400.0;
		double menorEsperado = 250.0;
		
		// parte 3: validacao
		Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
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
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		double mediaEsperada = (250.0 + 300.0 + 400.0) / 3.0;
		
		// parte 3: validacao
		Assert.assertEquals(mediaEsperada, leiloeiro.getMedia(), 0.00001);
	}
}
