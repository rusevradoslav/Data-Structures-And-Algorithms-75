package org.example.j_tree;

import java.util.List;

public interface AbstractTree<E> {
    List<E> orderBfs();
    List<E> postorderDfs();
    List<E> preorderDfs();
    void addChild(E parentKey, Tree<E> child);
	void removeNode(E nodeKey);
    void swap(E firstKey, E secondKey);
}
