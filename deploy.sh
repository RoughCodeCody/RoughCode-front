EXIST_BLUE=$(docker-compose -p ${IMAGE_NAME}-blue -f docker-compose.blue.yaml ps | grep Up)

if [ -z "$EXIST_BLUE" ]; then
    docker-compose -p ${IMAGE_NAME}-blue -f ./docker-compose.blue.yaml up -d
    BEFORE_COMPOSE_COLOR="green"
    AFTER_COMPOSE_COLOR="blue"
    BEFORE_PORT_NUMBER=8081
    AFTER_PORT_NUMBER=8080
else
    docker-compose -p ${IMAGE_NAME}-green -f ./docker-compose.green.yaml up -d
    BEFORE_COMPOSE_COLOR="blue"
    AFTER_COMPOSE_COLOR="green"
    BEFORE_PORT_NUMBER=8080
    AFTER_PORT_NUMBER=8081
fi

echo "${AFTER_COMPOSE_COLOR} server up(port:${AFTER_PORT_NUMBER})"

# 2
# for cnt in {1..10}
# do
#     echo "서버 응답 확인중..(${cnt}/10)";
#     UP=$(curl -s http://localhost:${AFTER_PORT_NUMBER}/actuator/health | grep 'UP')
#     if [ -z "${UP}" ] 
#         then
# 	    sleep 10
# 	    continue       
#         else
#             break
#     fi
# done
cnt=1
while [ $cnt -le 10 ]
do
    echo "서버 응답 확인중..(${cnt}/10)";
    UP=$(curl -s http://localhost:${AFTER_PORT_NUMBER}/actuator/health | grep 'UP')
    if [ -z "${UP}" ] 
        then
	    sleep 10
	    continue       
        else
            break
    fi
    cnt=$((cnt+1))
done


if [ $cnt -eq 10 ]
then
    echo "서버가 정상적으로 구동되지 않았습니다."
    exit 1
fi

# 3
# sudo sed -i "s/${BEFORE_PORT_NUMBER}/${AFTER_PORT_NUMBER}/" /etc/nginx/conf.d/service-url.inc
sed -i "s/${BEFORE_PORT_NUMBER}/${AFTER_PORT_NUMBER}/" /etc/nginx/conf.d/service-url.inc
# sudo nginx -s reload
nginx -s reload
echo "Deploy Completed!!"

# 4
echo "$BEFORE_COMPOSE_COLOR server down(port:${BEFORE_PORT_NUMBER})"
docker-compose -p ${IMAGE_NAME}-${BEFORE_COMPOSE_COLOR} -f docker-compose.${BEFORE_COMPOSE_COLOR}.yaml down