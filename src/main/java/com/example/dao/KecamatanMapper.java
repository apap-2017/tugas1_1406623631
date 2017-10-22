package com.example.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	@Select("select * from kecamatan where id = #{id}")
	KecamatanModel selectKecamatanById (@Param("id") BigInteger id);
	
	@Select("select * from kecamatan where id_kota = #{id_kota}")
	List<KecamatanModel> selectAllKecamatanByKotaId (@Param("id_kota") BigInteger id_kota);
}
