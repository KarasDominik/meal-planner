package karas.dominik.meal_planner.meal.infrastructure.externalapi.themealdb;

import karas.dominik.meal_planner.meal.domain.dto.RecipeDto.Ingredient;

import java.util.List;

public class RecipesForTests {

    public static final class SPICY_ARABIATTA_PASTA {
        public static final Long ID = 52772L;
        public static final String NAME = "Teriyaki Chicken Casserole";
        public static final String DESCRIPTION = "Preheat oven to 350° F. Spray a 9x13-inch baking pan with non-stick spray.\\r\\nCombine soy sauce, ½ cup water, brown sugar, ginger and garlic in a small saucepan and cover. Bring to a boil over medium heat. Remove lid and cook for one minute once boiling.\\r\\nMeanwhile, stir together the corn starch and 2 tablespoons of water in a separate dish until smooth. Once sauce is boiling, add mixture to the saucepan and stir to combine. Cook until the sauce starts to thicken then remove from heat.\\r\\nPlace the chicken breasts in the prepared pan. Pour one cup of the sauce over top of chicken. Place chicken in oven and bake 35 minutes or until cooked through. Remove from oven and shred chicken in the dish using two forks.\\r\\n*Meanwhile, steam or cook the vegetables according to package directions.\\r\\nAdd the cooked vegetables and rice to the casserole dish with the chicken. Add most of the remaining sauce, reserving a bit to drizzle over the top when serving. Gently toss everything together in the casserole dish until combined. Return to oven and cook 15 minutes. Remove from oven and let stand 5 minutes before serving. Drizzle each serving with remaining sauce. Enjoy!";
        public static final List<Ingredient> INGREDIENTS = List.of(
                new Ingredient("soy sauce", "3/4 cup"),
                new Ingredient("water", "1/2 cup"),
                new Ingredient("brown sugar", "1/4 cup"),
                new Ingredient("ground ginger", "1/2 teaspoon"),
                new Ingredient("minced garlic", "1/2 teaspoon"),
                new Ingredient("cornstarch", "4 Tablespoons"),
                new Ingredient("chicken breasts", "2"),
                new Ingredient("stir-fry vegetables", "1 (12 oz.)"),
                new Ingredient("brown rice", "3 cups")
        );
    }

    public static final class CREAMY_MUSHROOM_PASTA {
        public static final Long ID = 52773L;
        public static final String NAME = "Creamy Mushroom Fettuccine";
        public static final String DESCRIPTION = """
            Bring a large pot of salted water to a boil and cook the fettuccine according to the package instructions, about 10 minutes.
            Meanwhile, in a large skillet over medium heat, melt the butter and add the olive oil. Add the sliced mushrooms and cook, stirring occasionally, until golden and softened, about 5-7 minutes.
            Stir in the garlic and cook for another minute until fragrant. Pour in the heavy cream and bring to a simmer, cooking for 5 minutes until slightly thickened. Season with salt, pepper, and a pinch of nutmeg.
            Drain the fettuccine and toss it in the creamy mushroom sauce. Remove from heat and stir in the grated Parmesan cheese and chopped parsley. Serve hot and garnish with more Parmesan if desired.
            """;
        public static final List<Ingredient> INGREDIENTS = List.of(
                new Ingredient("fettuccine", "12 ounces"),
                new Ingredient("olive oil", "1 tablespoon"),
                new Ingredient("butter", "2 tablespoons"),
                new Ingredient("mushrooms", "10 ounces, sliced"),
                new Ingredient("garlic", "2 cloves, minced"),
                new Ingredient("heavy cream", "1 cup"),
                new Ingredient("salt", "1 pinch"),
                new Ingredient("black pepper", "1 pinch"),
                new Ingredient("nutmeg", "1 pinch"),
                new Ingredient("Parmesan cheese", "1/2 cup, grated"),
                new Ingredient("fresh parsley", "2 tablespoons, chopped")
        );
    }
}
