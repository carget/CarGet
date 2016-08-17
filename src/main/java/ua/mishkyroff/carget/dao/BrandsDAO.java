package ua.mishkyroff.carget.dao;

import ua.mishkyroff.carget.entities.Brand;

import java.util.List;

/**
 * Interface for general operation with brands table.
 *
 * @author Anton Mishkyroff
 */
public interface BrandsDAO {
    List<Brand> getAllBrands();
}
