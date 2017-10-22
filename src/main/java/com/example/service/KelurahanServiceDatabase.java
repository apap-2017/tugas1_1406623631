package com.example.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KelurahanMapper;
import com.example.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KelurahanServiceDatabase implements KelurahanService {
	@Autowired
	private KelurahanMapper kelurahanMapper;
	
	public KelurahanModel selectKelurahanById (BigInteger id) {
		log.info("Select Kelurahan with ID {}", id);
		return kelurahanMapper.selectKelurahanById(id);
	}
	
	public List<KelurahanModel> selectAllKelurahan () {
		log.info("Select All Kelurahan");
		return kelurahanMapper.selectAllKelurahan();
	}
	
	public List<KelurahanModel> selectAllKelurahanByKecamatanId (BigInteger id_kecamatan) {
		log.info("Select All Kelurahan from Kecamatan with ID {}", id_kecamatan);
		return kelurahanMapper.selectAllKelurahanByKecamatanId(id_kecamatan);
	}
}