FROM python:3.9-slim

# Instala o Graphviz e suas dependências
RUN apt-get update && apt-get install -y graphviz libgraphviz-dev

# Instala a biblioteca diagrams
RUN pip install diagrams[c4]