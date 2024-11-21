package com.ssafy.cafe.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cafe.model.dao.ShopDao;
import com.ssafy.cafe.model.dto.Shop;

@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	private ShopDao sDao;

	@Override
	public List<Shop> getshops() {
		return sDao.getShops();
	}

}
