JC = javac
build:
	$(JC) Main.java
run:
	java Main
clean:
	rm -f *.class
.PHONY: build run clean
