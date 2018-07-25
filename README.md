# Running in docker-compose

Note: **this doesn't work as we hit memory limit of 1GB for t2.micro instance.**

* Follow [guide](https://www.ybrikman.com/writing/2015/11/11/running-docker-aws-ground-up/#launching-an-ec2-instance) to start 
EC2 t2.micro instance.
    *  Expose ports 22 and 9000 through security group
* Login into the instance and setup docker-compose:


    sudo yum install -y docker
    sudo curl -L https://github.com/docker/compose/releases/download/1.21.2/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
    sudo service docker start
    sudo usermod -a -G docker ec2-user
    exit

* Login again and run prepared docker-compose


    wget https://raw.githubusercontent.com/zbstof/insiders-test/master/docker-compose-remote.yml  
    docker-compose --file docker-compose-remote.yml up

* Exit and run curl from your local machine:


    curl -X POST \
      http://<INSTANCE_DNS_NAME>:9000/docs \
      -H 'Content-Type: application/json' \
      -d '{ "name": "hello mr. test" }'
    
    curl http://<INSTANCE_DNS_NAME>:9000/docs/<RETURNED_ID>
    
    curl http://<INSTANCE_DNS_NAME>:9000/docs?name=hello
    
# Running outside docker container

TODO


