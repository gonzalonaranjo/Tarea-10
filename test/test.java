import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import junio2013.Anuncio;
import junio2013.IBaseDeDatosDeAnunciantes;
import junio2013.IBaseDeDatosDePagos;
import junio2013.TablonDeAnuncios;

import org.junit.Before;
import org.junit.Test;


public class test {

	private TablonDeAnuncios tab;

	@Before
	public void setUp(){
		tab = new TablonDeAnuncios();
	}
	
	@Test
	public void crearTablonHayUnAnuncio(){
		assertEquals(1, tab.anunciosPublicados());
	}
	
	@Test
	public void publicarUnAnuncioYVerQueSeIncrementaElNumero(){
		Anuncio anuncio = new Anuncio("2 anuncio", "segundo anuncio", "LA EMPRESA");
		//creamos objeto Mock
		IBaseDeDatosDeAnunciantes bdAnunciantes = mock(IBaseDeDatosDeAnunciantes.class);
		IBaseDeDatosDePagos bdPagos = mock(IBaseDeDatosDePagos.class);
		
		tab.publicarAnuncio(anuncio, bdAnunciantes, bdPagos);
		
		assertEquals(2, tab.anunciosPublicados());
		
	}
	@Test
	public void publicarUnAnuncioQueNoSeaEmpresaYQueNoInserte(){
		Anuncio anuncio = new Anuncio("anuncio nuevo", "primer anuncio", "OTRA EMPRESA");
		IBaseDeDatosDeAnunciantes bdAnunciantes = mock(IBaseDeDatosDeAnunciantes.class);
		IBaseDeDatosDePagos bdPagos = mock(IBaseDeDatosDePagos.class);
		
		tab.publicarAnuncio(anuncio, bdAnunciantes, bdPagos);
		
		assertEquals(1, tab.anunciosPublicados());
		
	}
	@Test
	public void publicarUnAnuncioQueNoSeaEmpresaYQueInserte(){
		Anuncio anuncio = new Anuncio("anuncio nuevo", "primer anuncio", "OTRA EMPRESA");
		IBaseDeDatosDeAnunciantes bdAnunciantes = mock(IBaseDeDatosDeAnunciantes.class);
		IBaseDeDatosDePagos bdPagos = mock(IBaseDeDatosDePagos.class);
		
		when(bdAnunciantes.buscarAnunciante("OTRA EMPRESA")).thenReturn(true);
		when(bdPagos.anuncianteTieneSaldo("OTRA EMPRESA")).thenReturn(true);
		
		tab.publicarAnuncio(anuncio, bdAnunciantes, bdPagos);
		//assertEquals(2, tab.anunciosPublicados());
		
		verify(bdAnunciantes).buscarAnunciante("OTRA EMPRESA");
	}
	
}
