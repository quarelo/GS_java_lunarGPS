package presentation;

import application.*;
import domain.*;
import domain.enums.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        NavegacaoService  navegacaoService  = new NavegacaoService();
        AstronautaService astronautaService = new AstronautaService();
        SateliteService   sateliteService   = new SateliteService();
        MissaoService     missaoService     = new MissaoService();
        RotaService       rotaService       = new RotaService(navegacaoService);
        ObstaculoService  obstaculoService  = new ObstaculoService();

        popularDadosMock(astronautaService, sateliteService, navegacaoService,
                missaoService, rotaService, obstaculoService);

        System.out.println("\n==================================================");
        System.out.println("        LUNAR GPS AI - Sistema de Navegacao       ");
        System.out.println("   Navegacao Inteligente para Bases Lunares v1.0  ");
        System.out.println("==================================================");
        System.out.println("  Dados de demonstracao carregados com sucesso.");

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n==================================================");
            System.out.println("                  MENU PRINCIPAL                  ");
            System.out.println("==================================================");
            System.out.println("1 - Cadastrar Astronauta");
            System.out.println("2 - Cadastrar Satelite");
            System.out.println("3 - Registrar Posicao");
            System.out.println("4 - Criar Missao");
            System.out.println("5 - Gerar Rota");
            System.out.println("6 - Registrar Obstaculo");
            System.out.println("7 - Consultar Missoes");
            System.out.println("8 - Consultar Rotas");
            System.out.println("9 - Relatorio Operacional");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Opcao invalida! Digite um numero de 0 a 9.");
                continue;
            }

            switch (opcao) {
                case 1: cadastrarAstronauta(sc, astronautaService); break;
                case 2: cadastrarSatelite(sc, sateliteService); break;
                case 3: registrarPosicao(sc, astronautaService, navegacaoService, obstaculoService); break;
                case 4: criarMissao(sc, missaoService); break;
                case 5: gerarRota(sc, rotaService, navegacaoService, obstaculoService); break;
                case 6: registrarObstaculo(sc, obstaculoService); break;
                case 7: consultarMissoes(sc, missaoService); break;
                case 8: consultarRotas(sc, rotaService); break;
                case 9: relatorioOperacional(astronautaService, sateliteService,
                            missaoService, rotaService, obstaculoService); break;
                case 0: System.out.println("\nEncerrando LUNAR GPS AI. Ate a proxima missao!"); break;
                default: System.out.println("\n[ERRO] Opcao invalida! Escolha de 0 a 9.");
            }
        }

        sc.close();
    }

    private static void cadastrarAstronauta(Scanner sc, AstronautaService service) {
        System.out.println("\n--- CADASTRAR ASTRONAUTA ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Matricula: ");
        String matricula = sc.nextLine();
        System.out.print("Nacionalidade: ");
        String nacionalidade = sc.nextLine();
        LocalDate nascimento = lerData(sc, "Data de Nascimento (dd/MM/yyyy): ");
        float oxigenio = lerFloat(sc, "Nivel de Oxigenio (0-100): ");
        float energia  = lerFloat(sc, "Nivel de Energia (0-100): ");

        try {
            service.cadastrar(new Astronauta(0, nome, matricula, nacionalidade,
                    nascimento, oxigenio, energia, 0));
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    private static void cadastrarSatelite(Scanner sc, SateliteService service) {
        System.out.println("\n--- CADASTRAR SATELITE ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Codigo de Identificacao: ");
        String codigo = sc.nextLine();
        float altitude   = lerFloat(sc, "Altitude Orbital (km): ");
        float inclinacao = lerFloat(sc, "Inclinacao Orbital (graus): ");
        float periodo    = lerFloat(sc, "Periodo Orbital (h): ");
        StatusSatelite status = lerEnum(sc, "Status [ATIVO / INATIVO / MANUTENCAO]: ",
                StatusSatelite.class);

        try {
            service.cadastrar(new Satelite(0, nome, codigo, altitude, inclinacao,
                    periodo, status, LocalDateTime.now()));
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
    }

    private static void registrarPosicao(Scanner sc, AstronautaService astronautaService,
                                          NavegacaoService navegacaoService,
                                          ObstaculoService obstaculoService) {
        System.out.println("\n--- REGISTRAR POSICAO ---");

        List<Astronauta> lista = astronautaService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("[AVISO] Nenhum astronauta cadastrado.");
            return;
        }
        System.out.println("Astronautas disponíveis:");
        for (Astronauta a : lista) System.out.println("  " + a.getResumo());

        System.out.print("ID do Astronauta: ");
        int idAstronauta;
        try { idAstronauta = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("[ERRO] ID invalido."); return; }

        Astronauta astronauta = astronautaService.buscarPorId(idAstronauta);
        if (astronauta == null) {
            System.out.println("[ERRO] Astronauta nao encontrado.");
            return;
        }

        float lat      = lerFloat(sc, "Latitude (-90 a 90): ");
        float lon      = lerFloat(sc, "Longitude (-180 a 180): ");
        float alt      = lerFloat(sc, "Altitude (m): ");
        float precisao = lerFloat(sc, "Precisao (m): ");
        System.out.print("Tipo de Registro [A=Automatico / M=Manual]: ");
        String tipoStr = sc.nextLine().toUpperCase().trim();
        char tipo = tipoStr.isEmpty() ? 'A' : tipoStr.charAt(0);

        Posicao posicao = new Posicao(0, lat, lon, alt, precisao,
                LocalDateTime.now(), tipo, astronauta, null);
        navegacaoService.registrarPosicao(posicao);
        astronautaService.atualizarPosicao(idAstronauta, posicao.getId());

        if (obstaculoService.verificarConflito(lat, lon)) {
            System.out.println("[ALERTA] Obstaculo detectado proximo a esta posicao!");
        }
    }

    private static void criarMissao(Scanner sc, MissaoService service) {
        System.out.println("\n--- CRIAR MISSAO ---");
        System.out.println("1 - Rapido (nome + objetivo)");
        System.out.println("2 - Completo");
        System.out.print("Modo: ");
        int modo;
        try { modo = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("[ERRO] Opcao invalida."); return; }

        if (modo == 1) {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("Objetivo: ");
            String objetivo = sc.nextLine();
            service.criar(nome, objetivo).gerarRelatorio();
        } else {
            System.out.print("Nome: ");
            String nome = sc.nextLine();
            System.out.print("Descricao: ");
            String descricao = sc.nextLine();
            System.out.print("Objetivo: ");
            String objetivo = sc.nextLine();
            PrioridadeMissao prioridade = lerEnum(sc,
                    "Prioridade [BAIXA / MEDIA / ALTA / CRITICA]: ", PrioridadeMissao.class);
            System.out.print("Definir datas? (S/N): ");
            Missao m;
            if ("S".equalsIgnoreCase(sc.nextLine().trim())) {
                LocalDateTime inicio  = lerDataHora(sc, "Data de Inicio (dd/MM/yyyy HH:mm): ");
                LocalDateTime prevFim = lerDataHora(sc, "Data de Previsao de Fim (dd/MM/yyyy HH:mm): ");
                m = service.criar(nome, descricao, objetivo, prioridade, inicio, prevFim);
            } else {
                m = service.criar(nome, descricao, objetivo, prioridade);
            }
            m.gerarRelatorio();
        }
    }

    private static void gerarRota(Scanner sc, RotaService rotaService,
                                   NavegacaoService navegacaoService,
                                   ObstaculoService obstaculoService) {
        System.out.println("\n--- GERAR ROTA ---");
        System.out.print("Nome da Rota: ");
        String nome = sc.nextLine();
        TipoRota tipo = lerEnum(sc, "Tipo [DIRETA / ALTERNATIVA / EMERGENCIA]: ", TipoRota.class);

        System.out.println("Coordenadas de Origem:");
        float latOri = lerFloat(sc, "  Latitude: ");
        float lonOri = lerFloat(sc, "  Longitude: ");
        System.out.println("Coordenadas de Destino:");
        float latDes = lerFloat(sc, "  Latitude: ");
        float lonDes = lerFloat(sc, "  Longitude: ");

        if (navegacaoService.rotaContemObstaculo(latOri, lonOri, latDes, lonDes,
                obstaculoService.listarTodos())) {
            System.out.println("[ALERTA] Obstaculos detectados no trajeto desta rota!");
            System.out.print("Deseja continuar mesmo assim? (S/N): ");
            if ("N".equalsIgnoreCase(sc.nextLine().trim())) {
                System.out.println("[INFO] Geracao cancelada.");
                return;
            }
        }

        rotaService.gerar(nome, tipo, latOri, lonOri, latDes, lonDes).gerarRelatorio();
    }

    private static void registrarObstaculo(Scanner sc, ObstaculoService service) {
        System.out.println("\n--- REGISTRAR OBSTACULO ---");
        System.out.print("Tipo [CRATERA / ROCHEDO / FISSURA / POEIRA / RADIACAO / OUTRO]: ");
        String tipo = sc.nextLine().toUpperCase();
        System.out.print("Descricao: ");
        String desc = sc.nextLine();
        NivelRisco risco = lerEnum(sc, "Nivel de Risco [BAIXO / MEDIO / ALTO / CRITICO]: ",
                NivelRisco.class);
        float lat  = lerFloat(sc, "Latitude: ");
        float lon  = lerFloat(sc, "Longitude: ");
        float raio = lerFloat(sc, "Raio de Impacto (m): ");
        System.out.print("Contornavel? (S/N): ");
        boolean contornavel = "S".equalsIgnoreCase(sc.nextLine().trim());
        System.out.print("Acao Recomendada: ");
        String acao = sc.nextLine();

        Obstaculo obs = new Obstaculo(0, tipo, desc, risco, lat, lon, raio, contornavel, acao);
        service.registrar(obs);
        obs.gerarRelatorio();
    }

    private static void consultarMissoes(Scanner sc, MissaoService service) {
        System.out.println("\n--- CONSULTAR MISSOES ---");
        System.out.println("1 - Listar todas");
        System.out.println("2 - Filtrar por status");
        System.out.println("3 - Ver relatorio detalhado");
        System.out.println("4 - Alterar status");
        System.out.print("Opcao: ");
        int sub;
        try { sub = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("[ERRO] Opcao invalida."); return; }

        switch (sub) {
            case 1:
                System.out.println("\n--- MISSOES CADASTRADAS ---");
                for (Missao m : service.listarTodos()) System.out.println("  " + m.getResumo());
                break;
            case 2:
                StatusMissao filtro = lerEnum(sc,
                        "Status [PLANEJADA / EM_ANDAMENTO / CONCLUIDA / CANCELADA]: ",
                        StatusMissao.class);
                System.out.println("\n--- MISSOES COM STATUS: " + filtro + " ---");
                for (Missao m : service.listarPorStatus(filtro)) System.out.println("  " + m.getResumo());
                break;
            case 3:
                System.out.print("ID da Missao: ");
                try {
                    int idRel = Integer.parseInt(sc.nextLine().trim());
                    Missao mRel = service.buscarPorId(idRel);
                    if (mRel != null) mRel.gerarRelatorio();
                    else System.out.println("[ERRO] Missao nao encontrada.");
                } catch (NumberFormatException e) { System.out.println("[ERRO] ID invalido."); }
                break;
            case 4:
                for (Missao m : service.listarTodos()) System.out.println("  " + m.getResumo());
                System.out.print("ID da Missao: ");
                try {
                    int idAlt = Integer.parseInt(sc.nextLine().trim());
                    System.out.println("1 - Iniciar  2 - Concluir  3 - Cancelar");
                    System.out.print("Acao: ");
                    int acao = Integer.parseInt(sc.nextLine().trim());
                    if (acao == 1) service.iniciar(idAlt);
                    else if (acao == 2) service.concluir(idAlt);
                    else if (acao == 3) service.cancelar(idAlt);
                } catch (NumberFormatException e) { System.out.println("[ERRO] Valor invalido."); }
                break;
            default:
                System.out.println("[ERRO] Opcao invalida.");
        }
    }

    private static void consultarRotas(Scanner sc, RotaService service) {
        System.out.println("\n--- CONSULTAR ROTAS ---");
        System.out.println("1 - Listar todas");
        System.out.println("2 - Filtrar por status");
        System.out.println("3 - Ver relatorio detalhado");
        System.out.println("4 - Alterar status");
        System.out.print("Opcao: ");
        int sub;
        try { sub = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("[ERRO] Opcao invalida."); return; }

        switch (sub) {
            case 1:
                System.out.println("\n--- ROTAS CADASTRADAS ---");
                for (Rota r : service.listarTodos()) System.out.println("  " + r.getResumo());
                break;
            case 2:
                StatusRota filtro = lerEnum(sc, "Status [PLANEJADA / ATIVA / CONCLUIDA]: ",
                        StatusRota.class);
                System.out.println("\n--- ROTAS COM STATUS: " + filtro + " ---");
                for (Rota r : service.listarPorStatus(filtro)) System.out.println("  " + r.getResumo());
                break;
            case 3:
                System.out.print("ID da Rota: ");
                try {
                    int idRel = Integer.parseInt(sc.nextLine().trim());
                    Rota rRel = service.buscarPorId(idRel);
                    if (rRel != null) rRel.gerarRelatorio();
                    else System.out.println("[ERRO] Rota nao encontrada.");
                } catch (NumberFormatException e) { System.out.println("[ERRO] ID invalido."); }
                break;
            case 4:
                for (Rota r : service.listarTodos()) System.out.println("  " + r.getResumo());
                System.out.print("ID da Rota: ");
                try {
                    int idAlt = Integer.parseInt(sc.nextLine().trim());
                    System.out.println("1 - Ativar   2 - Concluir");
                    System.out.print("Acao: ");
                    int acao = Integer.parseInt(sc.nextLine().trim());
                    if (acao == 1) service.ativar(idAlt);
                    else if (acao == 2) service.concluir(idAlt);
                } catch (NumberFormatException e) { System.out.println("[ERRO] Valor invalido."); }
                break;
            default:
                System.out.println("[ERRO] Opcao invalida.");
        }
    }

    private static void relatorioOperacional(AstronautaService astronautaService,
                                              SateliteService sateliteService,
                                              MissaoService missaoService,
                                              RotaService rotaService,
                                              ObstaculoService obstaculoService) {
        System.out.println("\n==================================================");
        System.out.println("      LUNAR GPS AI - RELATORIO OPERACIONAL        ");
        System.out.println("  " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        System.out.println("==================================================");

        List<Astronauta> astronautas = astronautaService.listarTodos();
        List<Astronauta> criticos    = astronautaService.listarEmNivelCritico();
        System.out.println("\n--- ASTRONAUTAS (" + astronautas.size() +
                " cadastrados | " + criticos.size() + " em nivel critico) ---");
        for (Astronauta a : astronautas) System.out.println("  " + a.getResumo());

        List<Missao> missoes     = missaoService.listarTodos();
        List<Missao> emAndamento = missaoService.listarPorStatus(StatusMissao.EM_ANDAMENTO);
        List<Missao> planejadas  = missaoService.listarPorStatus(StatusMissao.PLANEJADA);
        System.out.println("\n--- MISSOES (" + missoes.size() + " total | " +
                emAndamento.size() + " em andamento | " + planejadas.size() + " planejadas) ---");
        for (Missao m : missoes) System.out.println("  " + m.getResumo());

        List<Rota> rotas  = rotaService.listarTodos();
        List<Rota> ativas = rotaService.listarPorStatus(StatusRota.ATIVA);
        System.out.println("\n--- ROTAS (" + rotas.size() + " total | " + ativas.size() + " ativas) ---");
        for (Rota r : rotas) System.out.println("  " + r.getResumo());

        List<Obstaculo> obstaculos   = obstaculoService.listarTodos();
        List<Obstaculo> altoRisco    = obstaculoService.listarPorNivelRisco(NivelRisco.ALTO);
        List<Obstaculo> criticoRisco = obstaculoService.listarPorNivelRisco(NivelRisco.CRITICO);
        List<Obstaculo> naoContorn   = obstaculoService.listarNaoContornaveis();
        System.out.println("\n--- OBSTACULOS (" + obstaculos.size() + " total | " +
                (altoRisco.size() + criticoRisco.size()) + " alto/critico | " +
                naoContorn.size() + " nao contornaveis) ---");
        for (Obstaculo o : obstaculos) System.out.println("  " + o.getResumo());

        List<Satelite> satelites = sateliteService.listarTodos();
        List<Satelite> ativos    = sateliteService.listarAtivos();
        System.out.println("\n--- SATELITES (" + satelites.size() + " total | " +
                ativos.size() + " ativos) ---");
        for (Satelite s : satelites) System.out.println("  " + s.getResumo());

        int pontuacao = criticos.size() * 3 + (altoRisco.size() + criticoRisco.size()) * 2
                + naoContorn.size();
        String risco;
        if (pontuacao >= 8)      risco = "CRITICO - Operacoes de emergencia recomendadas";
        else if (pontuacao >= 4) risco = "ALTO - Requer atencao imediata";
        else if (pontuacao >= 2) risco = "MEDIO - Monitoramento intensificado";
        else                     risco = "BAIXO - Operacoes dentro da normalidade";

        System.out.println("\n--------------------------------------------------");
        System.out.println("ANALISE DE RISCO GERAL: " + risco);
        System.out.println("==================================================");
    }

    private static void popularDadosMock(AstronautaService aS, SateliteService sS,
                                          NavegacaoService nS, MissaoService mS,
                                          RotaService rS, ObstaculoService oS) {
        aS.cadastrar(new Astronauta(0, "Neil Armstrong", "BR001", "Americano",
                LocalDate.of(1985, 3, 15), 98.5f, 95.0f, 0));
        aS.cadastrar(new Astronauta(0, "Marcos Pontes", "BR002", "Brasileiro",
                LocalDate.of(1990, 7, 22), 87.3f, 76.5f, 0));
        aS.cadastrar(new Astronauta(0, "Valentina Tereshkova", "RU001", "Russa",
                LocalDate.of(1988, 11, 5), 14.2f, 11.0f, 0));
        aS.cadastrar(new Astronauta(0, "Chris Hadfield", "CA001", "Canadense",
                LocalDate.of(1982, 5, 29), 94.1f, 88.7f, 0));

        sS.cadastrar(new Satelite(0, "LunarSat-1", "LS-001", 100.0f, 45.0f, 1.9f,
                StatusSatelite.ATIVO, LocalDateTime.now().minusMinutes(5)));
        sS.cadastrar(new Satelite(0, "LunarSat-2", "LS-002", 150.0f, 60.0f, 2.3f,
                StatusSatelite.ATIVO, LocalDateTime.now().minusMinutes(15)));
        sS.cadastrar(new Satelite(0, "LunarSat-3", "LS-003", 200.0f, 30.0f, 2.8f,
                StatusSatelite.MANUTENCAO, LocalDateTime.now().minusHours(2)));

        Astronauta a1 = aS.buscarPorId(1);
        Astronauta a2 = aS.buscarPorId(2);
        nS.registrarPosicao(new Posicao(0, -8.5f, 15.3f, 0.0f, 2.5f,
                LocalDateTime.now(), 'A', a1, null));
        nS.registrarPosicao(new Posicao(0, -9.1f, 16.7f, 5.0f, 3.0f,
                LocalDateTime.now(), 'A', a2, null));
        nS.registrarPosicao(new Posicao(0, -7.8f, 14.9f, 0.0f, 2.0f,
                LocalDateTime.now(), 'M'));

        mS.criar("Exploracao Crater Sul",
                "Reconhecimento geologico do Crater Sul",
                "Coletar amostras de solo lunar",
                PrioridadeMissao.ALTA,
                LocalDateTime.now().minusHours(3),
                LocalDateTime.now().plusHours(5));
        mS.criar("Manutencao Solar Array", "Restaurar geracao de energia do modulo B");
        mS.criar("Instalacao Antena Relay",
                "Instalacao de nova antena de comunicacao",
                "Expandir cobertura para regiao leste",
                PrioridadeMissao.MEDIA);
        mS.iniciar(1);

        rS.gerar("Base Alpha > Crater Sul",   TipoRota.DIRETA,      -8.5f, 15.3f, -9.5f, 16.8f);
        rS.gerar("Base Alpha > Modulo B",     TipoRota.ALTERNATIVA, -8.5f, 15.3f, -8.8f, 15.7f);
        rS.gerar("Rota de Emergencia Alpha",  TipoRota.EMERGENCIA,  -8.5f, 15.3f, -7.9f, 14.5f);
        rS.ativar(1);

        oS.registrar(new Obstaculo(0, "CRATERA", "Cratera de impacto de medio porte",
                NivelRisco.MEDIO, -8.8f, 15.9f, 150.0f, true, "Desviar pelo lado norte"));
        oS.registrar(new Obstaculo(0, "ROCHEDO", "Formacao rochosa instavel",
                NivelRisco.ALTO, -9.3f, 16.2f, 50.0f, false, "Area interditada - retornar a base"));
        oS.registrar(new Obstaculo(0, "FISSURA", "Fissura no solo lunar",
                NivelRisco.ALTO, -8.6f, 15.5f, 30.0f, true, "Utilizar pontes portateis"));
        oS.registrar(new Obstaculo(0, "POEIRA", "Tempestade de poeira localizada",
                NivelRisco.BAIXO, -7.5f, 14.0f, 500.0f, true, "Aguardar dissipacao"));
    }

    private static float lerFloat(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Float.parseFloat(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Valor invalido. Digite um numero.");
            }
        }
    }

    private static LocalDate lerData(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(sc.nextLine().trim(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("[ERRO] Formato invalido. Use dd/MM/yyyy");
            }
        }
    }

    private static LocalDateTime lerDataHora(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDateTime.parse(sc.nextLine().trim(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            } catch (DateTimeParseException e) {
                System.out.println("[ERRO] Formato invalido. Use dd/MM/yyyy HH:mm");
            }
        }
    }

    private static <E extends Enum<E>> E lerEnum(Scanner sc, String prompt, Class<E> enumClass) {
        while (true) {
            System.out.print(prompt);
            try {
                return Enum.valueOf(enumClass, sc.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("[ERRO] Valor invalido. Escolha uma das opcoes indicadas.");
            }
        }
    }
}
