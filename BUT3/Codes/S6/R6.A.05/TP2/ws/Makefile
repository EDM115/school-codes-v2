GPP = g++ -Wall
SRC = ./src
BIN = ./bin

compilCPoint:
	@echo Compilation CPoint
	$(GPP) $(SRC)/CPoint.cpp -o $(BIN)/CPoint.o -c

compilTestCPoint: compilCPoint
	@echo Compilation testCPoint
	$(GPP) $(SRC)/testCPoint.cpp $(BIN)/CPoint.o -o $(BIN)/testCPoint.bin

compilLanceur: compilCPoint
	@echo Compilation Lanceur
	$(GPP) $(SRC)/Lanceur.cpp $(BIN)/CPoint.o -o $(BIN)/Lanceur.bin

compilCVoiture1:
	@echo Compilation CVoiture1
	$(GPP) $(SRC)/CVoiture1.cpp -o $(BIN)/CVoiture1.o -c

compilTestCVoiture1: compilCVoiture1
	@echo Compilation testCVoiture1
	$(GPP) $(SRC)/testCVoiture1.cpp $(BIN)/CVoiture1.o -o $(BIN)/testCVoiture1.bin

compilCVoiture2:
	@echo Compilation CVoiture2
	$(GPP) $(SRC)/CVoiture2.cpp -o $(BIN)/CVoiture2.o -c

compilTestCVoiture2: compilCVoiture2
	@echo Compilation testCVoiture2
	$(GPP) $(SRC)/testCVoiture2.cpp $(BIN)/CVoiture2.o -o $(BIN)/testCVoiture2.bin
