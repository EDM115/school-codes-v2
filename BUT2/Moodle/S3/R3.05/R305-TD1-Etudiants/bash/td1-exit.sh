#!/bin/bash
# effet du exit
(echo A-${BASHPID}; exit 0; echo B-${BASHPID})
echo C-${BASHPID}; exit 0; echo D-${BASHPID}
