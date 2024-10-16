import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

class FirebaseDB:
    def __init__(self):
        # Fetch the service account key JSON file contents
        cred = credentials.Certificate('../data/indilancer-c9297-firebase-adminsdk-cww8c-9095e5319a.json')

        # Initialize the app with a service account, granting admin privileges
        firebase_admin.initialize_app(cred, {
            'databaseURL': 'https://indilancer-c9297-default-rtdb.firebaseio.com/'
        })

        # As an admin, the app has access to read and write all data, regradless of Security Rules
        self.ref = db.reference('/')

    def get_tables(self):
        return list(self.ref.get().keys())