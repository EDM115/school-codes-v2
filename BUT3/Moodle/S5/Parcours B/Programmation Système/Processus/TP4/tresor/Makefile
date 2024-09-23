BIN=genealogie

.PHONY: nains
all: $(BIN)

genealogie: genealogie.c nains
	$(CC) -o genealogie genealogie.c

nains:
	make -C nains
clean:
	make -C nains clean
	rm -rf $(BIN)
