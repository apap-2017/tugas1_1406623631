package com.example.model;

import java.math.BigInteger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {
	@NotNull
	private BigInteger id;
	
	@NotNull
	@Size(min=16, max=16)
	private String nik;
	
	@NotNull
	@Size(min=1, max=128)
	private String nama;
	
	@NotNull
	@Size(min=1, max=128)
	private String tempat_lahir;
	
	@NotNull
	@Size(min=1, max=128)
	private String tanggal_lahir;
	
	@NotNull
	private int jenis_kelamin;
	
	@NotNull
	private int is_wni;
	
	@NotNull
	private BigInteger id_keluarga;
	
	@NotNull
	@Size(min=1, max=64)
	private String agama;
	
	@NotNull
	@Size(min=1, max=64)
	private String pekerjaan;
	
	@NotNull
	@Size(min=1, max=64)
	private String status_perkawinan;
	
	@NotNull
	@Size(min=1, max=64)
	private String status_dalam_keluarga;
	
	@NotNull
	@Size(min=1, max=32)
	private String golongan_darah;
	
	@NotNull
	private int is_wafat;
}
