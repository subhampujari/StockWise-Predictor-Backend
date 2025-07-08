package com.cerebromarkets.Service;

import com.cerebromarkets.Repository.WatchListRepository;
import com.cerebromarkets.modal.Coin;
import com.cerebromarkets.modal.User;
import com.cerebromarkets.modal.WatchList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServiceImpl  implements WatchListService{

    @Autowired
    private WatchListRepository watchListRepository;

    @Override
    public WatchList findUserWatchlist(Long userId) throws Exception {
        WatchList watchList=watchListRepository.findByUserId(userId);
        if(watchList == null){
            throw new Exception("Watch list not found");
        }
        return watchList;
    }

    @Override
    public WatchList createWatchList(User user) {
        WatchList watchList=new WatchList();
        watchList.setUser(user);
        return watchListRepository.save(watchList);
    }

    @Override
    public WatchList findById(Long id) throws Exception {
        Optional<WatchList> watchListOptional=watchListRepository.findById(id);
        if(watchListOptional.isEmpty()){
            throw new Exception("Watch List not found");
        }
        return watchListOptional.get();
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
        WatchList watchList=findUserWatchlist(user.getId());
        if(watchList.getCoins().contains(coin)){
            watchList.getCoins().remove(coin);
        }
        else watchList.getCoins().add(coin);
        watchListRepository.save(watchList);
    return  coin;
    }
}
