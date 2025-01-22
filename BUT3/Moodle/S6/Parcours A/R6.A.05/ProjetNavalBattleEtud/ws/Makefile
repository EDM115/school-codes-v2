# Makefile du projet "Bataille navale" A PLACER dans le répertoire /ws du projet VSCode

# Chaque classe du projet doit être testée séparément.
# Il y aura donc un lanceur (main) par classe à tester.

# Ce makefile doit permettre de faire :
#	- la compilation du lanceur associé à chaque classe à tester
#	- la compilation du projet final (c-à-d main.cpp)

# Déclaration de quelques variables utiles au makefile
GPP = g++ -Wall
SRC = ./src
BIN = ./bin

# La cible "deleteAll" est exécutée en tapant la commande "make deleteAll"
deleteAll :
	@# Précéder chaque commande d'une TABULATION
	@echo suppression du contenu du répertoire ./bin
	rm -f $(BIN)/*.o $(BIN)/*.bin

# Test de la classe CBateau
# Compilation du lanceur testCBateau, pour cela (l'ordre de compilation est important en C++) :
#	- supprimer le contenu du répertoire "./bin" et
#	- d'abord compiler CBateau
#	- ensuite compiler testCBateau

# La cible "testCBateau" est exécutée en la déclarant correctement sous VSCode
testCBateau : deleteAll compilCBateau compilTestCBateau

# La cible "compilCBateau" est exécutée en la déclarant correctement sous VSCode
compilCBateau :
	@# Précéder chaque commande d'une TABULATION
	@echo Compilation CBateau
	$(GPP) $(SRC)/CBateau.cpp -o $(BIN)/CBateau.o -c

# La cible "compilTestCBateau" est exécutée en la déclarant correctement sous VSCode
compilTestCBateau :
	@# Précéder chaque commande d'une TABULATION
	@echo Compilation testCBateau
	$(GPP) $(SRC)/testCBateau.cpp $(BIN)/CBateau.o -o $(BIN)/testCBateau.bin

# A VOUS D'ECRIRE LA SUITE...

# ICI, la cible de compilation finale pour obtenir main.bin
# A executer A LA FIN du projet en s'assurant que toutes les classes du projet compilent et s'exécutent sans erreurs !
# La cible "lanceurFinal"
lanceurFinal : deleteAll compilCBateau compilCArmada compilCCoups compilCGui compilCSocket compilCClientSocket compilCServerSocket compilCJoueur compilCJoueurCli compilCJoueurServ
	@echo Compilation lanceurFinal
	$(GPP) $(SRC)/main.cpp $(BIN)/CBateau.o $(BIN)/CArmada.o $(BIN)/CCoups.o $(BIN)/CGui.o $(BIN)/CSocket.o $(BIN)/CClientSocket.o $(BIN)/CServerSocket.o $(BIN)/CJoueur.o $(BIN)/CJoueurCli.o $(BIN)/CJoueurServ.o -o $(BIN)/main.bin


