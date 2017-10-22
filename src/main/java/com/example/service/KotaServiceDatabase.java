package com.example.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KotaMapper;
import com.example.model.KotaModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KotaServiceDatabase implements KotaService {
	@Autowired
	private KotaMapper kotaMapper;
	
	public KotaModel selectKotaById (BigInteger id) {
		log.info("Select Kota with ID {}", id);
		return kotaMapper.selectKotaById(id);
	}
	
	public List<KotaModel> selectAllKota () {
		log.info("Select All Kota");
		return kotaMapper.selectAllKota();
	}
}
