# play-evolutions-test-example

This is an example Evolutions ups & downs scripts testing from a specific revision.

# How to run test

```sh
$ docker build -f ./Dockerfile -t evolutions_test --no-cache=true .
$ docker run --name evolutions_test -d -p 3306:3306 evolutions_test
$ sbt root/test
```
