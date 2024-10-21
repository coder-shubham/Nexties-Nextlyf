class Cfg:
    # vector db
    vector_db_collection_name = "lyf_faq"
    vector_db_host = "localhost"
    vector_db_port = 8080

    # embedding model
    embedding_model_host = "ml1-dev"
    embedding_model_port = 8010
    embedding_model_url = f"http://{embedding_model_host}:{embedding_model_port}/v1/embeddings"