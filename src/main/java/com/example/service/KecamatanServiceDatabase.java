package com.example.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KecamatanMapper;
import com.example.model.KecamatanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService {
	@Autowired
	private KecamatanMapper kecamatanMapper;
	
	public KecamatanModel selectKecamatanById (BigInteger id) {
		log.info("Select Kecamatan with ID {}", id);
		return kecamatanMapper.selectKecamatanById(id);
	}
	
	public List<KecamatanModel> selectAllKecamatanByKotaId (BigInteger id_kota) {
		log.info("Select All Kecamatan from Kota with ID {}", id_kota);
		return kecamatanMapper.selectAllKecamatanByKotaId(id_kota);
	}
}
