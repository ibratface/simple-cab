FROM mysql:5.7
COPY ny_cab_data_cab_trip_data_full.sql /docker-entrypoint-initdb.d/
