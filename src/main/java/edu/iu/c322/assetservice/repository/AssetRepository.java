package edu.iu.c322.assetservice.repository;

import edu.iu.c322.assetservice.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
}
