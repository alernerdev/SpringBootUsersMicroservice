How to run logstash:

 bin/logstash -f simple-config.conf

 where simle-config.conf file is:
-----------------------------
 input {
    file {
        type=>"users-ws-log"
        path=>"~/Work/Learning/SpringBootUsersMicroservice/users-ws.log"
    }

   file {
        type=>"albums-ws-log"
        path=>"~/Work/Learning/PhotoAppApiAlbums/albums-ws.log"
    }

}

output {
    if [type] == "users-ws-log" {

        elasticsearch {
            hosts => ["localhost:9200"]
            index => "users-ws-%{+YYYY.MM.dd}"
        }
    } else if [type] == "albums-ws-log" {
        elasticsearch {
            hosts => ["localhost:9200"]
            index => "albums-ws-%{+YYYY.MM.dd}"
        }
    }

    stdout { codec => rubydebug }
}
------------------------------------

how to run elasticsearch:

bin/elasticsearch