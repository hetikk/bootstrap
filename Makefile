.PHONY: jar-build tests-run env-start env-stop db-start db-stop db-ping

POSTGRES_IMAGE := postgres
POSTGRES_CONTAINER := bootstrap-postgres
POSTGRES_HOST := localhost
POSTGRES_USER := bootstrap
POSTGRES_PASSWORD := bootstrap
POSTGRES_DB_NAME := bootstrap

env-start:
	@ POSTGRES_IMAGE=$(POSTGRES_IMAGE) \
	POSTGRES_CONTAINER=$(POSTGRES_CONTAINER) \
	POSTGRES_HOST=$(POSTGRES_HOST) \
	POSTGRES_USER=$(POSTGRES_USER) \
	POSTGRES_PASSWORD=$(POSTGRES_PASSWORD) \
	POSTGRES_DB_NAME=$(POSTGRES_DB_NAME) \
	docker-compose up -d

env-stop:
	@ POSTGRES_IMAGE=$(POSTGRES_IMAGE) \
	POSTGRES_CONTAINER=$(POSTGRES_CONTAINER) \
	POSTGRES_HOST=$(POSTGRES_HOST) \
	POSTGRES_USER=$(POSTGRES_USER) \
	POSTGRES_PASSWORD=$(POSTGRES_PASSWORD) \
	POSTGRES_DB_NAME=$(POSTGRES_DB_NAME) \
	docker-compose stop

env-clean:
	@ docker rm -f $(POSTGRES_CONTAINER) || true
	@ docker volume prune -f

db-ping:
	@ docker exec $(POSTGRES_CONTAINER) pg_isready --host=localhost --username=$(POSTGRES_USER) --dbname=$(POSTGRES_DB_NAME)