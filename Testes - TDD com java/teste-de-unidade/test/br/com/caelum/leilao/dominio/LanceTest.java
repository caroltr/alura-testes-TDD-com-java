package br.com.caelum.leilao.dominio;

import org.junit.Test;

public class LanceTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void naoDeveCriarLanceComValor0() {
		
		new Lance(new Usuario("John Doe"), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoDeveCriarLanceComValorNegativo() {
		
		new Lance(new Usuario("John Doe"), -10);
	}
}
