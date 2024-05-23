package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.model.Category;
import com.example.OnlineFoodOrdering.model.Food;
import com.example.OnlineFoodOrdering.model.Restaurant;
import com.example.OnlineFoodOrdering.repository.FoodRepository;
import com.example.OnlineFoodOrdering.repository.IngredientsCategoryRepository;
import com.example.OnlineFoodOrdering.repository.RestaurantRepository;
import com.example.OnlineFoodOrdering.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;


    @Autowired
    private IngredientsService ingredientService;

    @Autowired
    private IngredientsCategoryRepository ingredientCategoryRepo;

    @Override
    public Food createFood(CreateFoodRequest req,
                           Category category,
                           Restaurant restaurant) {

        Food food = new Food();
        food.setFoodCategory(category);
        food.setCreationDate(new Date());
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setPrice(req.getPrice());
        food.setSeasonal(req.isSeasonal());
        food.setVegetarian(req.isVegetarian());
        food.setIngredients(req.getIngredients());
        food.setRestaurant(restaurant);
        food = foodRepository.save(food);

        restaurant.getFoods().add(food);
        return food;

    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food = findFoodById(foodId);
        food.setRestaurant(null);
//		foodRepository.save(food);
        foodRepository.delete(food);

    }

    @Override
    public List<Food> getRestaurantsFood(
            Long restaurantId,
            boolean isVegetarian,
            boolean isNonveg,
            boolean isSeasonal,
            String foodCategory) throws Exception {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);


        if (isVegetarian) {
            foods = filterByVegetarian(foods, isVegetarian);
        }
        if (isNonveg) {
            foods = filterByNonveg(foods, isNonveg);
        }

        if (isSeasonal) {
            foods = filterBySeasonal(foods, isSeasonal);
        }
        if (foodCategory != null && !foodCategory.equals("")) {
            foods = filterByFoodCategory(foods, foodCategory);
        }

        return foods;

    }

    private List<Food> filterByVegetarian(List<Food> foods, boolean isVegetarian) {
        return foods.stream()
                .filter(food -> food.isVegetarian() == isVegetarian)
                .collect(Collectors.toList());
    }

    private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
        return foods.stream()
                .filter(food -> food.isVegetarian() == false)
                .collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
        return foods.stream()
                .filter(food -> food.isSeasonal() == isSeasonal)
                .collect(Collectors.toList());
    }

    private List<Food> filterByFoodCategory(List<Food> foods, String foodCategory) {

        return foods.stream()
                .filter(food -> {
                    if (food.getFoodCategory() != null) {
                        return food.getFoodCategory().getName().equals(foodCategory);
                    }
                    return false; // Return true if food category is null
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        List<Food> items = new ArrayList<>();

        if (keyword != "") {
            System.out.println("keyword -- " + keyword);
            items = foodRepository.searchByNameOrCategory(keyword);
        }

        return items;
    }

    @Override
    public Food updateAvailibilityStatus(Long id) throws Exception {
        Food food = findFoodById(id);

        food.setAvailable(!food.isAvailable());
        foodRepository.save(food);
        return food;
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> food = foodRepository.findById(foodId);
        if (food.isPresent()) {
            return food.get();
        }
//        throw new FoodException("food with id" + foodId + "not found");
        return null;
    }
}
