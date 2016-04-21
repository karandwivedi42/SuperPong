all:
	cd Backend
	javac *.java
	java *.class
clean:
	cd Backend
	rm -rf *.class
	cd ../Networking 
	rm -rf *.class