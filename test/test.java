import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import junio2013.AnuncianteNoExisteException;
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
		
		when(bdAnunciantes.buscarAnunciante("OTRA EMPRESA")).thenReturn(true);
		when(bdPagos.anuncianteTieneSaldo("OTRA EMPRESA")).thenReturn(false);
		tab.publicarAnuncio(anuncio, bdAnunciantes, bdPagos);
		
		assertEquals(1, tab.anunciosPublicados());
		
	}
	@Test
	public void publicarUnAnuncioQueNoSeaEmpresaYQueInserte(){
		Anuncio anuncio = new Anuncio("anuncio nuevo", "primer anuncio", "OTRA EMPRESA");
		IBaseDeDatosDeAnunciantes bdAnunciantes = mock(IBaseDeDatosDeAnunciantes.class);
		IBaseDeDatosDePagos bdPagos = mock(IBaseDeDatosDePagos.class);
		
		when(bdAnunciantes.buscarAnunciante(anuncio.anunciante_)).thenReturn(true);
		when(bdPagos.anuncianteTieneSaldo(anuncio.anunciante_)).thenReturn(true);
		
		tab.publicarAnuncio(anuncio, bdAnunciantes, bdPagos);
		assertEquals(2, tab.anunciosPublicados());
		
		
	}
	@Test
	public void publicarAnuncioDeEmpresaYComrobarQueEsta(){
		Anuncio anuncio1 = new Anuncio("anuncio 2", "segundo anuncio", "LA EMPRESA");
		Anuncio anuncio2 = new Anuncio("anuncio 3", "tercer anuncio", "LA EMPRESA");
		
		IBaseDeDatosDeAnunciantes bdAnunciantes = mock(IBaseDeDatosDeAnunciantes.class);
		IBaseDeDatosDePagos bdPagos = mock(IBaseDeDatosDePagos.class);
		
		tab.publicarAnuncio(anuncio1, bdAnunciantes, bdPagos);
		tab.publicarAnuncio(anuncio2, bdAnunciantes, bdPagos);
		
		assertEquals(anuncio2, tab.buscarAnuncioPorTitulo(anuncio2.titulo_));
		assertEquals(3, tab.anunciosPublicados());

	}
	@Test
	public void publicarAnunciosEmpresaBorrar1YComprobarQueNoEsta(){
		Anuncio anuncio1 = new Anuncio("anuncio 2", "segundo anuncio", "LA EMPRESA");
		Anuncio anuncio2 = new Anuncio("anuncio 3", "tercer anuncio", "LA EMPRESA");
		
		IBaseDeDatosDeAnunciantes bdAnunciantes = mock(IBaseDeDatosDeAnunciantes.class);
		IBaseDeDatosDePagos bdPagos = mock(IBaseDeDatosDePagos.class);
		
		tab.publicarAnuncio(anuncio1, bdAnunciantes, bdPagos);
		tab.publicarAnuncio(anuncio2, bdAnunciantes, bdPagos);
		
		tab.borrarAnuncio(anuncio1.titulo_, "LA EMPRESA");
		assertEquals(null, tab.buscarAnuncioPorTitulo(anuncio1.titulo_));
		assertEquals(2, tab.anunciosPublicados());
	}
	
	@Test
	public void publicarAnuncioQueYaExisteNoSeInserta(){
		Anuncio anuncio1 = new Anuncio("anuncio 2", "segundo anuncio", "LA EMPRESA");
		Anuncio anuncio2 = new Anuncio("anuncio 2", "tercer anuncio", "LA EMPRESA");
		
		IBaseDeDatosDeAnunciantes bdAnunciantes = mock(IBaseDeDatosDeAnunciantes.class);
		IBaseDeDatosDePagos bdPagos = mock(IBaseDeDatosDePagos.class);
		
		tab.publicarAnuncio(anuncio1, bdAnunciantes, bdPagos);
		assertEquals(2, tab.anunciosPublicados());
		tab.publicarAnuncio(anuncio2, bdAnunciantes, bdPagos);
		assertEquals(2, tab.anunciosPublicados());
		
	}
	@Test(expected = AnuncianteNoExisteException.class)
	public void publicarAnuncioAnuncianteNoExisteDevuelveExcepcion(){
		Anuncio anuncio1 = new Anuncio("anuncio 2", "segundo anuncio", "OTRA EMPRESA");
		IBaseDeDatosDeAnunciantes bdAnunciantes = mock(IBaseDeDatosDeAnunciantes.class);
		IBaseDeDatosDePagos bdPagos = mock(IBaseDeDatosDePagos.class);
		
		when(bdAnunciantes.buscarAnunciante(anuncio1.anunciante_)).thenReturn(false);
		when(bdPagos.anuncianteTieneSaldo(anuncio1.anunciante_)).thenReturn(false);
		
		tab.publicarAnuncio(anuncio1, bdAnunciantes, bdPagos);
	}
}
