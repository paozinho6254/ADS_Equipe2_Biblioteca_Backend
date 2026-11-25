mermaid 
´´´
classDiagram
    direction TB

    namespace Infra {
        class ConnectionFactory {
            -DataSource dataSource$
            +getConnection() Connection$
        }
        class TransactionManager {
            +executarEmTransacao(trabalho) T$
            +executarSemRetorno(trabalho) void$
        }
    }

    namespace CoreDomain {
        class Usuario {
            <<abstract>>
            +int id
            +String nome
            +String email
            +String senhaHash
            +Perfil perfil
            +autenticar(senha) boolean
        }
        class Aluno {
            +String matricula
            +String curso
            +String endereco
        }
        class Bibliotecario
        class Administrador
        class Livro {
            +int id
            +String titulo
            +String isbn
            +String autorPrincipal
            +int anoPublicacao
        }
        class Exemplar {
            +String codigoBarras
            +StatusExemplar status
            +Livro livro
            +alterarStatus(status) void
        }
        class Reserva {
            +int id
            +LocalDate dataReserva
            +LocalDate dataExpiracao
            +StatusReserva status
            +Aluno aluno
            +Livro livro
            +isExpirada(hoje) boolean
            +marcarComoAtendida(dataBase) void
            +cancelar() void
        }
        class Emprestimo {
            +int id
            +int PRAZO_DIAS = 7$
            +LocalDate dataInicio
            +LocalDate dataPrevista
            +LocalDate dataDevolucao
            +StatusEmprestimo status
            +Aluno aluno
            +Exemplar exemplar
            +calcularDataPrevista() LocalDate
            +isAtrasado(hoje) boolean
            +calcularDiasAtraso(dataRef) int
            +registrarDevolucao(dataDevolucao) void
        }
    }

    namespace FinancialContext {
        class Multa {
            +int id
            +BigDecimal TAXA_DIARIA = 2.00$
            +int diasAtraso
            +BigDecimal valor
            +StatusMulta status
            +calcularValor() BigDecimal
            +marcarComoPaga() void
            +registrarPagamento(valorPago, modalidade) Pagamento
        }
        class Pagamento {
            +int id
            +BigDecimal valorPago
            +LocalDate dataPagamento
            +ModalidadePagamento modalidade
        }
    }

    namespace DataAccess_DAO {
        class UsuarioDAO {
            +inserir(conn, usuario) void
            +buscarPorEmail(email) Usuario
            +buscarPorId(id) Usuario
            +buscarAlunoPorId(id) Aluno
        }
        class LivroDAO {
            +inserir(conn, livro) void
            +buscarPorIsbn(isbn) Livro
            +listarTodos(filtro) List~Livro~
        }
        class ExemplarDAO {
            +inserir(conn, exemplar) void
            +buscarPorCodigoBarras(codigo) Exemplar
            +contarDisponiveisPorLivro(livroId) int
            +reservarParaEmprestimo(conn, codigo) boolean
            +atualizarStatus(conn, codigo, status) void
        }
        class ReservaDAO {
            +inserir(conn, reserva) void
            +existeAtivaPorAlunoELivro(alunoId, livroId) boolean
            +buscarProximaAtivaPorLivro(conn, livroId) Reserva
            +buscarExpiradas(hoje) List~Reserva~
            +atualizar(conn, reserva) void
        }
        class EmprestimoDAO {
            +inserir(conn, emprestimo) void
            +buscarAtivoPorExemplar(codigo) Emprestimo
            +existeAtivoPorAlunoELivro(alunoId, livroId) boolean
            +buscarPorAluno(alunoId) List~Emprestimo~
            +buscarAtivosPorAluno(alunoId) List~Emprestimo~
            +buscarAtrasados(hoje) List~Emprestimo~
            +buscarProximosVencimento(dataAlvo) List~Emprestimo~
            +atualizar(conn, emprestimo) void
        }
        class MultaDAO {
            +inserir(conn, multa) void
            +buscarPorId(conn, id) Multa
            +existePendentePorAluno(alunoId) boolean
            +atualizar(conn, multa) void
        }
        class PagamentoDAO {
            +inserir(conn, pagamento, multaId) void
        }
    }

    namespace ApplicationLayer {
        class AuthService {
            +autenticar(email, senha) Usuario
            +cadastrarUsuario(usuario) void
        }
        class AcervoService {
            +consultarCatalogo(filtro) List~Livro~
            +cadastrarLivro(livro) void
            +cadastrarExemplar(exemplar) void
        }
        class ReservaService {
            +reservarLivro(alunoId, livroId) Reserva
            +expirarReservasVencidas() int
            +cancelarReserva(reservaId) void
        }
        class EmprestimoService {
            +validarInadimplencia(alunoId) boolean
            +registrarEmprestimo(alunoId, codigoBarras) Emprestimo
            +registrarDevolucao(codigoBarras) ComprovanteDevolucao
            +consultarHistorico(usuario) List~Emprestimo~
            +consultarEmprestimosAtivos(usuario) List~Emprestimo~
            +gerarRelatorioAtrasos() List~Emprestimo~
        }
        class FinanceiroService {
            +gerarMulta(conn, emprestimo) Multa
            +registrarPagamento(multaId, valor, modalidade) Pagamento
        }
        class ApiServlets {
            <<HttpServlet>>
            +AuthServlet
            +LivroServlet
            +ExemplarServlet
            +ReservaServlet
            +EmprestimoServlet
            +MultaServlet
        }
    }

    namespace DocumentService {
        class ComprovanteDevolucao {
            +String codigoBarras
            +String tituloLivro
            +String nomeAluno
            +LocalDate dataDevolucao
            +int diasAtraso
            +BigDecimal valorMulta
        }
        class ComprovantePdfService {
            +gerarComprovanteDados(emprestimo, multa) ComprovanteDevolucao
            +gerarPdfBytes(comprovante) byte[]
        }
    }

    namespace WorkerService {
        class NotificacaoWorker {
            +verificarDevolucoesProximas() void
            +processarExpiracaoReservas() void
            +enviarLembrete(destinatario, mensagem) void
        }
        class IEmailService {
            <<interface>>
            +enviar(destinatario, assunto, mensagem) void
        }
        class EmailSimuladoService {
            +enviar(destinatario, assunto, mensagem) void
        }
    }

    class Perfil {
        <<enumeration>>
        ALUNO
        BIBLIOTECARIO
        ADMINISTRADOR
    }
    class StatusExemplar {
        <<enumeration>>
        DISPONIVEL
        RESERVADO
        EMPRESTADO
        DANIFICADO
        PERDIDO
    }
    class StatusEmprestimo {
        <<enumeration>>
        ATIVO
        DEVOLVIDO_NO_PRAZO
        DEVOLVIDO_COM_ATRASO
    }
    class StatusMulta {
        <<enumeration>>
        PENDENTE
        PAGA
    }
    class ModalidadePagamento {
        <<enumeration>>
        PIX
        DINHEIRO
    }
    class StatusReserva {
        <<enumeration>>
        ATIVA
        ATENDIDA
        EXPIRADA
        CANCELADA
    }

    %% Heranca
    Usuario <|-- Aluno
    Usuario <|-- Bibliotecario
    Usuario <|-- Administrador

    %% Relacionamentos de Dominio
    Livro "1" *-- "0..*" Exemplar : possui
    Aluno "1" -- "0..*" Emprestimo : realiza
    Exemplar "1" -- "0..*" Emprestimo : historico
    Aluno "1" -- "0..*" Reserva : solicita
    Livro "1" -- "0..*" Reserva : reservada
    Emprestimo "1" *-- "0..1" Multa : gera
    Multa "1" -- "0..1" Pagamento : quitada por
    Emprestimo "1" -- "0..1" ComprovanteDevolucao : origina

    %% Infraestrutura e Services
    TransactionManager ..> ConnectionFactory : obtem DataSource
    AuthService ..> TransactionManager
    AcervoService ..> TransactionManager
    ReservaService ..> TransactionManager
    EmprestimoService ..> TransactionManager
    FinanceiroService ..> TransactionManager

    %% Fluxo Controller -> Service -> DAO
    ApiServlets ..> AuthService
    ApiServlets ..> AcervoService
    ApiServlets ..> ReservaService
    ApiServlets ..> EmprestimoService
    ApiServlets ..> FinanceiroService
    ApiServlets ..> ComprovantePdfService

    AuthService ..> UsuarioDAO
    AcervoService ..> LivroDAO
    AcervoService ..> ExemplarDAO
    ReservaService ..> ReservaDAO
    ReservaService ..> ExemplarDAO
    ReservaService ..> EmprestimoService

    EmprestimoService ..> UsuarioDAO
    EmprestimoService ..> EmprestimoDAO
    EmprestimoService ..> ExemplarDAO
    EmprestimoService ..> ReservaDAO
    EmprestimoService ..> MultaDAO
    EmprestimoService ..> FinanceiroService
    EmprestimoService ..> ComprovantePdfService

    FinanceiroService ..> MultaDAO
    FinanceiroService ..> PagamentoDAO

    ComprovantePdfService ..> ComprovanteDevolucao
    NotificacaoWorker ..> EmprestimoDAO
    NotificacaoWorker ..> ReservaService
    NotificacaoWorker ..> IEmailService
    EmailSimuladoService ..|> IEmailService

    %% Enumeracoes
    Usuario ..> Perfil
    Exemplar ..> StatusExemplar
    Emprestimo ..> StatusEmprestimo
    Multa ..> StatusMulta
    Pagamento ..> ModalidadePagamento
    Reserva ..> StatusReserva
´´´
