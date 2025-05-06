package com.fiveup.core.miniapp.service;

import java.util.List;

/**
 * @author shilin
 * @date 2022/10/3
 */
public interface SportItemService {

    List<String> getItemList();

    String getClassLimit(String itemName);
}
