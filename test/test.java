import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
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
	public void crearUnAnuncioHayUnAnuncio(){
		assertEquals(1, tab.anunciosPublicados());
	}

}
