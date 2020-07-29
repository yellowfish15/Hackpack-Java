import java.util.HashMap;
import java.util.Map;

/*
 * Trie Data Structure:
 * 
 * Allows you to search and insert words
 * into the data structure
 * 
 * Allows you to search for words in O(key_length)
 * Memory space is O(ALPHABET_SIZE*key_length*N)
 * 
 * This implementation does not include deletion
 * 
 */

class Trie {
	private TrieNode root;

	public Trie() {
		root = new TrieNode(true); // root can represent an empty string
	}

	// Insert word into trie
	public void insert(String word) {
		if (word == null || word.length() == 0)
			return;
		TrieNode parent = root;
		for (int i = 0; i < word.length(); i++) {
			char cur = word.charAt(i);
			TrieNode child = parent.children.get(cur);
			if (child == null) {
				child = new TrieNode(false);
				parent.children.put(cur, child);
			}
			parent = child; // Navigate to next level
		}
		parent.isEndOfWord = true;
	}

	// Returns true if the word is in the trie.
	public boolean search(String word) {
		if (word == null)
			return false;
		TrieNode parent = root;
		for (int i = 0; i < word.length(); i++) {
			char cur = word.charAt(i);
			TrieNode child = parent.children.get(cur);
			if (child == null)
				return false;
			parent = child; // Navigate to next level
		}
		return parent.isEndOfWord;
	}

	// Returns true if there is any word in the trie that starts with the given
	// prefix.
	public boolean startsWith(String prefix) {
		if (prefix == null)
			return false;
		TrieNode parent = root;
		for (int i = 0; i < prefix.length(); i++) {
			char cur = prefix.charAt(i);
			TrieNode child = parent.children.get(cur);
			if (child == null) 
				return false;
			parent = child; // Navigate to next level
		}
		return true;
	}

	/*
	 * Deletes a word from the trie if present, and return true if the word is
	 * deleted successfully.
	 */
	public boolean delete(String word) {
		if (word == null || word.length() == 0)
			return false;

		TrieNode deleteBelow = null;
		char deleteChar = '\0';

		TrieNode parent = root;
		for (int i = 0; i < word.length(); i++) {
			char cur = word.charAt(i);
			TrieNode child = parent.children.get(cur);
			if (child == null)
				return false;
			if (parent.children.size() > 1 || parent.isEndOfWord) {
				deleteBelow = parent;
				deleteChar = cur;
			}
			parent = child;
		}
		if (!parent.isEndOfWord) // word isn't in trie
			return false;
		if (parent.children.isEmpty())
			deleteBelow.children.remove(deleteChar);
		else
			parent.isEndOfWord = false;
		return true;
	}

	private class TrieNode {
		boolean isEndOfWord;
		Map<Character, TrieNode> children;

		TrieNode(boolean isEndOfWord) {
			this.isEndOfWord = isEndOfWord;
			this.children = new HashMap<>();
		}
	}

	// Driver Method
	public static void main(String[] args) {
		// Input keys (use only 'a' through 'z' and lower case)
		String keys[] = { "the", "a", "there", "answer", "any", "by", "bye", "their" };

		String output[] = { "Not present in trie", "Present in trie" };

		Trie trie = new Trie();

		// Construct trie
		int i;
		for (i = 0; i < keys.length; i++)
			trie.insert(keys[i]);

		// Search for different keys
		if (trie.search("the") == true)
			System.out.println("the --- " + output[1]);
		else
			System.out.println("the --- " + output[0]);

		if (trie.search("these") == true)
			System.out.println("these --- " + output[1]);
		else
			System.out.println("these --- " + output[0]);

		if (trie.search("their") == true)
			System.out.println("their --- " + output[1]);
		else
			System.out.println("their --- " + output[0]);

		if (trie.search("thaw") == true)
			System.out.println("thaw --- " + output[1]);
		else
			System.out.println("thaw --- " + output[0]);
		
		if (trie.search("b") == true) // not a word
			System.out.println("b --- " + output[1]);
		else
			System.out.println("b --- " + output[0]);
	}
}
