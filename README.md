# Nextlyf
The "Nexties" have developed an application which will help to redefine modern living and will help user from booking room , attending events or meeting new connection and best part, its per user preferences and interests.

## Unique Value Proposition (UVP)
- Personalized Recommendations
- Nexi – Your go-to buddy , an advance voice assistant
- Community feeling among residents.
- Loyalty rewards and discounts to boost engagement.
- Customer retention campaign


## TechStack

- Android Studio
- Java
- Python 3.12
- Solr
- Firebase
- SpringBoot
- Recommendation Engine
- Sentiment Analysis
- Topic Modelling 
  
## Repo structure
The repo is divided into the frontend and backend code. The frontend has been developed as an Android application written in Java. The backend in written in Python which hosts the APIs required by the frontend.

### Get started
1. Clone the repo

   ```
   git clone https://github.com/coder-shubham/Nexties-Nextlyf.git
   cd Nexties-Nextlyf
   ```

## Frontend Application

1. Navigate to the frontend code directory from project root:
    ```bash
    cd frontend
    ```
    
2. Run project via Android Studio.


## Backend Application

1. You need OPENAI API key to run the demo. You can checkout https://openai.com/index/openai-api/ to know more.

Set you OPENAI API key as env variable called `OPENAI_API_KEY` using this command on your terminal: `export OPENAI_API_KEY=<key goes here>`

To run the backend server first install the requirements.txt file

```pip install -r requirements.txt```

```cd Nexties-Nextlyf/backend/ml/src```

```python3 app.py```

2. Start SpringBoot Server for Solr and other backend Services. Note: You must have latest JDK installed.

```bash
    cd ../backend/server/EmailSender/
    mvn clean
    mvn package
    mvn spring-boot:run
```

### Install Application

```bash
    Download APK from release in your Android Mobile and enjoy...:)
```


