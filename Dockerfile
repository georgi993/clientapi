FROM ubuntu:latest

# Set environment variables
ENV DEBIAN_FRONTEND=noninteractive

# Update package lists and install required packages
RUN apt-get update && apt-get install -y \
    postgresql postgresql-contrib \
    openjdk-21-jdk \
    redis-server \
    curl \
    wget \
    && rm -rf /var/lib/apt/lists/*

# Set environment variables for Java
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:$PATH"

# Configure PostgreSQL
RUN service postgresql start && \
    su - postgres -c "psql -c \"ALTER USER postgres WITH PASSWORD 'password';\""

# Configure Redis
RUN sed -i 's/^supervised .*/supervised no/' /etc/redis/redis.conf

# Expose PostgreSQL and Redis ports
EXPOSE 5432 6379

# Start services when container runs
CMD service postgresql start && service redis-server start && tail -f /dev/null