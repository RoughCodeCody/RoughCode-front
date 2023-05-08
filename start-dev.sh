docker-compose -f docker-compose-dev.yml pull

COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f docker-compose-dev.yml up  --build -d

docker rm -f $(docker ps -a -q --filter "status=exited") || true

# docker ps -a -q --filter "status=exited" | xargs -r docker rm -f || true


