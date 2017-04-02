package br.com.ceosites.cachedproperties.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class KeysIterable implements Iterable<String> {

	private final Iterator<String> keysIterator; 

	public KeysIterable(List<String> keyList) {
		this.keysIterator = keyList.iterator();
	}

	public KeysIterable(String... keys) {
		this.keysIterator = Arrays.asList(keys).iterator();
	}
	
	public KeysIterable(Iterator<String> keysIterator) {
		this.keysIterator = keysIterator;
	}

	@Override
	public Iterator<String> iterator() {
		return this.keysIterator;
	}

}
