#!/bin/bash
# différence {} ()
echo $$ A-${BASHPID}
{ echo B-${BASHPID}; }
( echo C-${BASHPID} )
{ echo D-${BASHPID}; } | { read x ; echo ${x} D2-${BASHPID}; }
( echo E-${BASHPID} ) | ( read x ; echo ${x} E2-${BASHPID} )
