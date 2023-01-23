# spring-aws-ses-sandbox

## how to run

1. run servers

```sh
# run aws sdk stub(aws-ses-v2-local)
docker-compose up -d

# run spring boot application
./gradlew bootRun
```

2. open browser, access to [http://localhost:8080/mail](http://localhost:8080/mail)

   if mail send success, show message *success*

3. open browser, access to [http://localhost:8005/](http://localhost:8005/)

   list mails
