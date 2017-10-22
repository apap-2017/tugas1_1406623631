window.onload = function () {
  var inputKotaDOM = document.getElementById("inputKota");

  inputKotaDOM.addEventListener("change", function () {
    var thisInput = this;
    var selectedOption = thisInput.options[thisInput.selectedIndex];

    window.location.href = "/penduduk/cari?kt=" + selectedOption.value;
  }, false);

  var inputKecamatanDOM = document.getElementById("inputKecamatan");

  inputKecamatanDOM.addEventListener("change", function () {
    var kotaValue = inputKotaDOM.options[inputKotaDOM.selectedIndex].value;
    var thisInput = this;
    var selectedOption = thisInput.options[thisInput.selectedIndex];

    window.location.href = "/penduduk/cari?kt=" + kotaValue + "&kc=" + selectedOption.value;
  }, false);

  var inputKelurahanDOM = document.getElementById("inputKelurahan");
  var submitButtonDOM = document.getElementById("submitButton");

  inputKelurahanDOM.addEventListener("change", function () {
    var thisInput = this;
    var selectedOption = thisInput.options[thisInput.selectedIndex];

    if (selectedOption.value != null) {
      if (submitButtonDOM.hasAttribute("disabled")) {
        submitButtonDOM.removeAttribute("disabled");
      }
    } else {
      if (!submitButtonDOM.hasAttribute("disabled")) {
        submitButtonDOM.setAttribute("disabled", "disabled");
      }
    }
  }, false);

  if (inputKelurahanDOM.options[inputKelurahanDOM.selectedIndex].value != null && inputKelurahanDOM.options[inputKelurahanDOM.selectedIndex].value != 'Pilih Kelurahan') {
    if (submitButtonDOM.hasAttribute("disabled")) {
      submitButtonDOM.removeAttribute("disabled");
    }
  }
};