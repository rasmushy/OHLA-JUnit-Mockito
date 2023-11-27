package hinnoittelu;

import mockesimerkki.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TilaustenKäsittelyTest {
    @Mock
    IHinnoittelija hinnoittelijaMock;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    /*
        @Test
        public void testaaKäsittelijäWithMockitoHinnoittelija() {
            // Arrange
            float alkuSaldo = 100.0f;
            float listaHinta = 30.0f;
            float alennus = 20.0f;
            float loppuSaldo = alkuSaldo - (listaHinta * (1 - alennus / 100));
            Asiakas asiakas = new Asiakas(alkuSaldo);
            Tuote tuote = new Tuote("TDD in Action", listaHinta);
            // Record
            when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
                    .thenReturn(alennus);
            // Act
            TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
            käsittelijä.setHinnoittelija(hinnoittelijaMock);
            käsittelijä.käsittele(new Tilaus(asiakas, tuote));
            // Assert
            assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
            verify(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);
        }
    */
    @Test
    public void testaaKäsittelijäGreaterThanHundredWithMockitoHinnoittelija() {
        // Arrange
        float alkuSaldo = 150.0f;
        float listaHinta = 150.0f;
        float alennus = 20.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - (alennus + 5f) / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);
        // Record
        doNothing().when(hinnoittelijaMock).aloita();
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
                .thenReturn(alennus);
        doNothing().when(hinnoittelijaMock).setAlennusProsentti(any(Asiakas.class), anyFloat());
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
                .thenReturn(alennus + 5);
        doNothing().when(hinnoittelijaMock).lopeta();
        // Act
        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija(hinnoittelijaMock);
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));
        // Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        InOrder inOrder = inOrder(hinnoittelijaMock);
        inOrder.verify(hinnoittelijaMock).aloita();
        inOrder.verify(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);
        inOrder.verify(hinnoittelijaMock).setAlennusProsentti(any(Asiakas.class), anyFloat());
        inOrder.verify(hinnoittelijaMock).getAlennusProsentti(asiakas, tuote);
        inOrder.verify(hinnoittelijaMock).lopeta();
    }

    @Test
    public void testaaKäsittelijäLessThanHundredWithMockitoHinnoittelija() {
        // Arrange
        float alkuSaldo = 100.0f;
        float listaHinta = 90.0f;
        float alennus = 20.0f;
        float loppuSaldo = alkuSaldo - (listaHinta * (1 - (alennus) / 100));
        Asiakas asiakas = new Asiakas(alkuSaldo);
        Tuote tuote = new Tuote("TDD in Action", listaHinta);
        // Record
        doNothing().when(hinnoittelijaMock).aloita();
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
                .thenReturn(alennus);
        when(hinnoittelijaMock.getAlennusProsentti(asiakas, tuote))
                .thenReturn(alennus);
        doNothing().when(hinnoittelijaMock).lopeta();
        // Act
        TilaustenKäsittely käsittelijä = new TilaustenKäsittely();
        käsittelijä.setHinnoittelija(hinnoittelijaMock);
        käsittelijä.käsittele(new Tilaus(asiakas, tuote));
        // Assert
        assertEquals(loppuSaldo, asiakas.getSaldo(), 0.001);
        InOrder inOrder = inOrder(hinnoittelijaMock);
        inOrder.verify(hinnoittelijaMock).aloita();
        inOrder.verify(hinnoittelijaMock, times(2)).getAlennusProsentti(asiakas, tuote);
        inOrder.verify(hinnoittelijaMock).lopeta();
    }
}
