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

    usuario2 = Person(
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


        db = Database(
            "Banco de Dados SQL Local H2",
            "Banco de dados em memória",
            "Armazena dados de tickets e usuários."
        )

        db2 = Database(
            "Banco de Bahia Postgress",
            "Banco de dados em memória",
            "Armazena dados de tickets e usuários."
        )

        db4 = Database(
            "Banco de Novos Dados AWS RDS",
            "Banco de dados em memória",
            "Armazena dados de tickets e usuários."
        )



    # Relações entre os componentes
    usuario >> Relationship("Usa a API REST") >> app
    app >> Relationship("Lê e escreve dados") >> db