#!/bin/bash


function create_docker_image_blue(){

  echo "> blue front docker image 만들기"

  cd front-end 

  pnpm run build

  docker build -t front:0.1 .

  cd ..

}

function create_docker_image_green(){

  echo "> green front docker image 만들기"

  cd front-end 

  pnpm run build

  docker build -t front:0.2 .

  cd ..
  
}

function execute_blue(){
    docker ps -q --filter "name=front_blue" || grep -q . && docker stop front_blue && docker rm front_blue || true

    sleep 10

    docker-compose -p front-blue -f  docker-compose.blue.yml up -d

    sleep 10

    echo "GREEN:3003 종료"
    docker-compose -p front-green -f docker-compose.green.yml down

    #dangling=true : 불필요한 이미지 지우기
    docker rmi -f $(docker images -f "dangling=true" -q) || true
}

function execute_green(){
  docker ps -q --filter "name=front_green" || grep -q . && docker stop front_green && docker rm front_green || true

    echo "GREEN:3003 실행"
    docker-compose -p front-green -f docker-compose.green.yml up -d

    sleep 10

    echo "BLUE:3002 종료"
    docker-compose -p front-blue -f docker-compose.blue.yml down

    #dangling=true : 불필요한 이미지 지우기
    docker rmi -f $(docker images -f "dangling=true" -q) || true
}

# 현재 사용중인 어플리케이션 확인
# 3003포트의 값이 없으면 3002포트 사용 중
# shellcheck disable=SC2046
RUNNING_GREEN=$(docker ps -aqf "name=front_green")
RUNNING_BLUE=$(docker ps -aqf "name=front_blue")

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

      docker-compose -p front-blue -f docker-compose.blue.yml up -d
	  
    else
      # 8086포트로 어플리케이션 구동
      echo "BLUE:3002 실행 중"

      create_docker_image_green

      execute_green

    fi
else
    # 8085포트로 어플리케이션 구동
    echo "GREEN:3003 실행 중"

    echo "BLUE:3002 실행"

    create_docker_image_blue

    execute_blue

fi

# 새로운 어플리케이션 구동 후 현재 어플리케이션 종료
#kill -15 ${RUNNING_PORT_PID}