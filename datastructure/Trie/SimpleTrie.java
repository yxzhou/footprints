package fgafa.datastructure.Trie;

import java.util.Collection;
import java.util.HashMap;

public class SimpleTrie {
	private HashMap<Character, HashMap> root = new HashMap<>();
	private final Character END_CHARACTER = '$';

	public SimpleTrie() {
	}

	public SimpleTrie(String s) {
		add(s);
	}

	public SimpleTrie(Collection<String> collection) {
		for (String s : collection) {
			add(s);
		}
	}

	public void add(String s) {
		HashMap<Character, HashMap> node = root;
		for (int i = 0; i < s.length(); i++) {
			Character character = s.charAt(i);
			if (!node.containsKey(character)) {
				node.put(character, new HashMap<Character, HashMap>());
			}
			node = node.get(character);
		}
		node.put(END_CHARACTER, new HashMap<>());
	}

	public boolean contains(String s) {
		HashMap<Character, HashMap> node = root;
		for (int i = 0; i < s.length(); i++) {
			Character character = s.charAt(i);
			if (node.containsKey(character)) {
				node = node.get(character);
			} else {
				return false;
			}
		}
		return node.containsKey(END_CHARACTER);
	}

}
