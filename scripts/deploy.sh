#!/bin/bash
BUILD_JAR=$(ls /home/ec2-user/build/libs/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
YML_PWD=$(ls /home/ec2-user/src/main/resources/*.yml)
echo ">>> build 파일명: $JAR_NAME" >> /home/ec2-user/deploy.log

DEPLOY_PATH=/home/ec2-user/
echo ">>> build 파일 복사" >> /home/ec2-user/deploy.log
cp $BUILD_JAR $DEPLOY_PATH
echo ">>> yml 파일 복사" >> /home/ec2-user/deploy.log
cp $YML_PWD $DEPLOY_PATH

echo ">>> 현재 실행중인 애플리케이션 pid 확인" >> /home/ec2-user/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo ">>> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/deploy.log
else
  echo ">>> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
echo ">>> 로그 폴더 생성" >> /home/ec2-user/deploy.log
mkdir /home/ec2-user/log
echo ">>> DEPLOY_JAR 배포"    >> /home/ec2-user/deploy.log
nohup java -jar -Dspring.profiles.active=dev -Dspring.config.location=$YML_PWD $DEPLOY_JAR >> /home/ec2-user/deploy.log 2>/home/ec2-user/deploy_err.log &