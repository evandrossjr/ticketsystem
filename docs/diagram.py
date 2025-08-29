from diagrams import Diagram
from diagrams.c4 import Person, Container, Database, SystemBoundary, Relationship

with Diagram(
    "Sistema de Gerenciamento de Tickets",
    filename="./docs/diagrama_tickets",
    show=False,
    direction="TB"
):
    # Componente externo: o usuário que interage com o sistema
    usuario = Person(
        "Usuário",
        "Acessa e gerencia tickets"
    )

    # Limite do sistema principal
    with SystemBoundary("Aplicação de Tickets"):
        # Containers: os blocos principais da aplicação
        frontend = Container(
            "Frontend",
            "Aplicação Web",
            "Interface do usuário construída com JavaScript"
        )

        backend_api = Container(
            "Backend API",
            "Aplicação REST",
            "Fornece endpoints para a lógica de negócios em Java"
        )

        # O backend da aplicação se conecta a dois bancos de dados
        # Um para desenvolvimento e outro para produção
        with SystemBoundary("Local Development"):
            db_h2 = Database(
                "Database (H2)",
                "Banco de Dados Local",
                "Utilizado para desenvolvimento local."
            )

        with SystemBoundary("Railway"):
            db_postgres = Database(
                "Database (Postgres)",
                "Banco de Dados de Produção",
                "Armazena dados de tickets e usuários."
            )

        # O backend da aplicação se conecta a um serviço de e-mail externo
        servico_notificacao = Relationship("Serviço de Notificação (e.g. SendGrid)", "Envia e-mails")

    # Relações entre os componentes
    usuario >> Relationship("Acessa a aplicação") >> frontend
    frontend >> Relationship("Usa a API") >> backend_api
    backend_api >> Relationship("Persiste dados") >> db_h2
    backend_api >> Relationship("Persiste dados") >> db_postgres
    backend_api >> Relationship("Envia e-mails") >> servico_notificacao