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

import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	@Select("SELECT * FROM penduduk WHERE nik = #{nik}")
	PendudukModel selectPenduduk (@Param("nik") String nik);

	@Select("SELECT * FROM penduduk WHERE id_keluarga = #{id_keluarga}")
	List<PendudukModel> selectPendudukFromKeluarga (@Param("id_keluarga") BigInteger id_keluarga);
	
	@Select("SELECT COUNT(id) FROM penduduk")
	BigInteger countAllPenduduk();
	
	@Select("SELECT COUNT(nik) FROM penduduk WHERE nik LIKE #{query}")
	int countCurrentPendudukOnQuery(String query);

    @Insert("INSERT INTO penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, "
    		+ "id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, "
    		+ "golongan_darah, is_wafat) VALUES (#{id}, #{nik}, #{nama}, #{tempat_lahir}, "
    		+ "#{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, "
    		+ "#{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
    void addPenduduk (PendudukModel penduduk);

    @Update("UPDATE penduduk SET nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, "
    		+ "tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, "
    		+ "id_keluarga = #{id_keluarga}, agama = #{agama}, pekerjaan = #{pekerjaan}, "
    		+ "status_perkawinan = #{status_perkawinan}, status_dalam_keluarga = #{status_dalam_keluarga}, "
    		+ "golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat} WHERE id = #{id}")
    void updatePenduduk (PendudukModel penduduk);

    @Update("UPDATE penduduk SET is_wafat = 1 WHERE id = #{id}")
    void setPendudukWafat (@Param("id") BigInteger id);
    
    
}