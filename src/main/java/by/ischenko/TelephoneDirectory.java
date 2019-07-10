package by.ischenko;

public interface TelephoneDirectory {
	void put(String name, int telephone);
	int find(String name);
	int size();
}
