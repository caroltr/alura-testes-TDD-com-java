package br.com.caelum.leilao.dominio;

import static br.com.caelum.leilao.matchers.LeilaoMatcher.temUmLance;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;

public class LeilaoTest {
	
	@Test
	public void deveReceberVariosLances() {
		Leilao leilao = new Leilao("Macbook pro 15");
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000.0));
		leilao.propoe(new Lance(new Usuario("Steve Wozniak"), 3000.0));
		
		assertThat(leilao.getLances().size(), equalTo(2));
		
		assertThat(leilao.getLances(), hasItems(
				new Lance(new Usuario("Steve Jobs"), 2000),
				new Lance(new Usuario("Steve Wozniak"), 3000)
			));
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Leilao leilao = new Leilao("Macbook pro 15");
		Usuario steveJobs = new Usuario("Steve Jobs");
		
		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(steveJobs, 3000.0));
		
		assertThat(leilao.getLances().size(), equalTo(1));
		
		assertThat(leilao.getLances(), hasItems(
				new Lance(new Usuario("Steve Jobs"), 2000)
			));
	}
	
	@Test
	public void naoDeveAceitarMaisDoQueCincoLancesDeUmMesmoUsuario() {
		Leilao leilao = new Leilao("Macbook pro 15");
		Usuario steveJobs = new Usuario("Steve Jobs");
		Usuario billGates = new Usuario("Bill Gates");
		
		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(billGates, 3000.0));
		
		leilao.propoe(new Lance(steveJobs, 4000.0));
		leilao.propoe(new Lance(billGates, 5000.0));
		
		leilao.propoe(new Lance(steveJobs, 6000.0));
		leilao.propoe(new Lance(billGates, 7000.0));
		
		leilao.propoe(new Lance(steveJobs, 8000.0));
		leilao.propoe(new Lance(billGates, 9000.0));
		
		leilao.propoe(new Lance(steveJobs, 10000.0));
		leilao.propoe(new Lance(billGates, 11000.0));
		
		// deve ser ignorado
		leilao.propoe(new Lance(steveJobs, 12000.0));
		
		assertThat(leilao.getLances().size(), equalTo(10));
		
		double ultimoLanceValido = leilao.getLances().get(leilao.getLances().size() - 1).getValor();
		assertThat(ultimoLanceValido, equalTo(11000.0));
	}
	
	@Test
    public void deveDobrarOUltimoLanceDado() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");
        Usuario billGates = new Usuario("Bill Gates");

        leilao.propoe(new Lance(steveJobs, 2000));
        leilao.propoe(new Lance(billGates, 3000));
        leilao.dobraLance(steveJobs);

        assertThat(leilao.getLances().get(2).getValor(), equalTo(4000.0));
    }
	
	@Test
    public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        Usuario steveJobs = new Usuario("Steve Jobs");

        leilao.dobraLance(steveJobs);

        assertThat(leilao.getLances().size(), equalTo(0));
    }
	
	@Test
    public void deveReceberUmLance() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15").constroi();
        assertThat(leilao.getLances().size(), equalTo(0));

        Lance lance = new Lance(new Usuario("Steve Jobs"), 2000);
        leilao.propoe(lance);

        assertThat(leilao.getLances().size(), equalTo(1));
        assertThat(leilao, temUmLance(lance));
    }

}
