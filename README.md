# OpenGTD - Backend API (Spring Boot)

**OpenGTD** is a gamified task management RESTful API inspired by the *Getting Things Done (GTD)* methodology. Built with **Java 21**, **Spring Boot**, **Spring Data JPA**, **Lombok**, and an in-memory **H2** database (ready for future migration to PostgreSQL).

It combines full task CRUD operations and quantitative dashboard analytics with an RPG-style progression system—rewarding completed tasks with XP, level-ups, and streak multipliers based on task difficulty.

---

## Package Structure

- `controller`: REST endpoints (`TarefaController`, `DashboardController`, `PerfilController`)
- `service`: Business logic, dashboard metrics calculation, and XP/Level progression rules (`TarefaService`, `PerfilService`)
- `repository`: Data access interfaces powered by Spring Data JPA (`TarefaRepository`, `PerfilRepository`)
- `model`: Entities and DTOs (`Tarefa`, `TarefaResumo`, `Perfil`, `Dashboard`)

---

## How to Run

In your terminal, inside the `opengtd` folder:

```powershell
# Windows (PowerShell / CMD)
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

- **Base API URL:** `http://localhost:8080`
- **Swagger UI / OpenAPI:** `http://localhost:8080/swagger-ui.html`
- **H2 Database Console:** `http://localhost:8080/h2-console`
  - **JDBC URL:** `jdbc:h2:mem:opengtddb`
  - **User Name:** `sa`
  - **Password:** *(leave blank)*

---

## Available Endpoints

### 1. Tasks (`/tarefas`)

- **`POST /tarefas`** — Create a new task
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
- **`GET /tarefas`** — List all tasks (full details)
- **`GET /tarefas/resumo`** — List tasks in summary format (`TarefaResumo`):
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
- **`GET /tarefas/{id}`** — Get a task by ID
- **`PUT /tarefas/{id}`** — Update an existing task
  *(Note: changing the `status` to `"concluido"` or `"fracassado"` automatically triggers XP, Level, and Streak calculations on the user's Profile)*
- **`DELETE /tarefas/{id}`** — Delete a task

### 2. Dashboard (`/dashboard`)

- **`GET /dashboard`** — Returns quantitative metrics across all tasks:
  ```json
  {
    "tarefasTotais": 105,
    "tarefasConcluidas": 80,
    "tarefasEmAndamento": 10,
    "tarefasEmEspera": 10,
    "tarefasFracassadas": 5
  }
  ```

### 3. Profile & Gamification (`/perfil`)

- **`GET /perfil`** — Returns user profile and gamification stats:
  ```json
  {
    "nome": "Yuri",
    "nivel": 15,
    "streak": 12,
    "xpAtual": 500,
    "xpProximoNivel": 300
  }
  ```
- **`PUT /perfil`** — Manually update profile attributes.
