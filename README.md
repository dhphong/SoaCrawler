# SoaCrawler

## Running RabbitMQ
Config ``hosts``

    Add rabbit 127.0.0.1 to /etc/hosts

Add RabbitMQ volume:

Config the data folder at ``docker-compose.yml``

Start:

    docker-composer up -d

Stop:

    docker-composer down

Try RabbitMQ GUI on: ``localhost:15672``

## Running aplication
Start ``edu.soa.main.MainApplication``
Start ``edu.soa.downloader.DownloaderApplication``
Swagger UI API test on: ``http://localhost:9090/api/swagger-ui.html``