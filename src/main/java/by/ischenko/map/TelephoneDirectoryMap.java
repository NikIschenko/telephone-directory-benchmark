package by.ischenko.map;

import by.ischenko.TelephoneDirectory;

import java.util.HashMap;
import java.util.Map;

public class TelephoneDirectoryMap implements TelephoneDirectory {
	private final Map<String, Integer> directory;

	public TelephoneDirectoryMap() {
		this.directory = new HashMap<>();
	}

	public void put(String name, int telephone) {
		directory.put(name, telephone);
	}

	public int find(String name) {
		Integer telephone = directory.get(name);
		return telephone == null
				? -1
				: telephone;
	}

	@Override
	public int size() {
		return directory.size();
	}
}
