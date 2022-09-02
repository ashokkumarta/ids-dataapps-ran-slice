sudo docker container prune
sudo docker image rm ashokkumarta/dataapps-ran-slice:latest
sudo docker build -t ashokkumarta/dataapps-ran-slice:latest .
sudo docker push ashokkumarta/dataapps-ran-slice:latest
