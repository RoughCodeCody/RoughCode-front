function create_docker_image_blue(){

  echo "> blue docker image 만들기"

  cd back-end/roughcode 

  ./gradlew clean build

  docker build -t app:0.1 .

}

function create_docker_image_green(){

  echo "> green docker image 만들기"

  cd back-end/roughcode 

  ./gradlew clean build

  docker build -t app:0.2 .
}

function execute_blue(){
    docker ps -q --filter "name=app_blue" || grep -q . && docker stop app_blue && docker rm app_blue || true

    sleep 10

    docker-compose -p app-blue -f docker-compose.blue.yml up -d

    sleep 10

    echo "GREEN:8083 종료"
    docker-compose -p app-green -f docker-compose.green.yml down

    #dangling=true : 불필요한 이미지 지우기
    docker rmi -f $(docker images -f "dangling=true" -q) || true
}

function execute_green(){
  docker ps -q --filter "name=app_green" || grep -q . && docker stop app_green && docker rm app_green || true

    echo "GREEN:8083 실행"
    docker-compose -p app-green -f docker-compose.green.yml up -d

    sleep 10

    echo "BLUE:8082 종료"
    docker-compose -p app-blue -f docker-compose.blue.yml down

    #dangling=true : 불필요한 이미지 지우기
    docker rmi -f $(docker images -f "dangling=true" -q) || true
}

# 현재 사용중인 어플리케이션 확인
# 8083포트의 값이 없으면 8082포트 사용 중
# shellcheck disable=SC2046
RUNNING_GREEN=$(docker ps -aqf --filter "name=app_green")
RUNNING_BLUE=$(docker ps -aqf --filter "name=app_blue")

echo ${RUNNING_GREEN}
echo ${RUNNING_BLUE}

# Blue or Green
if [ -z ${RUNNING_GREEN} ]
  then
    # 초기 실행 : BLUE도 실행중이지 않을 경우
    if [ -z ${RUNNING_BLUE} ]
    then
      echo "구동 앱 없음 => BLUE 실행"

      create_docker_image_blue

      sleep 10

      docker-compose -p app-blue -f docker-compose.blue.yml up -d
	  
    else
      # 8086포트로 어플리케이션 구동
      echo "BLUE:8082 실행 중"

      create_docker_image_green

      execute_green

    fi
else
    # 8085포트로 어플리케이션 구동
    echo "GREEN:8083 실행 중"

    echo "BLUE:8082 실행"

    create_docker_image_blue

    execute_blue

fi

# 새로운 어플리케이션 구동 후 현재 어플리케이션 종료
#kill -15 ${RUNNING_PORT_PID}