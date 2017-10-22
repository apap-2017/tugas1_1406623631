package com.example.controller;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.KecamatanService;
import com.example.service.KeluargaService;
import com.example.service.KelurahanService;
import com.example.service.KotaService;
import com.example.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageController {
    @Autowired
    KecamatanService kecamatanService;

    @Autowired
    KeluargaService keluargaService;

    @Autowired
    KelurahanService kelurahanService;

    @Autowired
    KotaService kotaService;

    @Autowired
    PendudukService pendudukService;

    @RequestMapping("/")
	public String index () {
		return "index";
	}

    @RequestMapping("/penduduk")
	public String penduduk (Model model, @RequestParam(value = "nik", required = false) String nik) {
    	if (nik != null) {
    		PendudukModel penduduk = pendudukService.selectPenduduk(nik);
    		log.info("Penduduk {}", penduduk);
    		model.addAttribute("penduduk", penduduk);
    		
    		KeluargaModel keluarga = keluargaService.selectKeluargaById(penduduk.getId_keluarga());
    		model.addAttribute("keluarga", keluarga);
    		
    		KelurahanModel kelurahan = kelurahanService.selectKelurahanById(keluarga.getId_kelurahan());
    		model.addAttribute("kelurahan", kelurahan);
    		
    		KecamatanModel kecamatan = kecamatanService.selectKecamatanById(kelurahan.getId_kecamatan());
    		model.addAttribute("kecamatan", kecamatan);
    		
    		KotaModel kota = kotaService.selectKotaById(kecamatan.getId_kota());
    		model.addAttribute("kota", kota);
    		
    		return "view-penduduk";
    	}

		return "penduduk";
	}
    
    @RequestMapping(value = "/penduduk/", method = RequestMethod.POST)
	public String setPendudukWafat (Model model, @Valid @ModelAttribute PendudukModel penduduk, BindingResult result) {
    	log.info("deleting penduduk {}", penduduk);
    	penduduk.setIs_wafat(1);

		KeluargaModel keluarga = keluargaService.selectKeluargaById(penduduk.getId_keluarga());
		log.info("Keluarga {}", keluarga);
		
		List<PendudukModel> anggota = pendudukService.selectPendudukFromKeluarga(keluarga.getId());
		log.info("Anggota {}", anggota);
		
		boolean sudahMeninggal = true;
		
		for (int i = 0; i < anggota.size(); i++) {
			log.info("current anggota {}", anggota.get(i));
			if (anggota.get(i).getIs_wafat() == 0 && anggota.get(i).getId().compareTo(penduduk.getId()) != 0) {
				
				sudahMeninggal = false;
				
			}
		}
		
		if (sudahMeninggal) {
			log.info("All dead");
			keluarga.setIs_tidak_berlaku(1);
			keluargaService.updateKeluarga(keluarga);
			
		}
    	
    	pendudukService.updatePenduduk(penduduk);

		model.addAttribute("penduduk", penduduk);
		return "sukses-nonaktifkan-penduduk";
	}
    
    @RequestMapping(value = "/penduduk/tambah", method = RequestMethod.GET)
	public String tambahPenduduk (Model model) {
    	List<KotaModel> listKota = kotaService.selectAllKota();
    	model.addAttribute("listKota", listKota);
		return "add-penduduk";
	}
    
    @RequestMapping("/penduduk/tambah/submit")
    public String addSubmit (Model model, 
            @RequestParam(value = "nama", required = false) String nama,
            @RequestParam(value = "tempat_lahir", required = false) String tempat_lahir,
            @RequestParam(value = "tanggal_lahir", required = false) String tanggal_lahir,
            @RequestParam(value = "jenis_kelamin", required = false) int jenis_kelamin,
            @RequestParam(value = "is_wni", required = false) int is_wni,
            @RequestParam(value = "id_keluarga", required = false) BigInteger id_keluarga,
            @RequestParam(value = "status_dalam_keluarga", required = false) String status_dalam_keluarga,
            @RequestParam(value = "agama", required = false) String agama,
            @RequestParam(value = "pekerjaan", required = false) String pekerjaan,
            @RequestParam(value = "status_perkawinan", required = false) String status_perkawinan,
            @RequestParam(value = "golongan_darah", required = false) String golongan_darah)
    {
    	BigInteger id = new BigInteger("0");
    	String nik = "";
    	
    	PendudukModel penduduk = new PendudukModel (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wni);
        
        log.info("penduduk={}", penduduk);
        return tambahPendudukSubmit(model, penduduk);
    }
    
    
	public String tambahPendudukSubmit (Model model, @Valid @ModelAttribute PendudukModel penduduk) {
    	
    	if (!penduduk.getTanggal_lahir().matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$")) {
    		log.info("test");
    		return "add-penduduk";
    	
    	}
    	
    	
    	String[] tahunBulanHari = penduduk.getTanggal_lahir().split("-");
    	int tahun = (Integer.parseInt(tahunBulanHari[0]) % 1000) % 100;
    	int bulan = Integer.parseInt(tahunBulanHari[1]);
    	int hari = Integer.parseInt(tahunBulanHari[2]);
    	
    	if (penduduk.getJenis_kelamin() == 1) {
    		hari += 40;
    	}
		
		KeluargaModel keluarga = keluargaService.selectKeluargaById(penduduk.getId_keluarga());
		KelurahanModel kelurahan = kelurahanService.selectKelurahanById(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanService.selectKecamatanById(kelurahan.getId_kecamatan());
		
		String kodeProvinsiKotaKecamatan = kecamatan.getKode_kecamatan().substring(0, 6);

		String prefix = kodeProvinsiKotaKecamatan + String.format("%02d", hari) + String.format("%02d", bulan) + String.format("%02d", tahun);
		String query = prefix + "%";
		
		int localizedCount = pendudukService.countCurrentPendudukOnQuery(query);
		BigInteger allCount = pendudukService.countAllPenduduk();
		
		String finalNik = prefix + String.format("%04d", localizedCount + 1);
		
		penduduk.setNik(finalNik);
		penduduk.setIs_wafat(0);
		penduduk.setId(allCount.add(new BigInteger("1")));
		
		log.info("NIK penduduk baru = {}", penduduk.getNik());
		log.info("ID penduduk baru = {}", penduduk.getId());
		
		pendudukService.addPenduduk(penduduk);

		model.addAttribute("penduduk", penduduk);
    	
    	return "success-add-penduduk";
	}

    @RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.GET)
	public String ubahPenduduk (Model model, @PathVariable(value = "nik") String nik) {
    	PendudukModel penduduk = pendudukService.selectPenduduk(nik);
    	
    	if (penduduk != null) {
    		model.addAttribute("penduduk", penduduk);
    		return "edit-penduduk";
    	}
    	
    	model.addAttribute("nik", nik);
		return "penduduk-not-found";
	}


    @RequestMapping(value = "/penduduk/ubah/submit/{nik}", method = RequestMethod.POST)
	public String ubahPendudukSubmit (Model model, @PathVariable(value = "nik") String nik, @ModelAttribute PendudukModel penduduk) {

    	log.info("pekerjaan baru={}", penduduk.getPekerjaan());
    	
    	log.info("id = {}", penduduk.getId());
    	
    	PendudukModel original = pendudukService.selectPenduduk(nik);
    	
    	penduduk.setId(original.getId());
    	
//    	if (
//    		penduduk.getTanggal_lahir() != original.getTanggal_lahir() ||
//    		penduduk.getId_keluarga().compareTo(original.getId_keluarga()) != 0 ||
//    		penduduk.getJenis_kelamin() != original.getJenis_kelamin()
//    	) {
        	String[] tahunBulanHari = penduduk.getTanggal_lahir().split("-");
        	int tahun = (Integer.parseInt(tahunBulanHari[0]) % 1000) % 100;
        	int bulan = Integer.parseInt(tahunBulanHari[1]);
        	int hari = Integer.parseInt(tahunBulanHari[2]);
        	
        	if (penduduk.getJenis_kelamin() == 1) {
        		hari += 40;
        	}
    		
    		KeluargaModel keluarga = keluargaService.selectKeluargaById(penduduk.getId_keluarga());
    		KelurahanModel kelurahan = kelurahanService.selectKelurahanById(keluarga.getId_kelurahan());
    		KecamatanModel kecamatan = kecamatanService.selectKecamatanById(kelurahan.getId_kecamatan());
    		
    		String kodeProvinsiKotaKecamatan = kecamatan.getKode_kecamatan().substring(0, 6);

    		String prefix = kodeProvinsiKotaKecamatan + String.format("%02d", hari) + String.format("%02d", bulan) + String.format("%02d", tahun);
    		String query = prefix + "%";
    		
    		int localizedCount = pendudukService.countCurrentPendudukOnQuery(query);
    		
    		String finalNik = prefix + String.format("%04d", localizedCount + 1);
    		
    		penduduk.setNik(finalNik);

    	
    	pendudukService.updatePenduduk(penduduk);

		model.addAttribute("nik", nik);
		model.addAttribute("penduduk", penduduk);

		return "success-edit-penduduk";
	}

    
    

    @RequestMapping("/keluarga")
	public String keluarga (
		Model model,
		@RequestParam(value = "nkk", required = false) String nkk
	) {
    	if (nkk != null) {
    		KeluargaModel keluarga = keluargaService.selectKeluarga(nkk);
    		log.info("Keluarga {}", keluarga);
    		model.addAttribute("keluarga", keluarga);
    		
    		List<PendudukModel> anggota = pendudukService.selectPendudukFromKeluarga(keluarga.getId());
    		log.info("Anggota {}", anggota);
    		model.addAttribute("anggota", anggota);

    		KelurahanModel kelurahan = kelurahanService.selectKelurahanById(keluarga.getId_kelurahan());
    		model.addAttribute("kelurahan", kelurahan);
    		
    		KecamatanModel kecamatan = kecamatanService.selectKecamatanById(kelurahan.getId_kecamatan());
    		model.addAttribute("kecamatan", kecamatan);
    		
    		KotaModel kota = kotaService.selectKotaById(kecamatan.getId_kota());
    		model.addAttribute("kota", kota);
    		
    		return "view-keluarga";
    	}

		return "keluarga";
	}

    @RequestMapping(value = "/keluarga/tambah", method = RequestMethod.GET)
	public String tambahKeluarga (Model model) {
    	List<KelurahanModel> daftarKelurahan = kelurahanService.selectAllKelurahan();
    	model.addAttribute("daftarKelurahan", daftarKelurahan);
    	
		return "add-keluarga";
	}
    
    @RequestMapping("/keluarga/tambah/submit")
    public String addSubmit (Model model, 
            @RequestParam(value = "alamat", required = false) String alamat,
            @RequestParam(value = "rt", required = false) String rt,
            @RequestParam(value = "rw", required = false) String rw,
            @RequestParam(value = "id_kelurahan", required = false) BigInteger id_kelurahan)
    {
    	BigInteger id = new BigInteger("0");
    	String nkk = "";
    	int is_tidak_berlaku = 0;

        KeluargaModel keluarga = new KeluargaModel (id, nkk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku);

        log.info("keluarga={}", keluarga);
        return tambahKeluargaSubmit(model, keluarga);
    }
    

    
	public String tambahKeluargaSubmit (Model model, @ModelAttribute KeluargaModel keluarga) {
    	
    	if (keluarga.getRt().length() != 3 || keluarga.getRw().length() != 3) {
        	int i_rt = keluarga.getRt().length();
        	int i_rw = keluarga.getRw().length();
        	
        	String newRt = keluarga.getRt();
        	for(int i = i_rt; i<3; i++) {
        		newRt = "0" + newRt;
        	}
        	
        	String newRw = keluarga.getRw();
        	for(int i = i_rw; i<3; i++) {
        		newRw = "0" + newRw;
        	}
    		
        	
        	keluarga.setRt(newRt);
        	keluarga.setRw(newRw);
 
        	
    	}
    	
    	String hariBulanTahun = new SimpleDateFormat("ddMMyy").format(Calendar.getInstance().getTime());
		
		KelurahanModel kelurahan = kelurahanService.selectKelurahanById(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = kecamatanService.selectKecamatanById(kelurahan.getId_kecamatan());
		
		String kodeProvinsiKotaKecamatan = kecamatan.getKode_kecamatan().substring(0, 6);

		String prefix = kodeProvinsiKotaKecamatan + hariBulanTahun;
		String query = prefix + "%";
		
		int localizedCount = keluargaService.countCurrentKeluargaOnQuery(query);
		BigInteger allCount = keluargaService.countAllKeluarga();
		
		String finalNkk = prefix + String.format("%04d", localizedCount + 1);
		
		keluarga.setNomor_kk(finalNkk);
		keluarga.setIs_tidak_berlaku(0);
		keluarga.setId(allCount.add(new BigInteger("1")));
		
		keluargaService.addKeluarga(keluarga);
		model.addAttribute("keluarga", keluarga);
		return "success-add-keluarga";
	}
	
    @RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.GET)
	public String ubahKeluarga (Model model, @PathVariable(value = "nkk") String nkk) {
    	KeluargaModel keluarga = keluargaService.selectKeluarga(nkk);
    	
    	if (keluarga != null) {
        	List<KelurahanModel> daftarKelurahan = kelurahanService.selectAllKelurahan();
        	model.addAttribute("daftarKelurahan", daftarKelurahan);
    		model.addAttribute("keluarga", keluarga);
    		return "edit-keluarga";
    	}
    	
    	model.addAttribute("nkk", nkk);
		return "keluarga-not-found";
	}
    
    
    @RequestMapping(value = "/keluarga/ubah/submit/{nkk}", method = RequestMethod.POST)
	public String ubahKeluargaSubmit (Model model, @PathVariable(value = "nkk") String nkk, @ModelAttribute KeluargaModel keluarga) {
    	KeluargaModel original = keluargaService.selectKeluarga(nkk);

    	log.info("{}", keluarga);
    	
    	if (keluarga.getRt().length() != 3 || keluarga.getRw().length() != 3) {
        	
    		int i_rt = keluarga.getRt().length();
        	int i_rw = keluarga.getRw().length();
        	
        	String newRt = keluarga.getRt();
        	for(int i = i_rt; i<3; i++) {
        		newRt = "0" + newRt;
        	}
        	
        	String newRw = keluarga.getRw();
        	for(int i = i_rw; i<3; i++) {
        		newRw = "0" + newRw;
        	}
    		
        	
        	keluarga.setRt(newRt);
        	keluarga.setRw(newRw);
   		
    		
    	}

    	String nkkFix = "";
    	if (keluarga.getId_kelurahan().compareTo(original.getId_kelurahan()) != 0) {
    		
    		KelurahanModel kelurahan = kelurahanService.selectKelurahanById(keluarga.getId_kelurahan());
    		KecamatanModel kecamatan = kecamatanService.selectKecamatanById(kelurahan.getId_kecamatan());
    		
    		String kodeProvinsiKotaKecamatan = kecamatan.getKode_kecamatan().substring(0, 6);

    		String prefix = kodeProvinsiKotaKecamatan + original.getNomor_kk().substring(6, 12);
    		String query = prefix + "%";
    		
    		int localizedCount = keluargaService.countCurrentKeluargaOnQuery(query);
    		
    		String finalNkk = prefix + String.format("%04d", localizedCount + 1);
    		
    		nkkFix = finalNkk;
    		
    		keluarga.setNomor_kk(finalNkk);
    		
    		List<PendudukModel> anggota = pendudukService.selectPendudukFromKeluarga(keluarga.getId());
    		
    		for (int i = 0; i < anggota.size(); i++) {
    			PendudukModel penduduk = anggota.get(i);
    			log.info("Penduduk iterated on {}", penduduk);
 
            	String[] tahunBulanHari = penduduk.getTanggal_lahir().split("-");
            	int tahun = (Integer.parseInt(tahunBulanHari[0]) % 1000) % 100;
            	int bulan = Integer.parseInt(tahunBulanHari[1]);
            	int hari = Integer.parseInt(tahunBulanHari[2]);
            	
            	if (penduduk.getJenis_kelamin() == 1) {
            		hari += 40;
            	}
            	
        		String prefixPenduduk = kodeProvinsiKotaKecamatan + String.format("%02d", hari) + String.format("%02d", bulan) + String.format("%02d", tahun);
        		String queryPenduduk = prefixPenduduk + "%";
        		
        		int localizedCountPenduduk = pendudukService.countCurrentPendudukOnQuery(queryPenduduk);
        		
        		String finalNik = prefixPenduduk + String.format("%04d", localizedCountPenduduk + 1);
        		
        		penduduk.setNik(finalNik);
            	pendudukService.updatePenduduk(penduduk);
    		}
    	}
    	keluarga.setId(original.getId());
    	keluarga.setNomor_kk(nkkFix);
    	keluargaService.updateKeluarga(keluarga);

		model.addAttribute("nkk", nkk);
		model.addAttribute("keluarga", keluarga);
		return "success-edit-keluarga";
	}


    @RequestMapping("/penduduk/cari")
	public String cariPenduduk (
		Model model,
		@RequestParam(value = "kt", required = false) BigInteger kt,
		@RequestParam(value = "kc", required = false) BigInteger kc,
		@RequestParam(value = "kl", required = false) BigInteger kl
	) throws ParseException {
    	List<KotaModel> listKota = kotaService.selectAllKota();
    	model.addAttribute("listKota", listKota);
    	
    	if (kt != null) {
    		model.addAttribute("kt", kt);
    		List<KecamatanModel> listKecamatan = kecamatanService.selectAllKecamatanByKotaId(kt);
    		model.addAttribute("listKecamatan", listKecamatan);
    	}
    	
    	if (kc != null) {
    		model.addAttribute("kc", kc);
    		List<KelurahanModel> listKelurahan = kelurahanService.selectAllKelurahanByKecamatanId(kc);
    		model.addAttribute("listKelurahan", listKelurahan);
    	}

    	if (kt != null && kc != null && kl != null) {
    		KotaModel kota = kotaService.selectKotaById(kt);
    		model.addAttribute("kota", kota);
    		KecamatanModel kecamatan = kecamatanService.selectKecamatanById(kc);
    		model.addAttribute("kecamatan", kecamatan);
    		KelurahanModel kelurahan = kelurahanService.selectKelurahanById(kl);
    		model.addAttribute("kelurahan", kelurahan);
    		
    		List<KeluargaModel> listKeluarga = keluargaService.selectAllKeluargaByKelurahanId(kl);
    		
    		List<PendudukModel> daftarPenduduk = new ArrayList<PendudukModel>();
    		
    		for (int i = 0; i < listKeluarga.size(); i++) {
    			List<PendudukModel> anggotaKeluarga = pendudukService.selectPendudukFromKeluarga(listKeluarga.get(i).getId());
    			daftarPenduduk.addAll(anggotaKeluarga);
    		}
    		
    		PendudukModel youngest = null;
    		PendudukModel oldest = null;
    		
    		for (int i = 0; i < daftarPenduduk.size(); i++) {
    			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    			PendudukModel current = daftarPenduduk.get(i);

    			if (youngest == null) {
    				youngest = daftarPenduduk.get(i);
    			} else {
    				Date youngestBirthDate = dateFormatter.parse(youngest.getTanggal_lahir());
    				Date currentBirthDate = dateFormatter.parse(current.getTanggal_lahir());
    				
    				if (youngestBirthDate.compareTo(currentBirthDate) > 0) {
    					youngest = current;
    				}
    			}
    			
    			if (oldest == null) {
    				oldest = daftarPenduduk.get(i);
    			} else {
    				Date oldestBirthDate = dateFormatter.parse(oldest.getTanggal_lahir());
    				Date currentBirthDate = dateFormatter.parse(current.getTanggal_lahir());
    				
    				if (oldestBirthDate.compareTo(currentBirthDate) < 0) {
    					oldest = current;
    				}
    			}
    		}
    		
    		if (youngest != null) {
    			model.addAttribute("youngest", youngest);
    		}
    		
    		if (oldest != null) {
    			model.addAttribute("oldest", oldest);
    		}
    		
    		model.addAttribute("daftarPenduduk", daftarPenduduk);
    		
    		return "cari-result";
    	}
    	
		return "cari";
	}
}
