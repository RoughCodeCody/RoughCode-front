docker-compose -f docker-compose-dev.yml pull

# COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f docker-compose-dev.yml up  --build -d
COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f docker-compose-dev.yml up --env-file=.env --build -d

docker rm -f $(docker ps -a -q --filter "status=exited") || true
