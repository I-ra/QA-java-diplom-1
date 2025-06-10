import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class BurgerTest {
    private Burger burger;
    @Mock
    private Bun bun;
    @Mock
    private Ingredient ingredient1;
    @Mock
    private Ingredient ingredient2;
    @Mock
    private Ingredient ingredient3;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();
    }
    @Parameterized.Parameters
    public static Object[][] getBunAndIngredientPrices() {
        return new Object[][] {
                {100, 50, 30, 280},
                {0, 10, 20, 30},
                {150.5f, 0, 0, 301}
        };
    }

    @Parameterized.Parameter(0)
    public float bunPrice;

    @Parameterized.Parameter(1)
    public float ingredient1Price;

    @Parameterized.Parameter(2)
    public float ingredient2Price;

    @Parameterized.Parameter(3)
    public float expectedTotalPrice;

    @Test
    public void testGetPrice() {
        when(bun.getPrice()).thenReturn(bunPrice);
        when(ingredient1.getPrice()).thenReturn(ingredient1Price);
        when(ingredient2.getPrice()).thenReturn(ingredient2Price);

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        assertEquals("Неверная общая стоимость бургера", expectedTotalPrice, burger.getPrice(), 0.001f);
    }

    @Test
    public void testSetBuns() {
        burger.setBuns(bun);
        assertSame("Булочка не установилась", bun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        burger.addIngredient(ingredient1);
        assertEquals("Ингредиент не добавился", 1, burger.ingredients.size());
        assertSame("Добавленный ингредиент не совпадает", ingredient1, burger.ingredients.get(0));
    }

    @Test
    public void testRemoveIngredient() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.removeIngredient(0);
        assertEquals("Ингредиент не удалился", 1, burger.ingredients.size());
        assertSame("Оставшийся ингредиент не совпадает", ingredient2, burger.ingredients.get(0));
    }

    @Test
    public void testMoveIngredient() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.addIngredient(ingredient3);

        burger.moveIngredient(1, 0);

        List<Ingredient> expectedOrder = Arrays.asList(ingredient2, ingredient1, ingredient3);
        assertEquals("Ингредиенты переместились неверно", expectedOrder, burger.ingredients);
    }

    @Test
    public void testGetReceipt() {
        when(bun.getName()).thenReturn("black bun");
        when(ingredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredient1.getName()).thenReturn("chili sauce");
        when(ingredient2.getType()).thenReturn(IngredientType.FILLING);
        when(ingredient2.getName()).thenReturn("cheese");
        when(bun.getPrice()).thenReturn(100f);
        when(ingredient1.getPrice()).thenReturn(50f);
        when(ingredient2.getPrice()).thenReturn(30f);

        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        String expectedReceipt =
                "(==== black bun ====)\n" +
                        "= sauce chili sauce =\n" +
                        "= filling cheese =\n" +
                        "(==== black bun ====)\n" +
                        "\nPrice: 280.000000\n";

        String actualReceipt = burger.getReceipt().replace(",", ".");

        assertEquals(
                "Чек сформирован неверно",
                expectedReceipt.replace("\r\n", "\n"),
                actualReceipt.replace("\r\n", "\n")
        );
    }
}
