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
    @Update
    void updateCompany(Company company);
    @Delete
    void deleteCompany(Company company);
    @Query("SELECT * FROM Company")
    LiveData<List<Company>> fetchAllCompanies();

    @Insert
    void insertProduct(Product product);
    @Update
    void updateProduct(Product product);
    @Delete
    void deleteProduct(Product product);

    @Query ("SELECT * FROM Product")
    LiveData<List<Product>> fetchAllProducts();

    //Fetch all products belonging to a company
    @Query("SELECT* FROM Product WHERE companyId IS :companyId")
    LiveData<List<Product>> fetchAllCompanyProducts(int companyId);
}


