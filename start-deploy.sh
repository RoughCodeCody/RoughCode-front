docker-compose -f docker-compose-deploy.yml pull

COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f docker-compose-deploy.yml up --build -d

docker rm -f $(docker ps -a -q --filter "status=exited") || true
