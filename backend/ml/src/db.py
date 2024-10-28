import firebase_admin
from firebase_admin import credentials, firestore
from firebase_admin import db

class FirebaseDB:
    def __init__(self):
        # Fetch the service account key JSON file contents
        cred = credentials.Certificate('/Users/clarence/Documents/playground/HackBangalore/final-singapore/Nexties-Nextlyf/backend/ml/data/nextlyf-2609e-firebase-adminsdk-j6qwp-1bcdd9c08a.json')

        # Initialize the app with a service account, granting admin privileges
        firebase_admin.initialize_app(cred, {
            'databaseURL': 'https://nextlyf-2609e-default-rtdb.firebaseio.com'
        })

        # As an admin, the app has access to read and write all data, regradless of Security Rules
        self.ref = db.reference('/')
        self.client = firestore.client()

    def get_tables(self):
        return list(self.ref.get().keys())
    
if __name__ == "__main__":
    db = FirebaseDB()
    print(db.client.collection(u'users').get()[0].to_dict())
    # print(db.ref.get())
    # print(db.get_tables())