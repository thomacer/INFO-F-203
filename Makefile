CC=javac
SRCFILES=$(wildcard src/*.java)
LOC=./src

all: ${LOC}/Main.java
	${CC} -cp .:${LOC} $^

clean:
	rm ${LOC}/*.class

zip:
	zip AG2.PERALE-RUSU.zip ./src/*.java ./test/* ./rapport/rapport.tex ./rapport/*.png README.md ./Makefile ./rapport.pdf
