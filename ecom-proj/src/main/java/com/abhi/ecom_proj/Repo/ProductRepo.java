package com.abhi.ecom_proj.Repo;

import com.abhi.ecom_proj.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>{
}
