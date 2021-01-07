package com.yummyyum.Controllers;

import com.yummyyum.Model.DTO.CategoryDTO;
import com.yummyyum.Model.DTO.MenuCategoryDTO;
import com.yummyyum.Model.DTO.MenuDTO;
import com.yummyyum.Model.Meal;
import com.yummyyum.Model.MealCategory;
import com.yummyyum.Model.Menu;
import com.yummyyum.Repositories.MenuRepository;
import com.yummyyum.Services.Meal.MealService;
import com.yummyyum.Services.MealCategory.MealCategoryService;
import com.yummyyum.Services.Menu.MenuService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080")
public class MenuController {

    private final MenuService menuService;
    private final MealCategoryService mealCategoryService;
    private final MealService mealService;
    private final MenuRepository menuRepository;

    public MenuController(MenuService menuService, MealCategoryService mealCategoryService, MealService mealService, MenuRepository menuRepository) {
        this.menuService = menuService;
        this.mealCategoryService = mealCategoryService;
        this.mealService = mealService;
        this.menuRepository = menuRepository;
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/menus/menu-name/{menuname}")
    public MenuDTO getMenuByMenuName(@PathVariable("menuname") String menuName) {

        Optional<Menu> menu1 = menuService.getMenuByMenuName(menuName);

        List<Meal> allMeals = menu1.get().getMeals();

        String mealCategory;
        int n = menu1.get().getMealCategories().size();

        Meal meal;


        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuName(menu1.get().getMenuName());
        menuDTO.setReleaseDate(menu1.get().getReleaseDate());

        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        ArrayList<Meal>[] mealList1 = new ArrayList[n];

        // initializing
        for (int i = 0; i < n; i++) {
            mealList1[i] = new ArrayList<Meal>();
        }

        for (int i = 0; i < n; i++) {

            CategoryDTO categoryDTO = new CategoryDTO();
            mealCategory = menu1.get().getMealCategories().get(i).getCategory();
            categoryDTO.setCategory(mealCategory);

            for (Meal allMeal : allMeals) {
                meal = allMeal;
                if (allMeal.getMealCategory().getCategory().equals(mealCategory)) {
                    mealList1[i].add(meal);
                }
            }

            categoryDTO.setMeals(mealList1[i]);
            categoryDTOs.add(categoryDTO);

        }

        menuDTO.setMealCategories(categoryDTOs);

        return menuDTO;
    }

    @GetMapping("/menus/date/{releasedate}")
    public MenuDTO getMenuByReleaseDate(@PathVariable("releasedate")
                                        @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                LocalDate releaseDate) {
        Optional<Menu> menu1 = menuService.getMenuByReleaseDate(releaseDate);

        List<Meal> allMeals = menu1.get().getMeals();

        String mealCategory;
        int n = menu1.get().getMealCategories().size();

        Meal meal;


        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setMenuName(menu1.get().getMenuName());
        menuDTO.setReleaseDate(menu1.get().getReleaseDate());

        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        ArrayList<Meal>[] mealList1 = new ArrayList[n];

        // initializing
        for (int i = 0; i < n; i++) {
            mealList1[i] = new ArrayList<Meal>();
        }

        for (int i = 0; i < n; i++) {

            CategoryDTO categoryDTO = new CategoryDTO();
            mealCategory = menu1.get().getMealCategories().get(i).getCategory();
            categoryDTO.setCategory(mealCategory);

            for (Meal allMeal : allMeals) {
                meal = allMeal;
                if (allMeal.getMealCategory().getCategory().equals(mealCategory)) {
                    mealList1[i].add(meal);
                }
            }

            categoryDTO.setMeals(mealList1[i]);
            categoryDTOs.add(categoryDTO);

        }

        menuDTO.setMealCategories(categoryDTOs);

        return menuDTO;
    }

    @PostMapping("/menus")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public MenuDTO createNewMenu(@RequestBody Menu menu,
                                 HttpServletResponse response,
                                 UriComponentsBuilder builder) {

        Menu menu1 = menuService.createNewMenu(menu.getReleaseDate(), menu.getMenuName());

        List<MealCategory> mealCategories = mealCategoryService.getAllMealCategories();

        MenuDTO menuDTO = new MenuDTO();

        menuDTO.setMenuName(menu1.getMenuName());
        menuDTO.setReleaseDate(menu1.getReleaseDate());

        int i;
        int numberOfMealsToAdd = 9;
        int mealsArraySize;
        int randomInt;
        int n = mealCategories.size();

        Random random = new Random();

        String mealCategoryName;

        List<Meal> meals;
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        List<Meal> mealsDTO = new ArrayList<>();
        ArrayList<Meal>[] mealList = new ArrayList[n];

        // initializing
        for (i = 0; i < n; i++) {
            mealList[i] = new ArrayList<Meal>();
        }

        for (i = 0; i < mealCategories.size(); i++) {

            CategoryDTO categoryDTO = new CategoryDTO();

            mealCategoryName = mealCategories.get(i).getCategory();
            menu1.addMealCategory(mealCategories.get(i));
            categoryDTO.setCategory(menu1.getMealCategories().get(0).getCategory());

            meals = mealService.getMealsByMealCategory(mealCategoryName);

            for (int j = 0; j < numberOfMealsToAdd; j++) {

                mealsArraySize = meals.size();
                randomInt = random.nextInt(mealsArraySize);
                mealList[i].add(meals.get(randomInt));
                meals.remove(randomInt);

            }

            categoryDTO.setMeals(mealList[i]);
            categoryDTOs.add(categoryDTO);

        }

        for (i = 0; i < mealList.length; i++) {
            mealsDTO.addAll(mealList[i]);
        }

        menu1.setMeals(mealsDTO);

        menuDTO.setMealCategories(categoryDTOs);

        menuRepository.save(menu1);

        return menuDTO;
    }

    @PostMapping("/menu-mealcategory")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Menu processAddCategories(@RequestBody MenuCategoryDTO menuCategoryDTO,
                                     Errors errors,
                                     HttpServletResponse response,
                                     UriComponentsBuilder builder) {

        System.out.println(menuCategoryDTO.getMealCategory());
        System.out.println(menuCategoryDTO.getMenu());
        Menu menu = menuCategoryDTO.getMenu();
        Optional<MealCategory> mealCategory =
                mealCategoryService.findMealCategoryByCategory(
                        menuCategoryDTO.getMealCategory().getCategory());

        if (!(menu.getMealCategories().contains(mealCategory))) {
            menu.addMealCategory(mealCategory.get());
            menuRepository.save(menu);
        }


        return menu;

    }
}
