package by.ischenko.trie;

import java.util.*;

class TrieNode {
	private Map<Character, TrieNode> children;
	private int telephone;

	public Map<Character, TrieNode> getChildren() {
		if (children == null)
			children = new HashMap<>(1);

		return children;
	}

	public int getTelephone() {
		return telephone;
	}

	public void setTelephone(int telephone) {
		this.telephone = telephone;
	}
}