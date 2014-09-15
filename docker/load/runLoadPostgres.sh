export PROCESSOR=`cat /proc/cpuinfo | grep 'processor' | wc -l`

if ls /data/*.nt; then
    export DATAFILE=`ls /data/*.nt`
    export FILETYPE=nt
else
    export DATAFILE=`ls /data/*.nq`
    export FILETYPE=nq
fi

export DB2_HOST=$POSTGRES_PORT_5432_TCP_ADDR
export DB2_PORT=$POSTGRES_PORT_5432_TCP_PORT
export DB2_DB=default
export DB2_USER=postgres
export DB2_PASSWORD=postgres
export DB2_SCHEMA=default
export KNOWLEDGE_BASE=default

mkdir /data/tmp

bash ../../scripts/build-load-files --db-engine postgresql --parallel $PROCESSOR --sort-options "buffer-size=25%" --tmpdir /data/tmp $FILETYPE $DATAFILE

bash ../../scripts/load-load-files --db-engine postgresql --parallel $PROCESSOR --sort-options "buffer-size=25%" --tmpdir /data/tmp $FILETYPE $DATAFILE

rm -rf /data/tmp