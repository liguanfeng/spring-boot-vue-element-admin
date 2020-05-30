#!/usr/bin/env bash

APPNAME=admin
VERSION=latest
PROT=8080:8080
# shellcheck disable=SC2006
RUN_ID=`docker ps | grep  ${APPNAME} | grep -v grep | awk '{print $1}'`
echo "RUN_ID : ${RUN_ID}"

if [[ "${RUN_ID}" ]]; then
  echo "docker restart ${APPNAME} "
  docker restart ${APPNAME}
else
  echo "docker run ${APPNAME}:${VERSION} "
   docker run -p ${PROT}  --name ${APPNAME} app/${APPNAME}:${VERSION}
fi


