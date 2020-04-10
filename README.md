## Top K Frequent Items in a Time-sliding Window

You need a Twitter Dev account in order to get results from the Twitter api.

Please edit the `.env` file and add your private keys. These keys should be added:

    CONSUMERKEY=##Consumer Key##
    CONSUMERSECRET=##Consumer Secret##
    ACCESSTOKEN=##Access Token##
    ACCESSTOKENSECRET=##Access Token Secret##

After you finish editing the `.env` file, start Docker and run

    docker-compose up
 
The application urls are:

    http://localhost:3000 (Frontend)
    http://localhost:8080 (Frequency Api)
    http://localhost:8081 (Tweet exporter Prometheus Server)
    http://localhost:8082 (Term exporter Prometheus Server)  
