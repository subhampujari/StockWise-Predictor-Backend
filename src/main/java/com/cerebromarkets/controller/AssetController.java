package com.cerebromarkets.controller;

import com.cerebromarkets.Service.AssetService;
import com.cerebromarkets.Service.UserService;
import com.cerebromarkets.modal.Asset;
import com.cerebromarkets.modal.User;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;

    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception{
        Asset asset=assetService.getAssetById(assetId);
        return ResponseEntity.ok().body(asset);
    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset>getAssetByUserIdAndCoinId(@PathVariable String coinId,@RequestHeader("Authorization") String jwt) throws  Exception{
        User user =userService.findUserProfileByJwt(jwt);
        Asset asset=assetService.findAssetByUserIdAndCoinId(user.getId(),coinId);
        return ResponseEntity.ok().body(asset);
    }
@GetMapping()
    public ResponseEntity<List<Asset>> getAssetsForUser(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Asset> assets=assetService.getUsersAssets(user.getId());
        return ResponseEntity.ok().body(assets);
    }
}
