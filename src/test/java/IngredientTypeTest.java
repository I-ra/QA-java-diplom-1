import org.junit.Test;
import praktikum.IngredientType;

import static org.junit.Assert.assertNotNull;

public class IngredientTypeTest {

    @Test
    public void testEnumValues() {
        assertNotNull(IngredientType.SAUCE);
        assertNotNull(IngredientType.FILLING);
    }
}
