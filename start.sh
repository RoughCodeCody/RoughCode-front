docker-compose -f docker-compose.yml pull

./init-letsencrypt.sh

COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f docker-compose.yml up --build -d

docker rm -f $(docker ps -a -q --filter "status=exited") || true
