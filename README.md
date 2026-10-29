# OpenGTD - Backend API (Spring Boot)

API REST desenvolvida em **Java 21** com **Spring Boot**, **Spring Data JPA**, **Lombok** e banco em memória **H2** (pronta para futura migração para PostgreSQL).

## Estrutura de Pacotes

- `controller`: Endpoints REST (`TarefaController`, `DashboardController`, `PerfilController`)
- `service`: Regras de negócio, cálculo de Dashboard e regra de XP/Level (`TarefaService`, `PerfilService`)
- `repository`: Interfaces de acesso a dados com Spring Data JPA (`TarefaRepository`, `PerfilRepository`)
- `model`: Entidades e modelos de retorno (`Tarefa`, `TarefaResumo`, `Perfil`, `Dashboard`)

---

## Como Executar

No terminal, dentro da pasta `opengtd`:

```powershell
.\mvnw.cmd spring-boot:run
```

- **URL Base da API:** `http://localhost:8080`
- **Console do Banco H2:** `http://localhost:8080/h2-console`
  - **JDBC URL:** `jdbc:h2:mem:opengtddb`
  - **User Name:** `sa`
  - **Password:** *(deixar em branco)*

---

## Endpoints Disponíveis (Para testar no Postman - Aula 19)

### 1. Tarefas (`/tarefas`)

- **`POST /tarefas`** — Cadastrar nova tarefa (Aula 15)
  ```json
  {
    "titulo": "Lavar a Louça",
    "descricao": "Lavar a louça com esponja",
    "peso": "intensa",
    "timestamp": 23123132,
    "status": "espera",
    "tags": ["cotidiano", "higiene", "organização"]
  }
  ```
- **`GET /tarefas`** — Listar todas as tarefas completas (Aula 16)
- **`GET /tarefas/resumo`** — Listar tarefas no formato `ListaTarefas`:
  ```json
  [
    {
      "id": 1,
      "titulo": "Lavar a Louça",
      "timestamp": 1291329012,
      "peso": "intensa"
    }
  ]
  ```
- **`GET /tarefas/{id}`** — Buscar tarefa por ID
- **`PUT /tarefas/{id}`** — Atualizar tarefa (Aula 17)
  *(Observação: alterar o `status` para `"concluido"` ou `"fracassado"` aciona automaticamente o cálculo de XP/Level e Streak no Perfil)*
- **`DELETE /tarefas/{id}`** — Excluir tarefa (Aula 18)

### 2. Dashboard (`/dashboard`)

- **`GET /dashboard`** — Retorna o resumo quantitativo das tarefas:
  ```json
  {
    "tarefasTotais": 105,
    "tarefasConcluidas": 80,
    "tarefasEmAndamento": 10,
    "tarefasEmEspera": 10,
    "tarefasFracassadas": 5
  }
  ```

### 3. Perfil (`/perfil`)

- **`GET /perfil`** — Retorna os dados do perfil e gamificação:
  ```json
  {
    "nome": "Yuri",
    "nivel": 15,
    "streak": 12,
    "xpAtual": 500,
    "xpProximoNivel": 300
  }
  ```
- **`PUT /perfil`** — Atualiza manualmente os dados do perfil.
