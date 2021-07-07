package com.bdo.redis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bdo.redis.entity.Product;
import com.bdo.redis.repository.ProductDao;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductDao dao;
	
	@PostMapping
	public Product save(@RequestBody Product product) {
		return dao.save(product);
	}
	
	@GetMapping
	public List<Product> getAllProducts(){
		return dao.findAll();
	}
	
	@GetMapping("/{id}")
	@Cacheable(key = "#id" , value="Product")
	public Product getProductById(@PathVariable int id) {
		System.out.println("getProductById() Called From DB");
		return dao.findProductById(id);
	}
	
	@PutMapping()
	@CachePut(key = "#result.id", value="Product")
	public Product updateProductById(@RequestBody Product product) {
		System.out.println("updateProductById() Called From DB");
		return dao.update(product);
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(key = "#id", value = "Product")
	public String remove(@PathVariable int id) {
		System.out.println("deleteById() Called From DB");
		return dao.deleteProduct(id);
	}
	
}
