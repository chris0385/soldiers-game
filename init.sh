#!/bin/bash

alias soldier_run="mvn install -Dmaven.test.skip && ( cd soldiers-web ; mvn jetty:run )"
