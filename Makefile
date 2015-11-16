install:
#

listing:
	bash mklisting src/*
	pdflatex listing.tex
	rm listing.out
	rm listing.aux
	rm listing.log
	rm listing.tex
