@echo off

echo Begin deploy......

mvn clean deploy -Dmaven.test.skip=true

echo Deploy success
cmd

