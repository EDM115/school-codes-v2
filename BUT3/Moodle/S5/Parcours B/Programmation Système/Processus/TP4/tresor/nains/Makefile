NAINS=balin  durin  dwalin  farin  fudin  gimli  gloin  groin  thorin oin

CFLAGS=-O0 -g -Wall
LDFLAGS=-lrt

all : $(NAINS)

%:%.c
	$(CC) -o $@ $< $(CFLAGS) $(LDFLAGS)

clean:
	rm -f $(NAINS)
