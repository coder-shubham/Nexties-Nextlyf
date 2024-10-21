import json
import logging
import weaviate
import traceback
from tqdm import tqdm
from weaviate.classes.query import Filter
from weaviate.classes.query import MetadataQuery
from weaviate.classes.config import Configure, VectorDistances

class WeaviateDB:
    def __init__(self, collection_name, vectorizer_fn, host="ml1-dev", port=8080):
        self.collection_name = collection_name
        self.client = weaviate.connect_to_local(
            host=host,
            port=port,
            # grpc_port=port
        )
        self.vectorizer_fn = vectorizer_fn
        self.collection = None

    def get_or_create_collection(self):
        try:
            self.collection = self.client.collections.get(self.collection_name)
            logging.info(f"Collection {self.collection_name} already exists.")

        except weaviate.RequestError:
            # Collection does not exist, create a new one
            self.collection = self.client.collections.create(
                self.collection_name,
                vector_index_config=Configure.VectorIndex.hnsw(
                    distance_metric=VectorDistances.COSINE
                ),
            )
            logging.info(f"Collection {self.collection_name} created successfully.")
            
    def drop_collection(self):
        self.client.collections.delete(self.collection_name)
        logging.info(f"Collection {self.collection_name} deleted successfully.")
    
    def view_document(self, id):
        data_object = self.collection.query.fetch_object_by_id(id)
        return data_object.properties

    def insert(self, document):
        if self.collection is not None:
            question = str(document['question'])
            answer = str(document['answer'])
            utterance = question + " " + answer
            document_embedding = self.vectorizer_fn(utterance)
            doc_id = self.collection.data.insert(
                properties=document,
                vector=document_embedding
            )
            return doc_id
        else:
            logging.info("Collection not initialized. Please create a collection first.")

    def insert_bulk(self, data):
        # # Load data from JSON file
        # with open(json_path, 'r') as json_file:
        #     data = json.load(json_file)

        # Insert data into the collection
        if self.collection is not None:
            doc_ids = []
            with self.collection.batch.fixed_size(batch_size=50) as batch:
                for document in tqdm(data):
                    question = str(document['question'])
                    answer = str(document['answer'])
                    utterance = question + " " + answer
                    document_embedding = self.vectorizer_fn(utterance)
                    doc_id = batch.add_object(
                        # collection="JeopardyQuestion",
                        properties=document,
                        vector=document_embedding
                    )
                    doc_ids.append(doc_id)
            
            return doc_ids
        else:
            logging.info("Collection not initialized. Please create a collection first.")

    def delete_documents(self, document_id_list):
        if self.collection is not None:
            # for document_id in document_id_list:
            try:
                self.collection.data.delete_many(
                    where=Filter.by_id().contains_any(document_id_list)  # Delete the 3 objects
                )
            except:
                logging.error(traceback.format_exc())
                logging.error(f"Document with IDs {document_id_list} not found.")
        else:
            logging.info("Collection not initialized. Please create a collection first.")

    # TODO: add threshld value
    def get_similar(self, query, limit=2, alpha=0.5):
        # Encode the query
        query_vector = self.vectorizer_fn(query)
        
        response = self.collection.query.hybrid(
            query=query,
            vector=query_vector.tolist(),
            # query_properties=["Resolution", "document", "Section"],
            alpha=alpha, 
            return_metadata=MetadataQuery(score=True, explain_score=True),
            limit=limit
        )
        retrieved_documents = [o.properties for o in response.objects]

        return retrieved_documents

if __name__ == '__main__':
    from qa.vector_db import WeaviateDB
    from config import Cfg
    from qa.embeddings import get_embeddings

    db = WeaviateDB(
        collection_name=Cfg.vector_db_collection_name, 
        vectorizer_fn=get_embeddings, 
        host=Cfg.vector_db_host, 
        port=Cfg.vector_db_port,
    )

    # create a collection
    db.get_or_create_collection()

    # insert data
    db.insert_bulk(json_path="../data/faq.csv")