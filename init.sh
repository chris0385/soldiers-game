#!/bin/bash

# For roundtrip test; use a remote host, double tunnel - should be slow :)     --> 3ms roundtrip to raspberry-pi in local network
#      ssh -L 9123:localhost:9124 -R 9124:localhost:5643 pi@192.168.178.29

alias soldier_run="mvn install -Dmaven.test.skip && ( cd soldiers-web ; mvn jetty:run )"
