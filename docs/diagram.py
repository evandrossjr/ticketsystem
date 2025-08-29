from diagrams import Diagram
from diagrams.c4 import Person, Container, Database, SystemBoundary, Relationship

with Diagram(
    "Sistema de Gerenciamento de Tickets",
    filename="./docs/diagrama_tickets",
    show=False,
    direction="TB"
):
    # Componentes Externos
    usuario = Person(
        "Usuário-Admin",
        "Cliente que interage com a API"
    )

    # Sistema Interno
    with SystemBoundary("Sistema de Gerenciamento de Tickets"):
        app = Container(
            "Aplicação API",
            "Java REST API",
            "Fornece endpoints JSON para tickets e usuários."
        )

        app2 = Container(
            "Aplicação API",
            "Java REST API",
            "Fornece endpoints JSON para tickets e usuários."
        )

        app3 = Container(
            "Aplicação API",
            "Java REST API",
            "Fornece endpoints JSON para tickets e usuários."
        )

        app4 = Container(
            "Aplicação API",
            "Java REST API",
            "Fornece endpoints JSON para tickets e usuários."
        )

        db = Database(
            "Banco de Dados H2",
            "Banco de dados em memória",
            "Armazena dados de tickets e usuários."
        )

        db2 = Database(
            "Banco de Bahia Postgress",
            "Banco de dados em memória",
            "Armazena dados de tickets e usuários."
        )

        db4 = Database(
            "Banco de Dados AWS RDS",
            "Banco de dados em memória",
            "Armazena dados de tickets e usuários."
        )

        db5 = Database(
            "Banco de Dados Oracle",
            "Banco de dados em memória",
            "Armazena dados de tickets e usuários."
        )

        db7 = Database(
            "Banco de Dados Oracle",
            "Banco de dados em memória",
            "Armazena dados de tickets e usuários."
        )

        db6 = Database(
            "Banco de Dados Azure SQL",
            "Banco de dados em memória",
            "Armazena dados de tickets e usuários."
        )

        db9 = Database(
            "Banco de Dados Bahia Cloud SQL",
            "Banco de dados em memória",
            "Armazena dados de tickets e usuários."
        )


    # Relações entre os componentes
    usuario >> Relationship("Usa a API REST") >> app
    app >> Relationship("Lê e escreve dados") >> db