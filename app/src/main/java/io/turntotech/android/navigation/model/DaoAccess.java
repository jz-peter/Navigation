package io.turntotech.android.navigation.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.turntotech.android.navigation.model.entity.Company;
import io.turntotech.android.navigation.model.entity.Product;

@Dao
public interface DaoAccess {

    @Insert
    void insertCompany(Company company);
    void insertProduct(Product product);

    @Update
    void updateCompany(Company company);
    void updateProduct(Product product);

    @Delete
    void deleteCompany(Company company);
    void deleteProduct(Product product);


    @Query("SELECT * FROM Company")
    LiveData<List<Company>> fetchAllCompanies();

    @Query ("SELECT * FROM Product")
    LiveData<List<Product>> fetchAllProducts();
}


