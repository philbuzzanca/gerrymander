package com.orioles.helper_model;

import java.io.Serializable;
import java.util.Objects;

public class Pair<K extends Serializable, V extends Serializable> implements Serializable {
	private K key;
	private V value;

	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	protected K getKey() {
		return key;
	}

	protected void setKey(K key) {
		this.key = key;
	}

	protected V getValue() {
		return value;
	}

	protected void setValue(V value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Pair<?, ?> pair = (Pair<?, ?>) o;
		return key.equals(pair.key) && value.equals(pair.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}

	@Override
	public String toString() {
		return String.format("<%s, %s>", key , value);
	}
}
