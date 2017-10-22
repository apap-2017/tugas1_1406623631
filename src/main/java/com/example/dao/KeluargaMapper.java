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

import com.example.model.KeluargaModel;

@Mapper
public interface KeluargaMapper {
	@Select("select * from keluarga where nomor_kk = #{nkk}")
	KeluargaModel selectKeluarga (@Param("nkk") String nkk);

	@Select("select * from keluarga where id = #{id}")
	KeluargaModel selectKeluargaById (@Param("id") BigInteger id);
	
	@Select("select * from keluarga where id_kelurahan = #{id_kelurahan}")
	List<KeluargaModel> selectAllKeluargaByKelurahanId (@Param("id_kelurahan") BigInteger id_kelurahan);
	
	@Select("SELECT COUNT(id) FROM keluarga")
	BigInteger countAllKeluarga();
	
	@Select("SELECT COUNT(nomor_kk) FROM keluarga WHERE nomor_kk LIKE #{query}")
	int countCurrentKeluargaOnQuery(String query);

    @Insert("INSERT INTO keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) VALUES (#{id}, #{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
    void addKeluarga (KeluargaModel keluarga);

    @Update("UPDATE keluarga SET nomor_kk = #{nomor_kk}, alamat = #{alamat}, rt = #{rt}, rw = #{rw}, id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} WHERE id = #{id}")
    void updateKeluarga (KeluargaModel keluarga);

    @Update("UPDATE keluarga SET is_tidak_berlaku = 0 WHERE id = #{id}")
    void setKeluargaActive (@Param("id") BigInteger id);

    @Update("UPDATE keluarga SET is_tidak_berlaku = 1 WHERE id = #{id}")
    void setKeluargaInactive (@Param("id") BigInteger id);
}
