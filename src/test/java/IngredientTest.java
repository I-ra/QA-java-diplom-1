import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

@RunWith(Parameterized.class)
public class IngredientTest {
    private final IngredientType type;
    private final String name;
    private final float price;

    public IngredientTest(IngredientType type, String name, float price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {IngredientType.SAUCE, "hot sauce", 100},
                {IngredientType.FILLING, "cutlet", 200},
                {IngredientType.SAUCE, "", 0},
                {IngredientType.FILLING, "special ingredient", Float.MAX_VALUE}
        };
    }

    @Test
    public void testGetType() {
        Ingredient ingredient = new Ingredient(type, name, price);
        assertEquals("Тип ингредиента должен совпадать", type, ingredient.getType());
    }

    @Test
    public void testGetName() {
        Ingredient ingredient = new Ingredient(type, name, price);
        assertEquals("Название ингредиента должно совпадать", name, ingredient.getName());
    }

    @Test
    public void testGetPrice() {
        Ingredient ingredient = new Ingredient(type, name, price);
        assertEquals("Цена ингредиента должна совпадать", price, ingredient.getPrice(), 0.001f);
    }

    @Test
    public void testConstructorInitializesFields() {
        Ingredient ingredient = new Ingredient(type, name, price);

        assertSame("Тип должен быть установлен через конструктор", type, ingredient.type);
        assertEquals("Название должно быть установлено через конструктор", name, ingredient.name);
        assertEquals("Цена должна быть установлена через конструктор", price, ingredient.price, 0.001f);
    }

    @Test
    public void testMinimumPrice() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "free sauce", 0);
        assertEquals(0, ingredient.getPrice(), 0);
    }

    @Test
    public void testMaximumPrice() {
        Ingredient ingredient = new Ingredient(IngredientType.FILLING, "golden cutlet", Float.MAX_VALUE);
        assertEquals(Float.MAX_VALUE, ingredient.getPrice(), 0);
    }
}
