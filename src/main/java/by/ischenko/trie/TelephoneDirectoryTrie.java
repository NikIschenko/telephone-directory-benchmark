package by.ischenko.trie;

import by.ischenko.TelephoneDirectory;

public class TelephoneDirectoryTrie implements TelephoneDirectory {
	private TrieNode root;
	private int size;

	public TelephoneDirectoryTrie() {
		root = new TrieNode();
		size = 0;
	}

	public void put(String name, int telephone) {
		TrieNode current = root;

		for (int i = 0; i < name.length(); i++) {
			current = current.getChildren().computeIfAbsent(name.charAt(i), c -> new TrieNode());
		}
		current.setTelephone(telephone);
		size++;
	}

	public int find(String name) {
		TrieNode current = root;

		for (int i = 0; i < name.length(); i++) {
			char ch = name.charAt(i);
			TrieNode node = current.getChildren().get(ch);
			if (node == null) {
				return -1;
			}
			current = node;
		}
		return current.getTelephone();
	}

	public int size() {
		return size;
	}
}