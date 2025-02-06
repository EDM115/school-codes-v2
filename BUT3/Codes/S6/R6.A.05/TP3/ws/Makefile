GPP = g++ -Wall
SRC = ./src
BIN = ./bin

compilCConvert:
	@echo Compilation CConvert
	$(GPP) $(SRC)/CConvert.cpp -o $(BIN)/CConvert.o -c

compilTestCConvert: compilCConvert
	@echo Compilation testCConvert
	$(GPP) $(SRC)/testCConvert.cpp $(BIN)/CConvert.o -o $(BIN)/testCConvert.bin

compilCManFch:
	@echo Compilation CManFch
	$(GPP) $(SRC)/CManFch.cpp -o $(BIN)/CManFch.o -c

compilTestCManFch: compilCManFch
	@echo Compilation testCManFch
	$(GPP) $(SRC)/testCManFch.cpp $(BIN)/CManFch.o -o $(BIN)/testCManFch.bin
