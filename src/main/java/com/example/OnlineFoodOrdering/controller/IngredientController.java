package com.example.OnlineFoodOrdering.controller;


import com.example.OnlineFoodOrdering.model.IngredientCategory;
import com.example.OnlineFoodOrdering.model.IngredientsItem;
import com.example.OnlineFoodOrdering.request.IngredientCategoryRequest;
import com.example.OnlineFoodOrdering.request.IngredientRequest;
import com.example.OnlineFoodOrdering.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req) throws Exception {

        IngredientCategory item = ingredientsService.createIngredientsCategory(req.getName(),req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);

    }

    @PostMapping("/")
    public ResponseEntity<IngredientsItem> createIngredientItem(
            @RequestBody IngredientRequest req) throws Exception {

        IngredientsItem item = ingredientsService.createIngredientsItem(req.getRestaurantId(), req.getName(),req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);

    }

    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItem> updateIngredientStoke(
            @PathVariable Long id) throws Exception {

        IngredientsItem item = ingredientsService.updateStoke(id);
        return new ResponseEntity<>(item, HttpStatus.CREATED);

    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(@PathVariable Long id) throws Exception {
        List<IngredientCategory> items= ingredientsService.findIngredientsCategoryByRestaurantId(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }



}
