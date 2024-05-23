package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.model.IngredientCategory;
import com.example.OnlineFoodOrdering.model.IngredientsItem;
import com.example.OnlineFoodOrdering.model.Restaurant;
import com.example.OnlineFoodOrdering.repository.IngredientsCategoryRepository;
import com.example.OnlineFoodOrdering.repository.IngredientsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientsServiceImp implements IngredientsService{
    @Autowired
    private IngredientsCategoryRepository ingredientsCategoryRepo;

    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public IngredientCategory createIngredientsCategory(String name,Long restaurantId) throws Exception {

//        IngredientCategory isExist=ingredientsCategoryRepo
//                .findByRestaurantIdAndNameIgnoreCase(restaurantId,name);
//
//        if(isExist!=null) {
//            return isExist;
//        }
//
//        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
//
//        IngredientCategory ingredientCategory=new IngredientCategory();
//        ingredientCategory.setRestaurant(restaurant);
//        ingredientCategory.setName(name);
//
//        IngredientCategory createdCategory = ingredientsCategoryRepo.save(ingredientCategory);
//
//        return createdCategory;
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory category = new IngredientCategory();
        category.setRestaurant(restaurant);
        category.setName(name);

        return ingredientsCategoryRepo.save(category);
    }

    @Override
    public IngredientCategory findIngredientsCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> opt=ingredientsCategoryRepo.findById(id);
        if(opt.isEmpty()){
            throw new Exception("ingredient category not found");
        }
        return opt.get();
    }

    @Override
    public List<IngredientCategory> findIngredientsCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientsCategoryRepo.findByRestaurantId(id);
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {

        return ingredientsItemRepository.findByRestaurantId(restaurantId);
    }


    @Override
    public IngredientsItem createIngredientsItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {

//        IngredientCategory category = findIngredientsCategoryById(ingredientCategoryId);
//
//        IngredientsItem isExist = ingredientsItemRepository.
//                findByRestaurantIdAndNameIngoreCase(restaurantId, ingredientName,category.getName());
//        if(isExist!=null) {
//            System.out.println("is exists-------- item");
//            return isExist;
//        }
//
//        Restaurant restaurant=restaurantService.findRestaurantById(
//                restaurantId);
//        IngredientsItem item=new IngredientsItem();
//        item.setName(ingredientName);
//        item.setRestaurant(restaurant);
//        item.setCategory(category);
//
//        IngredientsItem savedIngredients = ingredientsItemRepository.save(item);
//        category.getIngredients().add(savedIngredients);
//
//        return savedIngredients;
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category = findIngredientsCategoryById(categoryId);

        IngredientsItem item = new IngredientsItem();
        item.setName(ingredientName);
        item.setRestaurant(restaurant);
        item.setCategory(category);

        IngredientsItem ingredents = ingredientsItemRepository.save(item);
        category.getIngredients().add(ingredents);

        return ingredents;
    }


    @Override
    public IngredientsItem updateStoke(Long id) throws Exception {
        Optional<IngredientsItem> item=ingredientsItemRepository.findById(id);
        if(item.isEmpty()) {
            throw new Exception("ingredient not found with id "+item);
        }
        IngredientsItem ingredient=item.get();
        ingredient.setInStoke(!ingredient.isInStoke());
        return ingredientsItemRepository.save(ingredient);
    }


}
