package es.msanchez.poker.server.abstraction;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;

/**
 * @author msanchez
 * @since 23.03.2019
 */
public class PokerTest {

    /**
     * As I use TestNG, I cannot use the tag {@link org.junit.runner.RunWith}
     * so easily to auto inject Mocks.
     */
    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

}