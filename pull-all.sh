#!/usr/bin/env bash

repositorios=( "plataforma/core" \
    "plataforma/discovery" \
    "plataforma/documents" \
    "plataforma/gateway" \
    "plataforma/services" \
    "plataforma/test" \
    "plataforma/ui" \
	"plataforma/userauthentication" \
    "autuacao/autuacao" \
    "autuacao/distribuicao" \
    "autuacao/peticionamento" \
    "autuacao/recebimento" \
)

for identificador in "${repositorios[@]}"
do
    if [ -d $identificador"/.git" ]; then
        cd $identificador
        echo "Fazendo pull de "$identificador
        git pull
        cd - > /dev/null
    fi
done