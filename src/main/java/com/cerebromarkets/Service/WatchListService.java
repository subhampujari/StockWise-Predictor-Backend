package com.cerebromarkets.Service;

import com.cerebromarkets.modal.Coin;
import com.cerebromarkets.modal.User;
import com.cerebromarkets.modal.WatchList;

public interface WatchListService {

    WatchList findUserWatchlist(Long userId) throws Exception;
    WatchList createWatchList(User user);
    WatchList findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin,User user) throws Exception;
}
