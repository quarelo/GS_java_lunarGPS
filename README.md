# LunarGPS AI 🌙

Sistema de navegação inteligente para bases lunares desenvolvido em Java para a **Global Solution — FIAP**.

O sistema simula o gerenciamento de missões, rotas, astronautas, satélites e obstáculos em operações na superfície lunar, com detecção automática de riscos e cálculo de rotas otimizadas.

---

## Estrutura do Projeto

O projeto segue a arquitetura em camadas (Domain Driven Design):

```
src/
├── presentation/
│   └── Main.java                     # Menu interativo via console
│
├── application/
│   ├── AstronautaService.java        # Lógica de negócio de astronautas
│   ├── SateliteService.java          # Lógica de negócio de satélites
│   ├── MissaoService.java            # Lógica de negócio de missões
│   ├── RotaService.java              # Lógica de negócio de rotas
│   ├── ObstaculoService.java         # Lógica de negócio de obstáculos
│   └── NavegacaoService.java         # Cálculo de distâncias e consumo energético
│
├── domain/
│   ├── EntidadeBase.java             # Classe base abstrata (id + gerarRelatorio)
│   ├── Pessoa.java                   # Classe abstrata com atributo nome
│   ├── Astronauta.java               # Entidade astronauta (herda de Pessoa)
│   ├── Satelite.java                 # Entidade satélite
│   ├── Posicao.java                  # Entidade posição GPS lunar
│   ├── Missao.java                   # Entidade missão
│   ├── Rota.java                     # Entidade rota (com origem e destino)
│   ├── Obstaculo.java                # Entidade obstáculo
│   └── enums/
│       ├── NivelRisco.java           # BAIXO, MEDIO, ALTO, CRITICO
│       ├── PrioridadeMissao.java     # BAIXA, MEDIA, ALTA, CRITICA
│       ├── StatusMissao.java         # PLANEJADA, EM_ANDAMENTO, CONCLUIDA, CANCELADA
│       ├── StatusRota.java           # PLANEJADA, ATIVA, CONCLUIDA
│       ├── StatusSatelite.java       # ATIVO, INATIVO, MANUTENCAO
│       └── TipoRota.java             # DIRETA, ALTERNATIVA, EMERGENCIA
│
└── infrastructure/
    ├── AstronautaRepositorio.java    # ArrayList de astronautas (banco em memória)
    ├── SateliteRepositorio.java      # ArrayList de satélites
    ├── PosicaoRepositorio.java       # ArrayList de posições
    ├── MissaoRepositorio.java        # ArrayList de missões
    ├── RotaRepositorio.java          # ArrayList de rotas
    └── ObstaculoRepositorio.java     # ArrayList de obstáculos
```

---

## Requisitos Atendidos

| Requisito | Implementação |
|-----------|---------------|
| **Arquitetura em Camadas** | `presentation`, `application`, `domain`, `infrastructure` |
| **Entidades com ID único** | `EntidadeBase` fornece `id` para todas as entidades |
| **Getters / Setters** | Presentes em todas as entidades |
| **Construtores padrão e não padrão** | Todas as classes têm `Classe()` e `Classe(params...)` |
| **Herança** | `EntidadeBase → Pessoa → Astronauta` (3 níveis) |
| **Polimorfismo Override** | `gerarRelatorio()` sobrescrito em 6 entidades |
| **Polimorfismo Overload** | `calcularDistancia()` com 3 assinaturas; `criar()` com 3 assinaturas |
| **ArrayList** | 6 repositórios na camada `infrastructure` |
| **Menu de navegação** | 10 opções no console (0–9) |
| **Validações** | Matrícula duplicada, IDs inválidos, formatos de data/hora, alertas de obstáculos |

---

## Como Compilar e Rodar

### Pré-requisitos
- Java 17 ou superior instalado
- Variável `JAVA_HOME` configurada **ou** use o caminho completo do `javac`

### Compilar

No terminal, dentro da pasta raiz do projeto:

```bash
# Linux / macOS
javac -encoding UTF-8 -d out $(find src -name "*.java")

# Windows (PowerShell)
$files = Get-ChildItem -Path src -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $files
```

> A pasta `out/` será criada automaticamente se você adicioná-la antes:
> ```bash
> mkdir out   # Linux/macOS
> New-Item -ItemType Directory -Force out   # Windows PowerShell
> ```

### Executar

```bash
# Linux / macOS
java -cp out presentation.Main

# Windows
java -cp out presentation.Main
```

### Compilar e rodar com Maven (opcional)

Se o Maven estiver instalado:

```bash
mvn compile exec:java
```

---

## Funcionalidades do Sistema

Ao iniciar, o sistema carrega dados de demonstração automaticamente e exibe o menu:

```
==================================================
        LUNAR GPS AI - Sistema de Navegacao
   Navegacao Inteligente para Bases Lunares v1.0
==================================================

1 - Cadastrar Astronauta
2 - Cadastrar Satélite
3 - Registrar Posição
4 - Criar Missão
5 - Gerar Rota
6 - Registrar Obstáculo
7 - Consultar Missões
8 - Consultar Rotas
9 - Relatório Operacional
0 - Sair
```

### Principais recursos

- **Cadastro de astronautas** com monitoramento de nível de oxigênio e energia
- **Rastreamento de satélites** em órbita lunar
- **Registro de posições GPS** associadas a astronautas ou satélites
- **Criação de missões** com prioridade, datas e status
- **Geração de rotas** com cálculo automático de distância (fórmula de Haversine lunar), consumo energético e tempo estimado
- **Registro de obstáculos** com nível de risco e detecção automática de conflitos
- **Relatório operacional** com análise de risco geral da base

---

## Hierarquia de Classes (Domain)

```
EntidadeBase  (abstract)
│   ├── id : int
│   └── gerarRelatorio() : String  [abstract]
│
├── Pessoa  (abstract)
│   ├── nome : String
│   └── Astronauta
│       ├── matricula, nacionalidade, dataNascimento
│       ├── nivelOxigenio, nivelEnergia
│       └── posicaoAtual : int
│
├── Satelite
│   ├── nome, codigoIdentificacao
│   ├── altitudeOrbital, inclinacaoOrbital, periodoOrbital
│   └── status : StatusSatelite
│
├── Posicao
│   ├── latitude, longitude, altitude, precisao
│   ├── tipoRegistro : char
│   ├── astronauta : Astronauta
│   └── satelite : Satelite
│
├── Missao
│   ├── nome, descricao, objetivo
│   ├── status : StatusMissao
│   ├── prioridade : PrioridadeMissao
│   └── dataInicio, dataPrevisaoFim, dataFimReal
│
├── Rota
│   ├── nome, distanciaTotal, consumoEnergiaEst, tempoEstimado
│   ├── tipoRota : TipoRota
│   ├── status : StatusRota
│   ├── origem : Posicao
│   ├── destino : Posicao
│   └── missao : Missao
│
└── Obstaculo
    ├── tipo, descricao, nivelRisco : NivelRisco
    ├── latitude, longitude, raioImpacto
    ├── contornavel : boolean
    ├── acaoRecomendada : String
    └── rota : Rota
```

---

## Tecnologias

- **Java 17**
- **Maven** (build opcional)
- Sem dependências externas — apenas a biblioteca padrão do Java (`java.util`, `java.time`)

---

## Grupo

Grupo KOLIA
Nicolas Baradel - José Kaneto - Enzo Quarelo - Gabriel Thompson - João Pedro Sassarão
