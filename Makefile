CC=javac
SRCFILES=$(wildcard src/*.java)
LOC=./src

all: ${LOC}/Main.java
	${CC} -cp .:${LOC} $^

clean:
	rm ${LOC}/*.class

listing:
	bash mklisting src/*.java
	pdflatex -interaction=nonstopmodelisting.tex 
	rm listing.out
	rm listing.aux
	rm listing.log
	rm listing.tex
