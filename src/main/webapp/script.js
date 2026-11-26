// Mostra uma mensagem simples
function aviso(msg) {
  alert(msg);
}

// Login simples (protótipo): admin/bibli -> área da biblioteca, outro -> aluno
function entrar() {
  var u = document.getElementById("u").value.toLowerCase();
  var s = document.getElementById("pw").value;
  if (u == "" || s == "") {
    alert("Informe usuário e senha");
    return;
  }
  if (u.indexOf("admin") >= 0 || u.indexOf("bibli") >= 0) {
    window.location.href = "inicio.html";
  } else {
    window.location.href = "minha-area.html";
  }
}

// Filtra as linhas de uma tabela pelo texto digitado
function filtrar(input, idTabela) {
  var linhas = document.querySelectorAll("#" + idTabela + " tbody tr");
  var texto = input.value.toLowerCase();
  for (var i = 0; i < linhas.length; i++) {
    if (linhas[i].textContent.toLowerCase().indexOf(texto) >= 0) {
      linhas[i].style.display = "";
    } else {
      linhas[i].style.display = "none";
    }
  }
}

// Filtra as multas por status (Todas, Pendente, Paga)
function filtrarMultas(status) {
  var linhas = document.querySelectorAll("#tm tbody tr");
  for (var i = 0; i < linhas.length; i++) {
    var st = linhas[i].getAttribute("data-status");
    linhas[i].style.display = (status == "Todas" || st == status) ? "" : "none";
  }
  var botoes = document.querySelectorAll("#filtros button");
  for (var j = 0; j < botoes.length; j++) {
    botoes[j].className = botoes[j].getAttribute("data-f") == status ? "btn" : "btn s";
  }
}
