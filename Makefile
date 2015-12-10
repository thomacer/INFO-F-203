CC=javac
SRCFILES=$(wildcard src/*.java)
LOC=./src

all: ${LOC}/RushHour.java
	${CC} -cp .:${LOC} $^

clean:
	rm ${LOC}/*.class

listing:
	bash mklisting src/*
	pdflatex listing.tex
	rm listing.out
	rm listing.aux
	rm listing.log
	rm listing.tex
