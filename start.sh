docker-compose -f docker-compose.yml pull

docker-compose -f docker-compose.yml down



COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f docker-compose.yml up --build -d

# ./init-letsencrypt.sh

docker rm -f $(docker ps -a -q --filter "status=exited") || true
