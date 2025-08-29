from diagrams import Diagram
from diagrams.c4 import Person, Container, Database, SystemBoundary, Relationship, System

with Diagram(
    "Sistema de Gerenciamento de Tickets",
    filename="./docs/diagrama_tickets",
    show=False,
    direction="TB"
):
    usuario = Person(
        "Usuário",
        "Acessa e gerencia tickets"
    )

    # Limite do sistema principal
    with SystemBoundary("Aplicação de Tickets"):
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

    # Adicionado o Render como um sistema de deployment
    render = System(
        "Render",
        "Ambiente de Deployment"
    )

    servico_notificacao = Container(
        "Serviço de Notificação (e.g. SendGrid)",
        "Serviço de terceiros",
        width="4" # Adicionado largura para o texto não cortar
    )

    # Relações entre os componentes
    usuario >> Relationship("Acessa a aplicação") >> frontend
    frontend >> Relationship("Usa a API") >> backend_api
    backend_api >> Relationship("Persiste dados") >> db_h2
    backend_api >> Relationship("Persiste dados") >> db_postgres
    backend_api >> Relationship("Envia e-mails") >> servico_notificacao

    # Relação de deployment do frontend para o Render
    frontend >> Relationship("Deploy") >> render