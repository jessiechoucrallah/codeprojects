package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
    
    // prevent instantiation
    private Trie() { }
    
    /**
     * Builds a trie by inserting all words in the input array, one at a time,
     * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
     * The words in the input array are all lower case.
     * 
     * @param allWords Input array of words (lowercase) to be inserted.
     * @return Root of trie with all words inserted from the input array
     */
    public static TrieNode buildTrie(String[] allWords) {
        /** COMPLETE THIS METHOD **/
        // The root will have no substring or siblings
                // No node other than the root can have one child
                TrieNode root = new TrieNode(null, null, null);
                if (allWords.length > 0) {
                    root.firstChild = new TrieNode(new Indexes(0, (short) 0, (short) (allWords[0].length() - 1)), null, null);
                    for (int i = 1; i < allWords.length; i++) {
                        root.firstChild = insertWord(allWords, root.firstChild, allWords[i], (short) i);
                    }
                }
        // FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
        // MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
        return root;
    }
    // bu should be 3, 1, 1 for words1
        private static TrieNode insertWord(String[] allWords, TrieNode node, String word, short j) {
            boolean match = false;
            TrieNode prev = null;
            TrieNode curr = node;
            // if no prefixes match, add a new sibling
            while (curr != null) {
                Indexes ind = curr.substr;
                String substr = allWords[ind.wordIndex].substring(ind.startIndex, ind.endIndex + 1);
                if (word.substring(ind.startIndex).startsWith(substr)) {
                    // If the inserted word begins with the entire substring at this
                    // node, the inserted word should be the last child of this node
                    // It can be assumed that this node has at least one child
                    // because
                    // no word is inserted that contains entirely another word

                    curr.firstChild = insertWord(allWords, curr.firstChild, word, j);

                    return node;
                } else {
                    for (int i = substr.length() - 1; i > 0; i--) {
                        if (word.substring(ind.startIndex).startsWith(substr.substring(0, i))) {
                            // if a match is found, this node becomes internal
                            match = true;

                            TrieNode child = new TrieNode(
                                    new Indexes(ind.wordIndex, (short) (ind.startIndex + i), (short) ind.endIndex), null,
                                    null);
                            child.firstChild = curr.firstChild;
                            // keep previous word index
                            TrieNode n = new TrieNode(
                                    new Indexes(ind.wordIndex, ind.startIndex, (short) (ind.startIndex + i - 1)), child,
                                    curr.sibling);
                            TrieNode add = new TrieNode(
                                    new Indexes(j, (short) (ind.startIndex + i), (short) (word.length() - 1)), null, null);
                            child.sibling = add;
                            if (prev != null) {
                                prev.sibling = n;
                            } else {
                                return n;
                            }
                            break;
                        }
                    }
                }
                prev = curr;
                curr = curr.sibling;
            }

            // If the word to insert does not match any of the nodes on the level,
            // add it as the next sibling
            if (!match) {
                // Since add is on the same level as node, its startIndex is the
                // same as node's
                TrieNode add = new TrieNode(new Indexes(j, (short) node.substr.startIndex, (short) (word.length() - 1)),
                        null, null);
                if (prev != null)
                    prev.sibling = add;
                else
                    return add;
            }
            return node;
        }

    
    /**
     * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
     * trie whose words start with this prefix. 
     * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
     * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
     * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
     * and for prefix "bell", completion would be the leaf node that holds "bell". 
     * (The last example shows that an input prefix can be an entire word.) 
     * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
     * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
     *
     * @param root Root of Trie that stores all words to search on for completion lists
     * @param allWords Array of words that have been inserted into the trie
     * @param prefix Prefix to be completed with words in trie
     * @return List of all leaf nodes in trie that hold words that start with the prefix, 
     *             order of leaf nodes does not matter.
     *         If there is no word in the tree that has this prefix, null is returned.
     */
        public static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix) {
            /** COMPLETE THIS METHOD **/
            ArrayList<TrieNode> list = new ArrayList<>();
            return completionList(root.firstChild, allWords, prefix, list);
        }

        private static ArrayList<TrieNode> completionList(TrieNode root, String[] allWords, String prefix,
                ArrayList<TrieNode> list) {

            TrieNode node = root;
            while (node != null) {
                Indexes ind = node.substr;
                String substring = allWords[ind.wordIndex].substring(ind.startIndex, ind.endIndex + 1);
                String fullString = allWords[ind.wordIndex].substring(0, ind.endIndex + 1);
                if (fullString.startsWith(prefix) || prefix.startsWith(substring)) {
                    if (node.firstChild == null) {
                        // if this is a leaf node
                        if (fullString.startsWith(prefix))
                            list.add(node);
                    }
                    completionList(node.firstChild, allWords, prefix, list);
                }
                if (substring.equals(prefix))
                    break;
                node = node.sibling;
            }
            return list.isEmpty() ? null : list;
        }

        public static void print(TrieNode root, String[] allWords) {
            System.out.println("\nTRIE\n");
            print(root, 1, allWords);
        }

        private static void print(TrieNode root, int indent, String[] words) {
            if (root == null) {
                return;
            }
            for (int i = 0; i < indent - 1; i++) {
                System.out.print("    ");
            }

            if (root.substr != null) {
                String pre = words[root.substr.wordIndex].substring(0, root.substr.endIndex + 1);
                System.out.println("      " + pre);
            }

            for (int i = 0; i < indent - 1; i++) {
                System.out.print("    ");
            }
            System.out.print(" ---");
            if (root.substr == null) {
                System.out.println("root");
            } else {
                System.out.println(root.substr);
            }

            for (TrieNode ptr = root.firstChild; ptr != null; ptr = ptr.sibling) {
                for (int i = 0; i < indent - 1; i++) {
                    System.out.print("    ");
                }
                System.out.println("     |");
                print(ptr, indent + 1, words);
            }
        }
    }