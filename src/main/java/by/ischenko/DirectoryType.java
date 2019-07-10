package by.ischenko;

import by.ischenko.map.TelephoneDirectoryMap;
import by.ischenko.trie.TelephoneDirectoryTrie;

public enum  DirectoryType {
	MAP {
		@Override
		public TelephoneDirectory create() {
			return new TelephoneDirectoryMap();
		}
	},
	TRIE {
		@Override
		public TelephoneDirectory create() {
			return new TelephoneDirectoryTrie();
		}
	};

	public abstract TelephoneDirectory create();
}
