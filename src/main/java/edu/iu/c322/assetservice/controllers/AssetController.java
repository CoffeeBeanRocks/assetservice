package edu.iu.c322.assetservice.controllers;

import edu.iu.c322.assetservice.client.LicenseClient;
import edu.iu.c322.assetservice.models.Asset;
import edu.iu.c322.assetservice.models.License;
import edu.iu.c322.assetservice.repository.AssetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/assets")
public class AssetController {

    private AssetRepository repository;
    private LicenseClient licenseClient;

    public AssetController(AssetRepository repository, LicenseClient licenseClient) {
        this.repository = repository;
        this.licenseClient = licenseClient;
    }

    @GetMapping
    public List<Asset> getAssets(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Asset getAsset(@PathVariable int id){
        Optional<Asset> maybeAsset = repository.findById(id);
        if(maybeAsset.isPresent()){
            Asset asset = maybeAsset.get();
            Optional<License> maybeLicense = licenseClient
                    .getOrganization(asset.getLicenseId());
            if(maybeLicense.isPresent()){
                License license = maybeLicense.get();
                asset.setLicense(license);
                return asset;
            }
        } else {
            throw new IllegalStateException("licensing id is invalid.");
        }
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public int create(@RequestBody Asset asset){
        Asset addedAsset = repository.save(asset);
        return addedAsset.getId();
    }

}
