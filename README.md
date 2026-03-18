# Api para centro de Taekwondo


### Contexto

- El centro de Taekwondo quiere tener un sistema para gestionar las cuotas de sus alumnos, los pagos y el envio de recordatorios de pago.

### Tecnologias a utilizar

- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Spring Security
- JWT
- Docker
- Docker Compose
- Flyway

### Endpoints

| Recurso | Método | Path | Descripción |
| :--- | :--- | :--- | :--- |
| **Autenticación** | `POST` | `/api/v1/auth/login` | Login |
| | `POST` | `/api/v1/auth/register` | Registro de usuario |
| **Estudiantes** | `POST` | `/api/v1/students` | Registrar estudiante |
| | `GET` | `/api/v1/students` | Listar estudiantes |
| **Disciplinas** | `GET` | `/api/v1/disciplines` | Listar disciplinas |
| **Inscripciones** | `POST` | `/api/v1/inscriptions` | Registrar inscripción student <-> disciplina |
| **Pagos** | `GET` | `/api/v1/payments/debtors` | Listar estudiantes con deudas |
| | `POST` | `/api/v1/payments` | Registrar un pago |
